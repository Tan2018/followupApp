package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.event.JPushEventService;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.properties.RSAKeyProperties;
import com.ry.fu.esb.common.properties.SystemProperties;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.medicaljournal.constants.SilverMedicineConstants;
import com.ry.fu.esb.medicaljournal.mapper.*;
import com.ry.fu.esb.medicaljournal.model.*;
import com.ry.fu.esb.medicaljournal.model.request.CancelRegisterRequest;
import com.ry.fu.esb.medicaljournal.model.request.PayRequest;
import com.ry.fu.esb.medicaljournal.model.request.RegisteredQueueRequest;
import com.ry.fu.esb.medicaljournal.model.request.RegistrationDetailRequest;
import com.ry.fu.esb.medicaljournal.model.response.*;
import com.ry.fu.esb.medicaljournal.service.JPushNoticeService;
import com.ry.fu.esb.medicaljournal.service.MedicalService;
import com.ry.fu.esb.medicaljournal.service.OrderService;
import com.ry.fu.esb.medicaljournal.util.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/4/2 10:38
 * @description 订单服务接口实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PublicService publicService;

    @Autowired
    private MedicalService medicalService;

    @Autowired
    private JPushNoticeService jPushNoticeService;

    @Autowired
    private RegOrderMapper regOrderMapper;

    @Autowired
    private FeeTypeMapper feeTypeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private PaysuccessRecordMapper paysuccessRecordMapper;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private RSAKeyProperties rsaKeyProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JPushEventService jPushEventService;

    @Autowired
    private RegistrationMapper registrationMapper;

    /**
     * 取消未支付订单
     *
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean cancleReg() throws ESBException, SystemException {
        //获取 orderStatus = 0 未支付的，feeType=挂号费 挂号费的，定时取消
        List<Order> orders = orderMapper.selectNeedCancel(DateFormatUtils.format(new Date(), Constants.DATE_YYYY_MM_DD_HH_MM_SS));
        if (orders != null && orders.size() > 0) {
            for (Order order : orders) {

               /* //查询支付
                Map<String, String> searchParam = new HashMap<String, String>();
                searchParam.put("outOrderNo", order.getId());
                searchParam.put("appId", systemProperties.getCacherAppId());
                searchParam.put("merchantNo", systemProperties.getPaymentMarchantNo());
                searchParam.put("nonceStr", UUID.randomUUID().toString().replace("-", ""));
                searchParam.put("signMode", SignatureUtils.SIGN_TYPE_RSA);
                searchParam.put("timeStamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                //待加密字符串
                String content = SignatureUtils.getSortContent(searchParam);
                String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getCasherPrivateKey());
                searchParam.put("sign", sign);
                ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getPaymentHost() + SilverMedicineConstants.PAY_CASH_QUERY_URL, searchParam, HashMap.class);
                HashMap<String, String> dataMap = respData.getBody();
                if(dataMap != null && dataMap.get("returnCode") != null && "SUCCESS".equals(dataMap.get("returnCode"))
                        && "PAYMENT".equals(dataMap.get("tradeState"))) {
                    //主动轮训，同步状态
                    order.setStatusRemark("定时取消的时候主动轮训同步为已支付");
                    order.setUpdateTime(new Date());
                    order.setOrderStatus("1");
                    order.setPaySuccessTime(new Date());
                    order.setPayType(String.valueOf(dataMap.get("channelCode")));
                    order.setTradeNo(String.valueOf(dataMap.get("tradeNo")));
                    order.setAgtTradeNo(String.valueOf(dataMap.get("agtTradeNo")));
                    orderMapper.updateByPrimaryKeySelective(order);

                    *//*------------------------------start----bySeaton------------------------------------*//*
                   *//* String attach = null;
                    try {
                        attach = URLDecoder.decode(dataMap.get("attach"),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Map<String, String> retMap = JsonUtils.readValue(attach, HashMap.class);*//*
                    Map<String, String> retMap =new HashMap<>();
                    retMap.put("tradeTotalFee",dataMap.get("totalFee"));
                    retMap.put("outOrderNo",dataMap.get("outOrderNo"));
                    *//*------------------------------end----------------------------------------*//*
                    try {
                        orderService.paySuccess(retMap);
                    } catch (SystemException e) {
                        e.printStackTrace();
                    } catch (ESBException e) {
                        e.printStackTrace();
                    }
                } else {*/
                    //调用HIS 取消挂号
                    CancelRegisterRequest cancelRegisterRequest = new CancelRegisterRequest();
                    cancelRegisterRequest.setCancelReason("过期取消");
                    cancelRegisterRequest.setOrderId(String.valueOf(order.getId()));
                    cancelRegisterRequest.setOrderType(Constants.SILVER_MEDICINE_TYPE_NO);   //医程通通道40
                    cancelRegisterRequest.setCancelTime(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
                    ResponseData responseData = publicService.query(ESBServiceCode.CANCELREGISTER, cancelRegisterRequest, ResultResponse.class);
                    if (responseData.getData() != null) {
                        ResultResponse resultResponse = (ResultResponse) responseData.getData();
                        boolean flag = false;
                        boolean isPay = false;
                        if (resultResponse.getRecord().getResultCode() == null) {
                            continue;
                        }
                        String desc = "对应的挂号记录已取消。";
                        if ("-1".equals(resultResponse.getRecord().getResultCode()) && resultResponse.getRecord().getResultDesc().contains(desc)) {
                            flag = true;
                        }
                        String noDataDesc = "没有对应的挂号记录";
                        if ("-1".equals(resultResponse.getRecord().getResultCode()) && resultResponse.getRecord().getResultDesc().contains(noDataDesc)) {
                            flag = true;
                        }
                        String payDesc = "对应的挂号记录已支付";
                        if ("-1".equals(resultResponse.getRecord().getResultCode()) && resultResponse.getRecord().getResultDesc().contains(payDesc)) {
                            if("1".equals(findRegisterStatus(order.getId()))){//表示已取消
                                flag = true;
                            }else {
                                logger.info("payment 订单orderId:{}已支付！！！", order.getId());
                            }
                        }
                        if ("0".equals(resultResponse.getRecord().getResultCode())) {
                            flag = true;
                        }
                        if (flag && "0".equals(order.getOrderStatus())) {
                            order.setOrderStatus("2");  //取消，自动取消
                            order.setAutoCancelTime(new Date());
                            order.setUpdateTime(new Date());
                            order.setStatusRemark("过期取消");

                            orderMapper.updateByPrimaryKeySelective(order);
                        }
                    }
                }
            }
       // }
        return true;
    }

    private String findRegisterStatus(String orderId) throws ESBException, SystemException {
        RegistrationDetailRequest registrRequest=new RegistrationDetailRequest();
        registrRequest.setOrderId(orderId);
        registrRequest.setOrderType(String.valueOf(40));
        Order order=new Order();
        order.setId(orderId);
        order=orderMapper.selectByPrimaryKey(order);
        ResponseData registeredInfo=publicService.query(ESBServiceCode.REGISTEREDLIST,registrRequest, RegistrationDetailRespone.class);
        if (registeredInfo.getData()!=null) {
            RegistrationDetailRespone registeredRespone = (RegistrationDetailRespone) registeredInfo.getData();
            RegistrationDetailRecord registrationrecord = registeredRespone.getRecord();
            return registrationrecord.getOrderStatus();
        }else {
            return "-1";
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean paySuccess(Map<String, String> retMap) throws SystemException, ESBException {
        logger.info("paySuccess参数============="+retMap);
        //查询订单
        Order order = new Order();
        order.setId(retMap.get("outOrderNo"));
        order = orderMapper.selectByPrimaryKey(order);
        RegisteredRecord registeredRecord = registrationMapper.selectRegisteredRecordByOrderId(retMap.get("outOrderNo"));
        logger.info("订单信息:"+order);
        //todo:进行详细判断
        //if (order != null && order.getOrderStatus() != null && "1".equals(order.getOrderStatus())) {
        if (order != null && order.getOrderStatus() != null) {
            //未支付
            order.setOrderStatus("1");   //已支付
            order.setPaySuccessTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
            String paySuccessTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

            //288表示挂号费，通知HIS挂号成功
            if ("挂号费".equals(order.getFeeType()) || "诊查费".equals(order.getFeeType())) {
                ResponseData regPay = medicalService.regPay(order.getId(), order.getId(), order.getPersonalFee(), paySuccessTime, order.getOrderType());
                if(regPay != null && regPay.getData() != null){
                    ResultResponse payResultResponse = (ResultResponse) regPay.getData();
                    if(payResultResponse != null && payResultResponse.getRecord() != null &&
                            payResultResponse.getRecord().getResultCode() != null ){
                           if("0".equals(payResultResponse.getRecord().getResultCode())){
                               jPushNoticeService.sendRegistrationSuccess(String.valueOf(registeredRecord.getAccountId()),
                                       registeredRecord.getPatientId(),registeredRecord.getPatientName(),order.getId());
                               logger.info("成功通知HIS挂号&推送通知");
                               return true;
                           }
                        }
                    }
                logger.info("调用his挂号返回结果"+regPay.toString());
                return false;
            } else {
                if (StringUtils.isNotBlank(order.getFeeType())||StringUtils.isNotBlank(order.getOrderName() )) {
                    PayRequest payRequest = new PayRequest();
                    payRequest.setUserCardId(registeredRecord.getIdCard());
                    payRequest.setInfoSeq(registeredRecord.getHisOrderId());
                    payRequest.setOrderId(order.getId());
                    payRequest.setPayCardNum(order.getTradeNo());
                    payRequest.setPayAmout(order.getPersonalFee());
                    payRequest.setPayTime(paySuccessTime);
                    List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(payRequest.getOrderId());
                    if(orderDetailList != null && !orderDetailList.isEmpty()){
                        StringBuffer stringBuffer = new StringBuffer();
                        for(OrderDetail detail : orderDetailList){
                            stringBuffer.append(detail.getDetailID()+",");
                        }
                        stringBuffer.deleteCharAt(stringBuffer.length()-1);
                        payRequest.setDetailId(stringBuffer.toString());
                        logger.info("支付明细========="+stringBuffer.toString());
                    }
                    ResponseData query = publicService.query(ESBServiceCode.WAITPAYMENT, payRequest, PayRespone.class);
                    String orderDesc = null;
                   if( query != null && query.getData() != null){
                       PayRespone payRespone = (PayRespone) query.getData();
                       if(payRespone != null && payRespone.getPayRecordList()!= null){
                           PayRecord   payRecord = payRespone.getPayRecordList().get(0);
                           if(payRecord != null && "0".equals(payRecord.getResultCode())){
                               order.setHisOrderStatus("2");
                               orderDesc = payRecord.getOrderDesc();
                               order.setOrderDesc(orderDesc);
                               order.setHisOrderId(payRecord.getOrderIdHIS());
                               orderMapper.updateByPrimaryKeySelective(order);
                           }else {
                                //支付失败,返回
                               logger.error("HIS支失败,请核实=======:"+ payRequest.toString()+ query.getMsg());
                               return false;
                           }
                       }else {
                           logger.error("HIS支失败,请核实=======:"+ payRequest.toString()+ query.getMsg());
                           return false;
                       }
                   }
                    jPushNoticeService.sendPaymentSuccess(String.valueOf(registeredRecord.getAccountId()), registeredRecord.getPatientName(),
                            orderDesc,order.getId());
                    logger.info("支付 调用His 成功");
                }
            }
        } else {
            logger.info("支付 payOrders == 订单状态不为0或者查无此订单, orderStatus:{}", order != null ? order.getOrderStatus() : "");
            return false;
        }
        return true;
    }

    @Override
    public void payInform() {
        List<RegOrderVO> regOrderVOList = regOrderMapper.selectWaittingPayment();
        if (regOrderVOList != null && regOrderVOList.size() > 0) {
            for (RegOrderVO regOrderVO : regOrderVOList) {
                String payEndTime = DateFormatUtils.format(regOrderVO.getCreatedTime().getTime()+15*60*1000, "HH点mm分");
                jPushNoticeService.sendUnpaidPush(regOrderVO.getAccountId().toString(),regOrderVO.getPatientId().toString(),
                        regOrderVO.getOrderId(),payEndTime);

            }
        }
    }

    /**
     * 定时同步已支付订单，主动查询 待支付订单，同步医程通状态，如果是已支付，则改为已支付
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void autoPayOrder() {
        List<Order> orders = orderMapper.selectNotPayOrder();
        for(Order order : orders) {
            //查询支付
            Map<String, String> searchParam = new HashMap<String, String>();
            searchParam.put("outOrderNo", order.getId());
            searchParam.put("appId", systemProperties.getCacherAppId());
            searchParam.put("merchantNo", systemProperties.getPaymentMarchantNo());
            searchParam.put("nonceStr", UUID.randomUUID().toString().replace("-", ""));
            searchParam.put("signMode", SignatureUtils.SIGN_TYPE_RSA);
            searchParam.put("timeStamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            //待加密字符串
            String content = SignatureUtils.getSortContent(searchParam);
            orderMapper.updateDate(new Date(), order.getId());

            String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getCasherPrivateKey());
            searchParam.put("sign", sign);
            ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getPaymentHost() + SilverMedicineConstants.PAY_CASH_QUERY_URL, searchParam, HashMap.class);
            HashMap<String, String> dataMap = respData.getBody();
            logger.info("Order Task autoPayOrder-->orderId:{}", order.getId());
            logger.info(JsonUtils.toJSon(dataMap));
            if(dataMap != null && dataMap.get("returnCode") != null && "SUCCESS".equals(dataMap.get("returnCode"))
                    && "PAYMENT".equals(dataMap.get("tradeState"))) {
                //主动轮训，同步状态
                if("0".equals(order.getOrderStatus())) {
                    order.setStatusRemark("定时轮训同步为已支付");
                    order.setUpdateTime(new Date());
                    order.setOrderStatus("1");
                    order.setPaySuccessTime(new Date());
                    order.setPayType(String.valueOf(dataMap.get("channelCode")));
                    order.setTradeNo(String.valueOf(dataMap.get("tradeNo")));
                    order.setAgtTradeNo(String.valueOf(dataMap.get("agtTradeNo")));
                    orderMapper.updateByPrimaryKeySelective(order);

                    /*------------------------------start----bySeaton------------------------------------*/
                  /*  String attach = null;
                    try {
                        attach = URLDecoder.decode(dataMap.get("attach"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Map<String, String> retMap = JsonUtils.readValue(attach, HashMap.class);*/
                    Map<String, String> retMap = new HashMap<>(2);
                    retMap.put("tradeTotalFee", dataMap.get("totalFee"));
                    retMap.put("outOrderNo", dataMap.get("outOrderNo"));
                    /*------------------------------end----------------------------------------*/
                    try {
                        orderService.paySuccess(retMap);
                    } catch (SystemException e) {
                        e.printStackTrace();
                    } catch (ESBException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    @Override
    public Order selectByPrimaryKey(String orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public void seeDoctorTodayInform() {
        List<RegOrderVO> regOrderVOList = regOrderMapper.seeDoctorTodayInform();
        if (regOrderVOList != null && regOrderVOList.size() > 0) {
            for (RegOrderVO regOrderVO : regOrderVOList) {
                jPushNoticeService.sendAppointment(regOrderVO.getAccountId().toString(),regOrderVO.getPatientName(),
                        regOrderVO.getVisitTime(),regOrderVO.getDiagnoseRoom());
            }
        }

    }

    @Override
    public void syncDiagnoseFlag() {
        List<RegOrderVO> regOrderVOList = regOrderMapper.syncDiagnoseFlag();
        StringBuffer sb = new StringBuffer();
        if (regOrderVOList != null && regOrderVOList.size() > 0) {
            for (RegOrderVO regOrderVO : regOrderVOList) {
                if (regOrderVO != null) {
                    sb.append(regOrderVO.getPatientId() + ",");
                }
            }
        }
        ResponseData responseData = new ResponseData();
        if (StringUtils.isNotBlank(sb)) {
            sb.deleteCharAt(sb.length() - 1);
            RegisteredQueueRequest request = new RegisteredQueueRequest();
            request.setPatientIds(sb.toString());
            try {
                responseData = publicService.query(ESBServiceCode.REGISTERERECORD, request, RegisteredRecordResponse.class);
            } catch (SystemException e) {
                e.printStackTrace();
            } catch (ESBException e) {
                e.printStackTrace();
            }
            RegisteredRecordResponse response = (RegisteredRecordResponse) responseData.getData();
            if (response != null && response.getDeptRegInfoResponseRecode() != null) {
                for (RegisterdResponseRecord record : response.getDeptRegInfoResponseRecode()) {
                    if (record != null && "1".equals(record.getDiagnoseFlag())) {
                        Order order = new Order();
                        order.setId(record.getOrderId());
                        order.setRegisterStatus("2");
                        orderMapper.updateByPrimaryKeySelective(order);
                    }
                }
            }
        }
    }

    @Override
    public Boolean isValid(String orderId) {
        //判断条件为15分钟内且未支付状态
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",orderId);
        Date time = DateUtils.addMinutes(new Date(),-15);
        criteria.andEqualTo("orderStatus",0);
        criteria.andGreaterThan("createTime",time);
        List<Order> orderList = orderMapper.selectByExample(example);
        if(orderList == null || orderList.size() == 0){
            return false;
        }{
            return true;
        }
    }

    @Override
    public RegisteredRecord selectRegisterRecordByOrderId(String orderId) {
        return  registrationMapper.selectRegisteredRecordByOrderId(orderId);
    }

    @Override
    public void seeDoctorTomorrowInform() {
        List<RegOrderVO> regOrderVOList = regOrderMapper.seeDoctorTomorrowInform();
        if (regOrderVOList != null && regOrderVOList.size() > 0) {
            for (RegOrderVO regOrderVO : regOrderVOList) {
                jPushNoticeService.sendAppointment(regOrderVO.getAccountId().toString(),regOrderVO.getPatientName(),
                        regOrderVO.getVisitTime(),regOrderVO.getDiagnoseRoom());
            }
        }

    }

    @Override
    public RegisterSucesessRecordVO registerSucesessRecord(String orderId) {
       return paysuccessRecordMapper.selectRegisterSucesessRecordByOrderId(orderId);
    }

    @Override
    public PaySucesessRecordVO paySucesessRecord(String orderId) {
        PaySucesessRecordVO paySucesessRecordVO = paysuccessRecordMapper.selectPaySucesessRecordByOrderId(orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId);
        if(paySucesessRecordVO != null){
            paySucesessRecordVO.setOrderDetail(orderDetails);
        }
        return paySucesessRecordVO;
    }

    /**
     * 公共的POST请求接口
     * @param url
     * @param request
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType) {
        logger.info("pre pay -- url : {}", url);
        logger.info("pre pay -- request : {}", request);
        ResponseEntity<T> respData = restTemplate.postForEntity(url, request, responseType);
        logger.info("pre pay -- respData : {}", respData.getBody());
        return respData;
    }




}

