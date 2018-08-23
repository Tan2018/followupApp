package com.ry.fu.esb.medicaljournal.service.impl;
import com.ry.fu.esb.common.config.UrlConfig;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.ResponseUtil;
import com.ry.fu.esb.common.utils.RestTemplateMethodParamUtil;
import com.ry.fu.esb.doctorbook.model.request.PatientQueryRequest;
import com.ry.fu.esb.doctorbook.model.response.PatientQueryRecord;
import com.ry.fu.esb.doctorbook.service.IPService;
import com.ry.fu.esb.medicaljournal.enums.PushType;
import com.ry.fu.esb.medicaljournal.mapper.ContactsMapper;
import com.ry.fu.esb.medicaljournal.mapper.PushListMapper;
import com.ry.fu.esb.medicaljournal.mapper.RegistrationMapper;
import com.ry.fu.esb.medicaljournal.model.PushList;
import com.ry.fu.esb.medicaljournal.service.FollowupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jackson on 2018/5/2.
 */
@Service
public class FollowupServiceImpl implements FollowupService {

    private static Logger logger = LoggerFactory.getLogger(FollowupServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private PushListMapper pushListMapper;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    IPService ipService;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;


    @Override
    public ResponseData sendSurvey(Map<String, String> retMap) {

        Boolean falg = false; //有一次推送成功则返回成功

        String title = "随访调查";//安卓端标题
        String notification_title = "随访调查";
        String patientId = retMap.get("patientId");
        List<String> accountIdList= contactsMapper.queryAccountIdListByEsbPatientId(patientId);
        if (accountIdList == null || accountIdList.size()<1){
            return new ResponseData("200","推送失败,患者未注册",null);
        }
        String flowProgramInstanceId = retMap.get("flowProgramInstanceId");

        Map<String,String> extrasparam = new HashMap<String, String>();
        extrasparam.put("flowProgramInstanceId",flowProgramInstanceId);
        Map<String,Object> data = new HashMap<>();
        data.put("flowProgramInstanceId",flowProgramInstanceId);
        data.put("pushType",PushType.FUSURVEY.getIndex()+"");
        StringBuilder json = JsonUtils.mapToJson(data,null);
        Map<String,String> extras = new HashMap<>();
        extras.put("data", String.valueOf(json));
        extras.put("pushType",PushType.FUSURVEY.getIndex()+"");

        String timeStamp = System.currentTimeMillis()+"";
        Map<String, Object> params = new HashMap<>();
        params.put("doctorbook",0);//推送的给就医日志app
        params.put("title", title);
        params.put("notification_title", notification_title);
        params.put("extras", extras);
        params.put("aliaes", accountIdList);
        params.put("pushType",PushType.FUSURVEY.getIndex());
        params.put("timeStamp", timeStamp);
        if(!"pro".equals(env.getActiveProfiles()[0])) {
            params.put("pushTo", 1);
        }
        String profile = env.getActiveProfiles()[0];
        //todo 用restmplate发起请求
        HttpEntity<String> formEntity = restTemplateMethodParamUtil.getPostRequestParams(JsonUtils.toJSon(params));
        long tt1 = System.currentTimeMillis();
        String result = restTemplate.postForObject(netServerHostAndPort+urlConfig.getNET_SEND_PUSH_URL(), formEntity, String.class);
        long tt2 = System.currentTimeMillis();
        logger.info("==请求推送===响应时间t2=" + (tt2) + ",---响应时长---" + (tt2 - tt1));

        for(String accountId:accountIdList) {
            ResponseData responseData1 = JsonUtils.readValue(result, ResponseData.class);
            if (responseData1.getData().toString().equals("1")) {
                falg=true;
                unifiedOrdinarySave(accountId,patientId, title, notification_title, PushType.FUSURVEY.getIndex(),data);
            }

        }
        if(falg){
            return ResponseUtil.getSuccessResultBean(null);
        }else{
            return ResponseUtil.getFailResultMapMap();
        }

    }

    @Override
    public ResponseData sendGroupInfo(Map<String, String> retMap) {
        String accountCode = retMap.get("accountCode");
        String data = retMap.get("data");
        String title = "项目审核";//安卓端标题
        String notification_title = "项目审核";
        Map<String,String> extras = new HashMap<>();
        extras.put("data", data);
      //  pushPayload = JPushUtil.buildPushObject_android_ios_alias(accountCode, notification_title, title,extras,PushType.FUGROUPAPPLICATION.getIndex());
        //logger.info(pushPayload.toString());
        //unifiedOrdinarySave(accountCode,title,notification_title,PushType.FUSURVEY.getIndex(),data);
        return null;
    }

    /**
     *
     * @param retMap
     * @return
     */
    @Override
    public ResponseData senfExamination(Map<String, Object> retMap) throws Exception {

        String projectId = null;
        String projectName = null;
        if(retMap.get("projectId")!=null) {
            projectId = (String) retMap.get("projectId");
        }
        if (retMap.get("projectName")!=null){
            projectName = (String) retMap.get("projectName");
        }

        Boolean falg = false; //有一次推送成功则返回成功
        String doctorName =(String) retMap.get("doctorName");
        String title = "复诊通知";//安卓端标题
        String notification_title = null;
        String ipseqno = (String) retMap.get("ipseqno");

        /**
         * 获取patientId,参数ipseqno
         */
        PatientQueryRequest request = new PatientQueryRequest();
        request.setQueryValue(ipseqno);
        ResponseData results = ipService.hospitalPatients(request);

        if(results.getData() == null){
            return new ResponseData("200","推送失败,患者未注册",null);
        }

        List<PatientQueryRecord >  patientList = (List)results.getData();

        PatientQueryRecord patientQueryRecord = patientList.get(0);
        String patientId = patientQueryRecord.getPatientId();
        String patientName = patientQueryRecord.getPatientName();
        notification_title = patientName+"你收到一个复诊通知";

        List<String> accountIdList= contactsMapper.queryAccountIdListByEsbPatientId(patientId);
        if (accountIdList == null || accountIdList.size()<1){
            return new ResponseData("200","推送失败,患者未注册",null);
        }
        Map<String,String> extrasparam = new HashMap<String, String>();
        Map<String,Object> data = new HashMap<>();

        if(projectName  ==null ||projectId ==null){
            return new ResponseData("200","推送失败,项目名称不存在",null);
        }

        data.put("projectName",projectName);
        data.put("projectId",projectId);
        data.put("doctorId",retMap.get("doctorId"));
        data.put("desc",retMap.get("desc"));
        data.put("patientId",patientId);
        StringBuilder json = JsonUtils.mapToJson(data,null);
        Map<String,String> extras = new HashMap<>();
        extras.put("data", String.valueOf(json));
        extras.put("pushType",PushType.FUEXAMINATION.getIndex()+"");

        Map<String, Object> paramters = new HashMap<>();
        paramters.put("aliaes",accountIdList);
        paramters.put("title",title);
        paramters.put("extras",extras);
        paramters.put("pushType",PushType.FUEXAMINATION.getIndex());
        paramters.put("notification_title",notification_title);
        paramters.put("extras",extras);
        paramters.put("doctorbook",0);//推送的给就医日志app
        String timeStamp = System.currentTimeMillis()+"";
        paramters.put("timeStamp",timeStamp);
        if(!"pro".equals(env.getActiveProfiles()[0])) {
            paramters.put("pushTo", 1);

        }
        String profile = env.getActiveProfiles()[0];
        logger.info("------指定推送环境:-------------"+profile+"-----------指定推送环境-------------");
        String postDatas = JsonUtils.toJSon(paramters);
        //todo 用restmplate发起请求
        HttpEntity<String> postRequestParams = restTemplateMethodParamUtil.getPostRequestParams(postDatas);
        logger.info(paramters.toString());
        long tt1 = System.currentTimeMillis();
        String result = restTemplate.postForObject(netServerHostAndPort+urlConfig.getNET_SEND_PUSH_URL(), postRequestParams, String.class);
        long tt2 = System.currentTimeMillis();
        logger.info("==请求推送===响应时间t2=" + (tt2) + ",---响应时长---" + (tt2 - tt1));

        ResponseData responseData1 = JsonUtils.readValue(result, ResponseData.class);

        for(String accountId:accountIdList) {

        if (responseData1.getData().toString().equals("1")) {
            falg=true;
            unifiedOrdinarySave(accountId,patientId, title, notification_title, PushType.FUEXAMINATION.getIndex(),data);
        }
        }
        Map<String,Object> innerData = new HashMap<>();
        if(falg){
            innerData.put("pushStatus","200");
            innerData.put("pushMsg","推送成功");
            return ResponseUtil.getSuccessResultMap(innerData);
        }else{
            Map<String,Object> inner = new HashMap<>();
            inner.put("pushStatus","500");
            inner.put("pushMsg","患者未注册");
            return new ResponseData("500", "患者未注册",inner);
        }
    }

    public void unifiedOrdinarySave(String openId,String patientId, String title, String notification_title, Integer pushType,Map<String,Object> data){
            logger.info("========消息推送成功!==========");
            //推送成功后保存到数据库中
            PushList pushList = new PushList();
            String pushListKey = BeanMapUtils.getTableSeqKey(pushList);
            Long pushListId = redisIncrementGenerator.increment(pushListKey, 1);
            pushList.setId(pushListId);
            pushList.setPushType(pushType.toString());
            pushList.setAlias(openId);
            pushList.setTitle(title);
            pushList.setContent(notification_title);
            pushList.setPushTime(new Date());
            pushList.setPatientId(patientId);
            Map<String,Object> extras = new HashMap<>();
            extras.put("data",JsonUtils.toJSon(data));
            pushList.setExtras(JsonUtils.toJSon(extras));
            logger.info(pushList.toString());
            pushListMapper.insertSelective(pushList);

    }

    /**
     * 获取今天和明天的就诊日期
     * @param retMap
     * @return
     * @throws ESBException
     *
     *
     */
    @Override
    public ResponseData queryFollowUpRegistered(Map<String,Object> retMap) throws ESBException, ParseException {

        Map<String,Object> innerDate = new HashMap<>();
        List<Map<String,Object>> todayReg = new ArrayList<>();
        List<Map<String,Object>> tomorrowReg = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentFormatDate = simpleDateFormat.format(new Date());
        Date currentDate = simpleDateFormat.parse(currentFormatDate);

        if(retMap.get("doctorId")!=null){
            long doctorId = (Integer) retMap.get("doctorId");
            List<Map<String,Object>> registrationList = registrationMapper.selectFollowUpRegistration(doctorId);

            for (Map<String,Object> mapReg:registrationList){
                mapReg.put("status","已挂号");

            }
            // 根据时间区分挂号提醒
            for (Map<String,Object> mapReg:registrationList){

                Object visitDate = mapReg.get("visitDate");
                if(visitDate!=null){
                    Date vDate = simpleDateFormat.parse(visitDate.toString());
                    String formatVDate = simpleDateFormat.format(vDate);
                    mapReg.put("visitDate",formatVDate);

                    //String formatTime = simpleDateFormat.format(visitTime.toString());
                    Long deadline = ( vDate.getTime()-currentDate.getTime())/(1000*3600*24);
                    logger.info("vDate="+Long.toString(vDate.getTime()));
                    logger.info("currentDate="+Long.toString(currentDate.getTime()));

                    if(deadline==0){
                        todayReg.add(mapReg);
                    }else if (deadline==1){
                        tomorrowReg.add(mapReg);
                    }
                }
            }
            innerDate.put("todayReg",todayReg);
            innerDate.put("tomorrowReg",tomorrowReg);
            logger.info("{}",registrationList);

            return  new ResponseData("200","请求成功",innerDate);

        }else{
            return new ResponseData("500","账号不存在",null);
        }
    }
}
