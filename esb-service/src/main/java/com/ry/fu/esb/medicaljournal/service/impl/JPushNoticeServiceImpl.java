package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.event.JPushEventService;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.utils.RestTemplateMethodParamUtil;
import com.ry.fu.esb.medicaljournal.enums.PushType;
import com.ry.fu.esb.medicaljournal.mapper.ContactsMapper;
import com.ry.fu.esb.medicaljournal.mapper.PatientMapper;
import com.ry.fu.esb.medicaljournal.mapper.PushListMapper;
import com.ry.fu.esb.medicaljournal.model.Contacts;
import com.ry.fu.esb.medicaljournal.model.Patient;
import com.ry.fu.esb.medicaljournal.service.JPushNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JPushNoticeServiceImpl implements JPushNoticeService {

    private static Logger logger = LoggerFactory.getLogger(JPushNoticeServiceImpl.class);

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private PushListMapper pushListMapper;

    @Autowired
    public ContactsMapper contactsMapper;

    @Autowired
    public PatientMapper patientMapper;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;

    @Autowired
    private Environment env;

    @Autowired
    private JPushEventService jPushEventService;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendUnpaidPush(String openId,String patientId,String orderId,String time) {
        String title = "待支付订单";
        String  notification_title = "尊敬的用户，您有一个订单还未支付，请在"+time+"前完成支付，逾期该订单将会作废。";
        saveAndPush(openId,title,notification_title,PushType.UNPAID.getIndex(), patientId,orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendRegistrationSuccess(String openId,String patientId,String userName,String orderId){

        String title = "支付成功";//安卓端标题
        String notification_title = userName + "您好，您的挂号已成功。请于就诊当日至少提前半个小时到指定地点候诊。";
        saveAndPush(openId,title,notification_title,PushType.REGISTERSUCCESS.getIndex(), null,orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendPaymentSuccess(String openId, String userName, String place,String orderId) {
        String title = "缴费成功";//安卓端标题
        String notification_title = userName + "您好，您完成了一笔门诊缴费。请您到" + place + "";
        saveAndPush(openId,title,notification_title,PushType.PAYSUCCESS.getIndex(),null,orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refundSuccessNotice(String openId, String userName, String orderId) {
        String title = "退费成功";
        String notification_title = "尊敬的"+userName + "，您的订单"+orderId+"已退费成功,退款会1-5天内原路退回到你的支付账号，敬请留意。";
        saveAndPush(openId,title,notification_title, PushType.REFUNDSUCCESS.getIndex(),null,orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refundFailureNotice(String openId, String userName, String orderId, String reason) {
        String title = "退费失败";
        //TODO:电话号码
        String notification_title = "尊敬的"+userName + "，您的订单"+orderId+"退费失败。如有疑问，请拨打电话:020-81332199";
        saveAndPush(openId,title,notification_title, PushType.REFUNDFAILURE.getIndex(),null,orderId);

    }


    @Override
    public void sendAppointment(String openId, String name, String visitTime, String consultationRoom) {
        String title = "就诊通知";//安卓端标题
        String startTime = StringUtils.substring(visitTime,0,2);
        //下午的号,当天通知
        String date = null;
        if(startTime != null && Integer.parseInt(startTime)>12){
             date = "今天下午";
        }else {
             date = "明天上午";
        }
        String notification_title = "尊敬的"+name+"，您预约了" + date+visitTime+ "的就诊,请您提前到" + consultationRoom + "候诊。";
        saveAndPush(openId, title, notification_title,PushType.APPOINTMENT.getIndex(),null,null);
    }

    @Override
    public void sendCriticalValues(Long lisLableNo, Long examineRequestID, Long patientId, String patientName, Long personId, String personNo, Long noTelevel, Long noteType, Long noteTime, Long itemID, String itemCode, String itemName, Date receiveDate) {

    }

    @Override
    public void sendCriticalValuesHandler(Long lisLableNo, Long examineRequestID, Long patientId, String patientName, Long disposeWay, String disposeEmployeeNo, String disposeEmployeeName, Date disposeDatetime, String disposeTypeCode, String disposeTypeName, String disposeDescribe, Long itemID, String itemCode, String itemName, Long disposeMinutes) {

    }

    @Override
    public void sendCriticalValuesRelieve(Long examineRequestId, String statreMark, Date optm, String results) {

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendPushTheItem(String patientId, String patientName, String orderId, String time,String cause,Integer pushType) {
        String title = "催交款通知";
        String  notification_title = patientName+"您好，您于"+time+"有订单号为"+orderId+"的订单,因"+cause+"原因未缴费成功,请尽快完成缴费";
        Patient patient=patientMapper.selectByESBPatientId(patientId);
        if(patient!=null){
            List<Contacts> contacts=contactsMapper.selectByPatientId(patient.getId());
            List<String> openIds=new ArrayList<>();
            for (Contacts contact:contacts
                 ) {
                openIds.add(contact.getAccountId().toString());
            }
            //saveAndPush(openIds,title,notification_title,pushType, patientId,orderId);
            jPushEventService.saveAndPush(openIds,title,notification_title,pushType, patientId,orderId);

        }
    }

    public void saveAndPush(List<String> openId, String title, String notification_title, Integer pushType,String patientId,String orderId){

       /* Map<String, String> extras=null;
        Map<String, String> extrasparam=new HashMap<String, String>();
        for (int i=0;i<openId.size();i++){
            PushList pushList = new PushList();
            String pushListKey = BeanMapUtils.getTableSeqKey(pushList);
            Long pushListId = redisIncrementGenerator.increment(pushListKey, 1);
            pushList.setId(pushListId);
            pushList.setAlias(openId.get(i));
            pushList.setTitle(title);
            pushList.setContent(notification_title);
            pushList.setPushTime(new Date());
            pushList.setPushType(pushType.toString());
            extras = new HashMap<>();
            extrasparam.put("patientId", patientId );
            extrasparam.put("orderId", orderId);
            extras.put("pushType", String.valueOf(pushType));
            extras.put("data", JsonUtils.toJSon(extrasparam));
            pushList.setExtras(JsonUtils.toJSon(extras));
            pushListMapper.insertSelective(pushList);
        }
        Map params = new HashMap<>();
        params.put("doctorbook","0");//推送的app
        params.put("aliaes", openId);
        params.put("title", title);
        params.put("notification_title", notification_title);
        params.put("extras", extras);
        if(!"pro".equals(env.getActiveProfiles()[0])) {
            params.put("pushTo", "1");
        }
        Header header = restTemplateMethodParamUtil.getPostHeader(params);
        String result = null;
        try {
            result = HttpRequest.post(netServerHostAndPort + Constants.JPUSH_ADDRESS, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON,header);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("极光推送错误");
        }
        //ResponseData responseData = JsonUtils.readValue(result, ResponseData.class);
        logger.info("推送结果"+result);*/
    }

    public void saveAndPush(String openId, String title, String notification_title, Integer pushType,String patientId,String orderId){
        List<String> list=new ArrayList<>();
        list.add(openId);
        jPushEventService.saveAndPush(list,title,notification_title,pushType, patientId,orderId);

    }

}
