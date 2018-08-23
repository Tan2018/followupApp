package com.ry.fu.esb.doctorbook.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.http.HttpRequest;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.ResponseUtil;
import com.ry.fu.esb.common.utils.RestTemplateMethodParamUtil;
import com.ry.fu.esb.doctorbook.mapper.*;
import com.ry.fu.esb.doctorbook.model.*;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.model.response.*;
import com.ry.fu.esb.doctorbook.service.DepartmentsService;
import com.ry.fu.esb.doctorbook.service.ShiftWorkerInfoService;
import com.ry.fu.esb.jpush.model.request.PushBarNumberRequest;
import com.ry.fu.esb.jpush.service.DoctoresManualService;
import com.ry.fu.esb.medicaljournal.enums.SliverMedicaneResponseCode;
import com.ry.fu.esb.medicaljournal.model.request.DoctorSearchReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DepartmentsServiceImpl implements DepartmentsService {

    private Logger logger = LoggerFactory.getLogger(DepartmentsServiceImpl.class);

    @Autowired
    private PublicService publicService;

    @Autowired
    private DepartmentsMapper departmentsMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private PatientRecordFileMapper patientRecordFileMapper;

    @Autowired
    private HandInformMapper handInformMapper;

    @Autowired
    private AppointDoctorMapper appointDoctorMapper;

    @Autowired
    private PatientCheckRecordMapper patientCheckRecordMapper;

    @Autowired
    private ShiftWorkInfoMapper shiftWorkInfoMapper;

    @Autowired
    private ShiftWorkerInfoService shiftWorkerInfoService;

    @Autowired
    private DoctoresManualService doctoresManualService;

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    @Override
    public ResponseData queryDepartmentsDoctor(DoctorSearchReq req) throws Exception{
        long t1 = System.currentTimeMillis();
        ResponseData responseData = publicService.query(ESBServiceCode.ALLDOCTORINFOLIST,req, DoctorInfoByNameRecord.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
       /* DoctorByNameInfoRecord record = (DoctorByNameInfoRecord) responseData.getData();
        if(record == null){
            DoctorByNameInfoRecordPage doctorByNameInfoRecordPage = new DoctorByNameInfoRecordPage();
            doctorByNameInfoRecordPage.setRecord(new ArrayList());
            doctorByNameInfoRecordPage.setTotal(0);
            responseData.setData(doctorByNameInfoRecordPage);
            return responseData;
        }*/

        return responseData;
    }

    @Override
    public ResponseData handoverStatistics(Integer orgId,String date,Integer pageSize,Integer pageSum) {

        if(date==null||date.trim()==""){
            Date d = new Date();
            DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
            date = format.format(d);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("orgId",orgId);
        map.put("date",date);
        map.put("pageSize",pageSize);
        map.put("pageSum",pageSum);

        long t1 = System.currentTimeMillis();
        List<HandoverStatisticsInfo> handoverStatisticsInfoList = departmentsMapper.handoverStatistics(map);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));

        ResponseData responseData = new ResponseData();
        responseData.setMsg(SliverMedicaneResponseCode.SUCCESS.getName());
        responseData.setStatus("200");

        HandoverStatisticsResponse handoverStatisticsResponse = new HandoverStatisticsResponse();

        if(handoverStatisticsInfoList!=null&&handoverStatisticsInfoList.size()>0){
            handoverStatisticsResponse.setHandoverStatisticsInfoList(handoverStatisticsInfoList);
            Integer sum = getCount(orgId,date);
            Integer total = sum%pageSize == 0 ? sum/pageSize : (sum/pageSize)+1;
            handoverStatisticsResponse.setTotal(total);
        }else{
            handoverStatisticsResponse.setHandoverStatisticsInfoList(new ArrayList<>());
            handoverStatisticsResponse.setTotal(0);
        }
        responseData.setData(handoverStatisticsResponse);
        return responseData;
    }

    @Override
    public Integer getCount(Integer orgId, String date) {
        return departmentsMapper.getCount(orgId,date);
    }

    @Override
    public ResponseData departmentsStatistics(String date, Integer pageSize, Integer pageSum) {

        if(date==null||date.trim()==""){
            Date d = new Date();
            DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
            date = format.format(d);
        }

        ResponseData responseData = new ResponseData();
        responseData.setStatus("200");
        responseData.setMsg(SliverMedicaneResponseCode.SUCCESS.getName());
        long t1 = System.currentTimeMillis();
        Map<String,Object> map = new HashMap<>();
        map.put("flag",1);
        map.put("pageSize",pageSize);
        map.put("date",date);
        map.put("pageSum",pageSum);
        List<DepartmentsStatisticsInfo> list = departmentsMapper.departmentsStatistics(map);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));

        List<String> list2 = new ArrayList<>();

        long t3 = System.currentTimeMillis();
        for(DepartmentsStatisticsInfo info : list){
            if(!(list2.contains(info.getOrgName()))){
                list2.add(info.getOrgName());
            }
        }

        DepartmentsStatisticsResponse departmentsStatisticsResponse = new DepartmentsStatisticsResponse();

        List<DepartmentsStatisticsRecord> list3 = new ArrayList<>();
        for(String str : list2){
            DepartmentsStatisticsRecord departmentsStatisticsRecord = new DepartmentsStatisticsRecord();
            departmentsStatisticsRecord.setDepName(str);
            List<DepartmentsStatisticsInfo> list4 = new ArrayList<>();
            for(DepartmentsStatisticsInfo info : list){
                if(info.getOrgName().equals(str)){
                    list4.add(info);
                }
            }
            departmentsStatisticsRecord.setList(list4);
            list3.add(departmentsStatisticsRecord);
        }
        long t4 = System.currentTimeMillis();
        logger.info("=====响应时间t4=" + (t4) + "---响应时长---" + (t4 - t3));
        departmentsStatisticsResponse.setHandoverStatisticsInfoList(list3);
        responseData.setData(departmentsStatisticsResponse);
        if(list3.size()>0){
            Integer size = getDepCount(date);
            Integer sum = size%pageSize == 0 ? size/pageSize : (size/pageSize)+1;
            departmentsStatisticsResponse.setTotal(sum);
        }else{
            departmentsStatisticsResponse.setHandoverStatisticsInfoList(new ArrayList<>());
            departmentsStatisticsResponse.setTotal(0);
        }


        return responseData;
    }

    @Override
    public Integer getDepCount(String date) {
        return departmentsMapper.getDepCount(date);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData addDepartmentsLog(DepartmentsLogRequest departmentsLogRequest) {
        logger.info("departmentsLogRequest"+JsonUtils.toJSon(departmentsLogRequest));
        long t1 = System.currentTimeMillis();
        //ResponseData responseData = new ResponseData();

        ShiftWorkInfo shiftWorkInfo = new ShiftWorkInfo();
        String shiftWorkInfoSeq = BeanMapUtils.getTableSeqKey(shiftWorkInfo);
        Long shiftWorkInfoId = redisIncrementGenerator.increment(shiftWorkInfoSeq, 1);
        shiftWorkInfo.setId(shiftWorkInfoId);
        shiftWorkInfo.setShiftDeptId(departmentsLogRequest.getDepId());
        //shiftWorkInfo.setShiftDeptName(departmentsLogRequest.getD);
        shiftWorkInfo.setShiftDoctor(departmentsLogRequest.getShiftDoctor());
        shiftWorkInfo.setShiftDoctorName(departmentsLogRequest.getShiftDoctorName());
        shiftWorkInfo.setShiftTime(new Date());
        shiftWorkInfo.setShiftState("0");
        shiftWorkInfo.setPatientId(-1L);
        shiftWorkInfo.setHospitalPatientId(shiftWorkInfoId);
        //shiftWorkInfo.setTakeDeptId(departmentsLogRequest.ge);
        //shiftWorkInfo.setTakeDeptName(departmentsLogRequest.get);
        shiftWorkInfo.setTakeDoctor(departmentsLogRequest.getTakeDoctor());
        shiftWorkInfo.setTakeDoctorName(departmentsLogRequest.getTakeDoctorName());
        shiftWorkInfo.setSubShiftDoctorId(StringUtils.isNumeric(departmentsLogRequest.getSubShiftDoctorId()) ? Long.valueOf(departmentsLogRequest.getSubShiftDoctorId()):null);
        shiftWorkInfo.setSubShiftDoctorName(departmentsLogRequest.getSubShiftDoctorName());
        shiftWorkInfo.setSupTakeDoctorId(StringUtils.isNumeric(departmentsLogRequest.getSupTakeDoctorId()) ? Long.valueOf(departmentsLogRequest.getSupTakeDoctorId()):null);
        shiftWorkInfo.setSupTakeDoctorName(departmentsLogRequest.getSupTakeDoctorName());
        shiftWorkInfo.setShiftWords(departmentsLogRequest.getDesc());
        shiftWorkInfo.setShiftFlag("2");
        shiftWorkInfoMapper.insertSelective(shiftWorkInfo);

        //调用保存文件的接口
        PatientCheckRecordFileRequest patientCheckRecordFileRequest = new PatientCheckRecordFileRequest();
        patientCheckRecordFileRequest.setPatientRecordId(String.valueOf(shiftWorkInfoId));
        patientCheckRecordFileRequest.setShiftPicture(departmentsLogRequest.getPictures());
        patientCheckRecordFileRequest.setShiftVoice(departmentsLogRequest.getMusics());
        shiftWorkerInfoService.uploadFile(patientCheckRecordFileRequest);

        HandInform handInform = new HandInform();
        String handInformSeq = BeanMapUtils.getTableSeqKey(handInform);
        Long handInformId = redisIncrementGenerator.increment(handInformSeq, 1);
        handInform.setId(handInformId);
        handInform.setCreateDate(new Date());
        handInformMapper.insertSelective(handInform);

        String titleContent = "交班值班通知";
        String pushInfo =null;
        if(StringUtils.isBlank(departmentsLogRequest.getSubShiftDoctorName())){
            pushInfo = departmentsLogRequest.getShiftDoctorName() + "医生已向你交班";
        }else {
            pushInfo =( departmentsLogRequest.getSubShiftDoctorName()+"/"+departmentsLogRequest.getShiftDoctorName()+"医生已向你交班");
        }
        Map<String, String> map = new HashMap<>(2);
        map.put("noticeType", "2");
        Map<String, Object> params = new HashMap<>();
        params.put("doctorbook","1");//推送的app
        if(!"pro".equals(env.getActiveProfiles()[0])) {
            params.put("pushTo", "1");
        }
        params.put("title",titleContent);
        params.put("notification_title",pushInfo);
        params.put("alias",String.valueOf(departmentsLogRequest.getTakeDoctor()));
        params.put("extras",map);
        String result= null;
        Header header = restTemplateMethodParamUtil.getPostHeader(params);

        result= HttpRequest.post(netServerHostAndPort + Constants.JPUSH_ADDRESS, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON, header);
        //ResponseData responseData1 = JsonUtils.readValue(result, ResponseData.class);
        //ResponseData responseData1 = JsonUtils.readValue(result, ResponseData.class);
        logger.info("交班推送入参"+params+"结果"+result);
        PushBarNumberRequest pushBarNumberRequest = new PushBarNumberRequest();
        pushBarNumberRequest.setPersonId(String.valueOf(departmentsLogRequest.getTakeDoctor()));
        pushBarNumberRequest.setNoticeType(2);
        doctoresManualService.savePushBarNumber(pushBarNumberRequest);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        return ResponseUtil.getSuccessResultBean(null);
    }

    @Override
    public ResponseData addOtherDep(OtherDepRequest otherDepRequest) {
        OtherDepInfo otherDepInfo = new OtherDepInfo();
        String seq = BeanMapUtils.getTableSeqKey(otherDepInfo);
        Long id = redisIncrementGenerator.increment(seq,1);
        otherDepInfo.setId(id);
        otherDepInfo.setBedNo(otherDepRequest.getBedNo().toString());
        otherDepInfo.setHospitalDay(otherDepRequest.getHospitalDay());
        otherDepInfo.setPAge(otherDepRequest.getPAge());
        otherDepInfo.setPatientId(otherDepRequest.getPatientId().toString());
        otherDepInfo.setPatientName(otherDepRequest.getPatientName());
        otherDepInfo.setPSex(otherDepRequest.getPSex());
        otherDepInfo.setTakeDoctor(otherDepRequest.getTakeDoctor().toString());
        otherDepInfo.setShiftTime(new Date());
        otherDepInfo.setStState(0);
        otherDepInfo.setStrDiagnosis(otherDepRequest.getStrDiagnosis());

        ResponseData responseData = new ResponseData();
        responseData.setStatus("200");
        responseData.setMsg(SliverMedicaneResponseCode.SUCCESS.getName());
        long t1 = System.currentTimeMillis();
        responseData.setData(departmentsMapper.addOtherDep(otherDepInfo));
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));


        return responseData;
    }

    @Override
    public ResponseData queryOtherDepPatient(QueryOtherDepPatientRequest queryOtherDepPatientRequest) {
        OtherDepPatientResponse otherDepPatientResponse = new OtherDepPatientResponse();
        long t1 = System.currentTimeMillis();
        List<OtherDepInfo> otherDepInfoList = departmentsMapper.queryOtherDepPatient(queryOtherDepPatientRequest);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        otherDepPatientResponse.setOtherDepInfoList(otherDepInfoList);
        ResponseData responseData = new ResponseData();
        responseData.setMsg(SliverMedicaneResponseCode.SUCCESS.getName());
        responseData.setStatus("200");
        responseData.setData(otherDepPatientResponse);
        return responseData;
    }

    @Override
    public ResponseData queryDepLog(QueryOtherDepPatientRequest queryOtherDepPatientRequest) {
        //Map<String,Object> map = new HashMap<>();
        /*map.put("pageSize",queryOtherDepPatientRequest.getPageSize());
        map.put("pageSum",queryOtherDepPatientRequest.getPageSum());
        map.put("depId",queryOtherDepPatientRequest.getDepId());*/
        long t1 = System.currentTimeMillis();

        List<Map<String, Object>> handoverStatisticsInfoList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        Example example = new Example(ShiftWorkInfo.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("shift_Time desc");
        if(queryOtherDepPatientRequest.getDeptLevel()!= null && 1==(queryOtherDepPatientRequest.getDeptLevel())){
            List<String> secondOrgList = shiftWorkInfoMapper.findSecondOrgList(queryOtherDepPatientRequest.getDepId());
            if(secondOrgList != null && secondOrgList.size() >0){
                criteria.andIn("shiftDeptId",secondOrgList);
            }else {
                criteria.andEqualTo("id",null);
            }
        }else {
            criteria.andEqualTo("shiftDeptId",queryOtherDepPatientRequest.getDepId());
        }
        criteria.andEqualTo("shiftFlag",2);
        PageHelper.startPage(queryOtherDepPatientRequest.getPageSum(), queryOtherDepPatientRequest.getPageSize());
        List<ShiftWorkInfo> shiftWorkInfoList = shiftWorkInfoMapper.selectByExample(example);
        PageInfo<ShiftWorkInfo> pageInfo = new PageInfo<>(shiftWorkInfoList);

        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            for (ShiftWorkInfo shiftWorkInfo1 : pageInfo.getList()) {
                //查询文件信息
                List<PatientRecordFile> patientRecordFiles = patientRecordFileMapper.findFileByRecordId(shiftWorkInfo1.getId());
                List<String> shiftPictureList = new ArrayList<>();
                List<String> shiftVoiceList = new ArrayList<>();

                if (patientRecordFiles != null && patientRecordFiles.size() > 0) {
                    for (PatientRecordFile patientRecordFile : patientRecordFiles) {
                        if ("0".equals(patientRecordFile.getFlag())) {
                            shiftPictureList.add(patientRecordFile.getFilePath());
                        } else if ("1".equals(patientRecordFile.getFlag())) {
                            shiftVoiceList.add(patientRecordFile.getFilePath());
                        }
                    }
                }
                Map<String, Object> map = new HashMap<>();
                map.put("id",shiftWorkInfo1.getId());
                map.put("shiftDoctor", shiftWorkInfo1.getShiftDoctor());
                map.put("chName", shiftWorkInfo1.getShiftDoctorName());
                map.put("shiftTime", shiftWorkInfo1.getShiftTime());
                map.put("pictures", shiftPictureList);
                if(StringUtils.isBlank(shiftWorkInfo1.getSubShiftDoctorName())){
                    map.put("doctorName", shiftWorkInfo1.getShiftDoctorName());
                }else {
                    map.put("doctorName", shiftWorkInfo1.getSubShiftDoctorName()+"/"+shiftWorkInfo1.getShiftDoctorName()+"医生");
                }
                map.put("musics", shiftVoiceList);
                map.put("describe", shiftWorkInfo1.getShiftWords());
                handoverStatisticsInfoList.add(map);

            }
        }
        result.put("total", pageInfo.getPages());
        /*List<DepartmentsStatisticsInfoLog> list = departmentsMapper.departmentsStatisticsLog(map);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        ResponseData responseData = new ResponseData();
        responseData.setMsg(SliverMedicaneResponseCode.SUCCESS.getName());
        responseData.setStatus("200");

        DepartmentsStatisticsLogReponse departmentsStatisticsLogReponse = new DepartmentsStatisticsLogReponse();
        departmentsStatisticsLogReponse.setHandoverStatisticsInfoList(list);
        if(list.size()>0){
            Integer size = departmentsMapper.getDepLogCount();
            Integer total = size%queryOtherDepPatientRequest.getPageSize() == 0 ? size/queryOtherDepPatientRequest.getPageSize():(size/queryOtherDepPatientRequest.getPageSize()+1);
            departmentsStatisticsLogReponse.setTotal(total);
        }else{
            departmentsStatisticsLogReponse.setTotal(0);
        }


        responseData.setData(departmentsStatisticsLogReponse);*/
        result.put("handoverStatisticsInfoList", handoverStatisticsInfoList);
        return ResponseUtil.getSuccessResultBean(result);
    }

    @Override
    public ResponseData queryAllDep() {
        List<DepDoctor> list = departmentsMapper.queryAllDep();
        DepAllResponse depAllResponse = new DepAllResponse();
        ResponseData responseData = new ResponseData();
        responseData.setMsg(SliverMedicaneResponseCode.SUCCESS.getName());
        responseData.setStatus("200");
        if(list.size()<=0){
            depAllResponse.setList(new ArrayList<>());
        }else{
            depAllResponse.setList(list);

        }
        responseData.setData(depAllResponse);
        return responseData;
    }

    @Override
    public ResponseData updateState(Long id) {
        ResponseData responseData = new ResponseData();
        responseData.setMsg(SliverMedicaneResponseCode.SUCCESS.getName());
        responseData.setStatus("200");
        Integer result = departmentsMapper.updateState(id,new Date());
        responseData.setData(result);
        return responseData;
    }

    @Override
    public ResponseData otherDepInfoLog(OtherDepInfoLogRequest otherDepInfoLogRequest) {
        ResponseData responseData = new ResponseData();
        responseData.setMsg(SliverMedicaneResponseCode.SUCCESS.getName());
        responseData.setStatus("200");
        OtherDepInfoLogReponse otherDepInfoLogReponse = new OtherDepInfoLogReponse();
        Map<String,Object> map = new HashMap<>();
        map.put("types",otherDepInfoLogRequest.getType());
        map.put("patientId",otherDepInfoLogRequest.getPatientId());
        map.put("shiftDoctor",otherDepInfoLogRequest.getShiftDoctor());
        map.put("pageSize",otherDepInfoLogRequest.getPageSize());
        map.put("pageSum",otherDepInfoLogRequest.getPageSum());
        String groupId = "";
        if(otherDepInfoLogRequest.getGroupId() == null||otherDepInfoLogRequest.getGroupId().trim()==""){
            groupId = " ";
        }else{
            groupId = otherDepInfoLogRequest.getGroupId();
        }
        map.put("orgId",otherDepInfoLogRequest.getOrgId());
        map.put("groupId",groupId);
        List<OtherDepInfoLog> list =  departmentsMapper.otherDepInfoLog(map);
        otherDepInfoLogReponse.setOtherDepInfoLogList(list);
        responseData.setData(otherDepInfoLogReponse);
        Integer size = departmentsMapper.queryOtherDepInfoLogCount(map);
        if(size<=0){
            otherDepInfoLogReponse.setTotal(0);
        }else{
           Integer total = size%otherDepInfoLogRequest.getPageSize() == 0 ? size/otherDepInfoLogRequest.getPageSize():(size/otherDepInfoLogRequest.getPageSize()+1);
           otherDepInfoLogReponse.setTotal(total);
        }

        return responseData;
    }
}
