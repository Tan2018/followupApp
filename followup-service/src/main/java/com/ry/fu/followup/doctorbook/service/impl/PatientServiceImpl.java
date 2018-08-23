package com.ry.fu.followup.doctorbook.service.impl;

import com.ry.fu.followup.base.model.Pagenation;
import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.config.UrlConfig;
import com.ry.fu.followup.constant.CommonConstant;
import com.ry.fu.followup.doctorbook.mapper.GroupRecordMapper;
import com.ry.fu.followup.doctorbook.mapper.PatientMapper;
import com.ry.fu.followup.doctorbook.model.Patient;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.PatientService;
import com.ry.fu.followup.utils.JsonUtils;
import com.ry.fu.followup.utils.ResponseMapUtil;

import com.ry.fu.followup.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
/**
 * Created by Jam on 2017/12/8.
 *
 */
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${esbServerHostAndPort}")
    private String esbServerHostAndPort;

    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private GroupRecordMapper groupRecordMapper;

    private Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryPatientsList(ReqInfo reqInfo) {
        Long pageNumber = reqInfo.getPageNumber();
        Long pageSize = reqInfo.getPageSize();
        Long projectId = reqInfo.getProjectId();
        String patientName = reqInfo.getPatientName();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (null != patientName) {
            patientName = patientName.trim();
            if (patientName.length() == 0) {
                patientName = null;
            }
        }
        Integer totalCount = patientMapper.getPatientsCountByGroupId(projectId, patientName);
        Pagenation pagenation = new Pagenation(pageNumber.intValue(), totalCount, pageSize.intValue());
        List<Patient> lists;
        if (null != patientName) {
            lists = patientMapper.queryPatientsByGroupIdAndNameByPage(projectId, patientName, pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());
        } else {
            lists = patientMapper.queryPatientsByGroupIdByPage(projectId, pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());
        }

        List<Map<String, Object>> patientsList = new ArrayList<>();

        for (int i = 0; i < lists.size(); i++) {

            Patient patient = lists.get(i);
            Map<String, Object> patientMap = new HashMap<>();

        //isEntrust字段:判断该医生是否把权限转移给了别人
            List<Map<String, Object>> maps = groupRecordMapper.selectDoctors(patient.getId(), projectId);
            Map<String, Object> map = maps.get(0);
            String memType = map.get("MEM_TYPE").toString();
            Object transferDoctor = map.get("TRANSFER_DOCTOR");
            if (memType.equals("项目负责人")){
                if (map.get("TRANSFER_DOCTOR") == null){
                    patientMap.put("isEntrust",0); //未转移
                }else{
                    //如果转移了看是否是转移给了自己

            String userId = map.get("USER_ID").toString();
                if (transferDoctor != null){
                    if (userId.equals(transferDoctor.toString())){
                        patientMap.put("isEntrust",0);//转移给了自己,未转移
                    }else{
                        patientMap.put("isEntrust",1);//转移给了别人
                    }
                }else {
                    patientMap.put("isEntrust",0);//未转移
                }
            }
            }else{
                if (map.get("TRANSFER_DOCTOR") != null){
                    patientMap.put("isEntrust",1);//已经转移给了别人
                }else{
                    patientMap.put("isEntrust",0);//未转移
                }
            }

        //newage:新的患者年龄字段便于兼容旧版本
            long birthdayTimetime = 0;
            String dateString = null;
            Date birthday = patient.getBirthday();
            if(birthday!=null){
                birthdayTimetime  = birthday.getTime();
                dateString = formatter.format(birthdayTimetime);
            }

            if (dateString!=null){

                DateTime birthTime = TimeUtils.getSdfDate(dateString);
                String age=TimeUtils.getAge(birthTime,new DateTime());
                patientMap.put("newage", age);

            }else{
                patientMap.put("newage", "");
            }

        //字段:下次随访时间下
            Date nextFollowUpTime = patient.getNextFollowupTime();
            Date maxFollowupTime = patient.getMaxFollowupTime();
            String formatDate = "未设置开始时间";
            if (nextFollowUpTime != null) {
                formatDate = "下次随访时间" + formatter.format(nextFollowUpTime);
            }else if(maxFollowupTime !=null){
                //如果最大值不为空则显示已过期。
                formatDate = "已过期";
            }

            patientMap.put("nextFollowupTime", formatDate);
            patientMap.put("id", patient.getId());
            patientMap.put("name", patient.getName());
            patientMap.put("hospitalNo", patient.getHospitalNo());
            patientMap.put("cdrPatientId", patient.getCdrPatientId());
            patientMap.put("groupRecordId", patient.getGroupRecordId());

            //网易云信的 用户ID = p+平台患者id
            //主索引--by_tasher
            String mpiId = String.valueOf(patient.getCdrPatientId());
            ReqInfo esb_reqInfo = new ReqInfo();
            //患者id
            esb_reqInfo.setPatientId(patient.getCdrPatientId());
            //定位搜索
            esb_reqInfo.setRequestFlag(CommonConstant.APP_TOKEN_EQUALS);
            String esb_url = esbServerHostAndPort + urlConfig.getESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST();
            try {
                ResponseEntity<HashMap> postForEntity = restTemplate.postForEntity(esb_url, esb_reqInfo, HashMap.class);
                //获取body
                HashMap ret = postForEntity.getBody();
                //如果主索引返回值正常存在，则获取其索引号当为mpiId，否则用CdrPatientId
                if ("200".equals(ret.get("status"))) {
                    Map dataMap = (LinkedHashMap) ret.get("data");
                    List patientRecordList = (ArrayList) dataMap.get("patientRecord");
                    Map patientRecord1 = (LinkedHashMap) patientRecordList.get(0);
                    mpiId = (String) patientRecord1.get("mpiId");
                }
            } catch (Exception e) {
                logger.warn(urlConfig.getESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST() + "查询主索引出错:"+e.getMessage());
            }
            //判断不是空和不为0
            if (StringUtils.isNotBlank(mpiId) & mpiId != "0") {
                patientMap.put("netEaseId", "p" + mpiId);
            } else {
                patientMap.put("netEaseId", "");
            }
            //字段:患者性别
            if (null == patient.getSex()) {
                    int max1 = 2;
                    int min1 = 1;
                    Random random1 = new Random();
                    int s1 = random1.nextInt(max1) % (max1 - min1 + 1) + min1;
                    patientMap.put("sex", s1);
            }else {
                    patientMap.put("sex", patient.getSex());
                }

        //字段:患者年龄
            if (null == patient.getAge()) {
                int max = 60;
                int min = 20;
                Random random = new Random();
                int s = random.nextInt(max) % (max - min + 1) + min;
                patientMap.put("age", s);
            } else {
                patientMap.put("age", patient.getAge());
            }

        //字段:患者手机号
            String phone = patient.getPhone();
            Set phoneSet = new HashSet<>();
            if (null != phone && phone.length() > 0) {
                String regEx = "^[\\u4e00-\\u9fa5]*$";
                Pattern pattern = Pattern.compile(regEx);
                String[] phoneArray = phone.split("#");
                for (int j = 0; j < phoneArray.length; j++) {
                    if (!pattern.matcher(phoneArray[j]).matches() && !"(null)".equals(phoneArray[j])) {
                        phoneSet.add(phoneArray[j]);
                    }
                }
            }
            patientMap.put("phoneNumber", phoneSet.toArray());

            patientsList.add(patientMap);
        }

        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("total", pagenation.getTotalPage());
        innerDataMap.put("list", patientsList);

        return ResponseMapUtil.getSuccessResultMap(innerDataMap);

    }

    @Override
    public ResponseData queryPatientStatus(ReqInfo reqInfo) {



        String jsonIds= reqInfo.getPatientIds();
        List<Integer> list = JsonUtils.readValue(jsonIds, List.class);
        if (list !=null && list.size()>0){
        List<Map<String,Object>> responsePatients1 = new ArrayList<>();//未排序
        List<Map<String,Object>> responsePatients2 = new ArrayList<>();//按入参排序

        List<Patient> patients =  patientMapper.queryPatientStatus(list);

            for (Patient patient:patients){

             Map<String, Object> patientMap = new HashMap<>();
             patientMap.put("cdrPatientId", patient.getCdrPatientId());
             patientMap.put("pname", patient.getName());

             if (patient.getStatus() == null || "0".equals(patient.getStatus())) {
                patientMap.put("status", "0");
             }
             else {
                patientMap.put("status", "1");
             }

             responsePatients1.add(patientMap);
            }

            for (int i = 0; i <list.size() ; i++) {

            Map<String,Object>  responMap = new HashMap<>();
                Integer integer = list.get(i);
                long patientId = integer.longValue();

                boolean flag = false;
            for (Map<String,Object> map: responsePatients1) {

            if (map.get("cdrPatientId") != null){
            long  cdrPatientId = (long)map.get("cdrPatientId");
            if (cdrPatientId == patientId){
            responsePatients2.add(map);
            flag = true;
                    }
                }

            }
            if (flag == false){
            responMap.put("cdrPatientId", patientId);
            responMap.put("status", "0");
            responsePatients2.add(responMap);
            }

        }
        //---------------

        /*for (int i = 0; i < list.size(); i++) {
            Map<String, Object> patientMap = new HashMap<>();

            if (i < patients.size()) {
            Patient patient = patients.get(i);
            patientMap.put("cdrPatientId", patient.getCdrPatientId());
            patientMap.put("pname", patient.getName());
            if (patient.getStatus() == null || "0".equals(patient.getStatus())) {
                patientMap.put("status", "0");
            } else {
                patientMap.put("status", "1");
            }
            responsePatients1.add(patientMap);

            } else {

            patientMap.put("cdrPatientId", list.get(i));
            patientMap.put("status", "0");
            responsePatients1.add(patientMap);
                }
            }*/

            return ResponseMapUtil.getSuccessResultList(responsePatients2);
        }
      return new ResponseData(200,"患者不存在",null);
    }




}