package com.ry.fu.esb.medicaljournal.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.properties.RSAKeyProperties;
import com.ry.fu.esb.common.properties.SystemProperties;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.ResponseUtil;
import com.ry.fu.esb.medicaljournal.constants.SilverMedicineConstants;
import com.ry.fu.esb.medicaljournal.mapper.OrderMapper;
import com.ry.fu.esb.medicaljournal.mapper.PayCashRefundReqMapper;
import com.ry.fu.esb.medicaljournal.mapper.PayCashReqMapper;
import com.ry.fu.esb.medicaljournal.mapper.RegistrationMapper;
import com.ry.fu.esb.medicaljournal.model.Order;
import com.ry.fu.esb.medicaljournal.model.PayCashRefundReq;
import com.ry.fu.esb.medicaljournal.model.PayCashReq;
import com.ry.fu.esb.medicaljournal.model.RegisteredRecord;
import com.ry.fu.esb.medicaljournal.model.request.RegisterRefundRequest;
import com.ry.fu.esb.medicaljournal.model.response.ResultResponse;
import com.ry.fu.esb.medicaljournal.service.JPushNoticeService;
import com.ry.fu.esb.medicaljournal.service.OrderService;
import com.ry.fu.esb.medicaljournal.service.PayService;
import com.ry.fu.esb.medicaljournal.util.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 17:11
 * @description 支付服务实现类
 */
@Service
public class PayServiceImpl implements PayService {

    private static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private RSAKeyProperties rsaKeyProperties;

    @Autowired
    private PayCashReqMapper payCashReqMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PayCashRefundReqMapper payCashRefundReqMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private PublicService publicService;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private JPushNoticeService jPushNoticeService;

    @Autowired
    private Environment env;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData preCashierPay(Map<String, String> params) {
        //先存取支付表
        //存储数据库
        PayCashReq payCashReq = new PayCashReq();
        String payCashReqKey = BeanMapUtils.getTableSeqKey(payCashReq);
        Long reqId = redisIncrementGenerator.increment(payCashReqKey, 1);
        payCashReq.setId(reqId);
        payCashReq.setAttach(params.get("attach"));
        payCashReq.setMerchantNo(params.get("merchantNo"));
        payCashReq.setOutOrderNo(params.get("outOrderNo"));
        payCashReq.setChannelCode(params.get("channelCode"));
        payCashReq.setReqUrl(systemProperties.getPaymentHost() + SilverMedicineConstants.PAY_CASH_URL);
        payCashReq.setNoncestr(params.get("nonceStr"));
        payCashReq.setPayTypeCode(params.get("channelCode"));
        payCashReq.setCreateTime(new Date());
        payCashReq.setReqData(JSONUtils.toJSONString(params));
        payCashReq.setTradeTotalFee(params.get("tradeTotalFee"));
        payCashReq.setAttach(params.get("attach"));
        payCashReq.setReturnUrl(params.get("returnUrl"));

        params.put("outOrderNo", params.get("outOrderNo"));

        Order order = new Order();
        order.setId(params.get("outOrderNo"));
        order.setPayType(params.get("channelCode"));
        orderMapper.updateByPrimaryKeySelective(order);

        //加密sign
        String content = SignatureUtils.getSortContent(params);
        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getCasherPrivateKey());
        params.put("sign", sign);

        logger.info(JsonUtils.toJSon(payCashReq));
        payCashReqMapper.insertSelective(payCashReq);
        ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getPaymentHost() + SilverMedicineConstants.PAY_CASH_URL, params, HashMap.class);

        HashMap<String, String> dataMap = respData.getBody();
        if(dataMap.get("returnCode") == null || StringUtils.isBlank(dataMap.get("returnCode").toString())
                || !"SUCCESS".equals(dataMap.get("returnCode").toString())) {
            return new ResponseData(StatusCode.PAY_ERROR.getCode(), StatusCode.PAY_ERROR.getMsg(), dataMap);
        }

        //验签
        String encryptStr = dataMap.remove("sign"); //秘钥
        logger.info("sign:{}", encryptStr);
        String password = SignatureUtils.getSortContent(dataMap);
        logger.info("SortContent:{}", password);
        boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getCasherPublicKey());
        if(!signFlag) {
            //验签失败
            logger.info(JsonUtils.toJSon(dataMap));
            logger.info("阳光康众返回数据 签名错误");
        }
        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put("appId", dataMap.get("appId"));
        retMap.put("merchantNo", dataMap.get("merchantNo"));
        retMap.put("cashierId", dataMap.get("cashierId"));
        retMap.put("nonceStr", dataMap.get("nonceStr"));
        retMap.put("timeStamp", dataMap.get("timeStamp"));
        //retMap.put("attach", payCashReq.getAttach());
        retMap.put("signMode", SignatureUtils.SIGN_TYPE_RSA);
        String needRSAContent = SignatureUtils.getSortContent(retMap);
        retMap.put("sign", SignatureUtils.rsa256Sign(needRSAContent, rsaKeyProperties.getCasherPrivateKey()));
        retMap.put("cashierUrl", dataMap.get("cashierUrl"));
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), retMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData payReturn(Map<String, String> params) {
        //根据 NONCESTR 和 OUT_ORDER_NO 查询并修改 PayCashReq
        String outOrderNo = params.get("outOrderNo");
        Long payCashReqId = payCashReqMapper.selectByNoncestrAndOutOrderId(params.get("outOrderNo"));
        if(payCashReqId != null && payCashReqId != 0) {
            PayCashReq payCashReq = new PayCashReq();
            payCashReq.setId(payCashReqId);
            payCashReq.setRespData(JsonUtils.toJSon(params));
            payCashReqMapper.updateByPrimaryKeySelective(payCashReq);
        }
        //查询支付
        Map<String, String> searchParam = new HashMap<String, String>();
        searchParam.put("outOrderNo", outOrderNo);

        searchParam.put("appId", systemProperties.getCacherAppId());
        searchParam.put("merchantNo", systemProperties.getPaymentMarchantNo());
        searchParam.put("nonceStr", UUID.randomUUID().toString().replace("-", ""));
        searchParam.put("notifyUrl", systemProperties.getLocalHost() + "/esb/followupApp/v1/medicalJournal/notify");
        searchParam.put("signMode", SignatureUtils.SIGN_TYPE_RSA);
        searchParam.put("timeStamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //待加密字符串
        String content = SignatureUtils.getSortContent(searchParam);

        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getCasherPrivateKey());
        searchParam.put("sign", sign);
        ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getPaymentHost() + SilverMedicineConstants.PAY_CASH_QUERY_URL, searchParam, HashMap.class);
        HashMap<String, String> dataMap = respData.getBody();
        logger.info("payReturn --> orderId:{}", outOrderNo);
        logger.info(JsonUtils.toJSon(dataMap));
        if(dataMap != null && dataMap.get("returnCode") != null && "SUCCESS".equals(dataMap.get("returnCode"))) {
            if("PAYMENT".equals(dataMap.get("tradeState"))) {
                //修改数据库
                Order order = new Order();
                order.setId(outOrderNo);
                order.setStatusRemark("支付回调主动查询修改为已支付");
                order.setUpdateTime(new Date());
                order.setOrderStatus("1");
                order.setPayType(params.get("channelCode"));
                order.setTradeNo(params.get("tradeNo"));
                order.setAgtTradeNo(params.get("agtTradeNo"));
                order.setPaySuccessTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);

                 /*------------------------------start----bySeaton------------------------------------*/
               /* String attach = null;
                try {
                    attach = URLDecoder.decode(params.get("attach"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, String> retMap = JsonUtils.readValue(attach, HashMap.class);*/
                Map<String, String> retMap = new HashMap<>();
                retMap.put("tradeTotalFee",params.get("tradeTotalFee"));
                retMap.put("outOrderNo",params.get("outOrderNo"));
                /*------------------------------end----------------------------------------*/
                try {
                    orderService.paySuccess(retMap);
                } catch (SystemException e) {
                    e.printStackTrace();
                } catch (ESBException e) {
                    e.printStackTrace();
                }

                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), "outOrderNo为 "+outOrderNo + "支付成功！");
            }
        }
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getCode(), dataMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData refundCashierPay(Map<String, String> params) {


        //查询订单号
        String orderId = params.get("outOrderNo");

       // Long refundFee = 0L;
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order == null) {
            return new ResponseData(StatusCode.ORDER_UNFOUND.getCode(), StatusCode.ORDER_UNFOUND.getMsg(), null);
        }

       /* if(order != null && StringUtils.isNotBlank(order.getPersonalFee())) {
            refundFee += Long.parseLong(order.getPersonalFee());
        }*/


        //先判断是否允许退费
       /*  if("挂号费".equals(order.getFeeType()) || "诊查费".equals(order.getFeeType())) {
            RegisterCanRefundRequest registerCanRefundRequest = new RegisterCanRefundRequest();
            registerCanRefundRequest.setOrderId(String.valueOf(order.getId()));
            registerCanRefundRequest.setOrderType(Constants.SILVER_MEDICINE_TYPE_NO);
            registerCanRefundRequest.setReturnAmout(order.getTotalFee());

            ResponseData responseData = null;
            try {
                responseData = publicService.query(ESBServiceCode.REGISTERCANREFUND, registerCanRefundRequest, ResultResponse.class);
            } catch (SystemException e) {
                e.printStackTrace();
            } catch (ESBException e) {
                e.printStackTrace();
            }
            resultResponse = (ResultResponse) responseData.getData();
        }
        if(resultResponse == null || resultResponse.getRecord() == null) {
            //未退费成功，未响应数据
            return new ResponseData("601", "HIS申请挂号退费接口无响应", null);
        }
        if(!"0".equals(resultResponse.getRecord().getResultCode())) {
            return new ResponseData("601", "HIS系统不允许退费", resultResponse.getRecord().getResultDesc());
        }*/

        PayCashRefundReq payCashRefundReq1 = new PayCashRefundReq();
        String refundReqKey = BeanMapUtils.getTableSeqKey(payCashRefundReq1);
        Long reqId = redisIncrementGenerator.increment(refundReqKey, 1);

        //取消挂号，退费
        //调用ESB退费接口
        ResultResponse resultResponse = null;
        if("挂号费".equals(order.getFeeType()) || "诊查费".equals(order.getFeeType())) {
            RegisterRefundRequest registerRefundRequest = new RegisterRefundRequest();
            registerRefundRequest.setOrderId(String.valueOf(order.getId()));
            registerRefundRequest.setOrderType(Constants.SILVER_MEDICINE_TYPE_NO);
            registerRefundRequest.setReturnNum(String.valueOf(reqId));
            registerRefundRequest.setReturnTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            registerRefundRequest.setReturnAmout(order.getTotalFee());
            ResponseData responseData = null;
            try {
                responseData = publicService.query(ESBServiceCode.REGISTERREFUND, registerRefundRequest, ResultResponse.class);
                resultResponse = (ResultResponse) responseData.getData();

            } catch (SystemException e) {
                e.printStackTrace();
            } catch (ESBException e) {
                e.printStackTrace();
            }

       /* if(resultResponse == null || resultResponse.getRecord() == null) {
            //未退费成功，未响应数据
            return new ResponseData(StatusCode.REFUND_FAILD.getCode(), StatusCode.REFUND_FAILD.getMsg(), null);
        }

        if(!"0".equals(resultResponse.getRecord().getResultCode()) ) {
            //未退费成功，未响应数据
            return new ResponseData(StatusCode.REFUND_FAILD.getCode(), "HIS系统退费失败", resultResponse.getRecord().getResultDesc());
        }*/

       RegisteredRecord registeredRecord = registrationMapper.selectRegisteredRecordByOrderId(orderId);
        //HIS退费成功
        if("0".equals(resultResponse.getRecord().getResultCode()) ) {
            //请求插入数据
            ResponseData sunshineRefundData = getSunshineRefundData(params, order.getPersonalFee(), reqId);
            //医程通退费失败
            if (sunshineRefundData == null || StatusCode.REFUND_FAILD.getCode().equals(sunshineRefundData.getStatus())) {
                jPushNoticeService.refundFailureNotice(String.valueOf(registeredRecord.getAccountId()),
                        registeredRecord.getPatientName(),orderId,sunshineRefundData.getMsg());
                return sunshineRefundData;
            }
            order.setHisOrderStatus("4");
        }else {
           String desc = resultResponse.getRecord().getResultDesc()+"";
            if(desc.contains("没有对应的挂号记录")||desc.contains("对应的挂号记录未支付")||
            desc.contains("对应的挂号记录已取消")||desc.contains("已被退号")){
                ResponseData sunshineRefundData = getSunshineRefundData(params, order.getPersonalFee(), reqId);
                //医程通退费失败
                if (sunshineRefundData == null || StatusCode.REFUND_FAILD.getCode().equals(sunshineRefundData.getStatus())) {
                    jPushNoticeService.refundFailureNotice(String.valueOf(registeredRecord.getAccountId()),
                            registeredRecord.getPatientName(),orderId,sunshineRefundData.getMsg());
                    return sunshineRefundData;
                }
            }else {
                jPushNoticeService.refundFailureNotice(String.valueOf(registeredRecord.getAccountId()),
                        registeredRecord.getPatientName(),orderId,resultResponse.getRecord().getResultDesc());
                return new ResponseData(StatusCode.REFUND_FAILD.getCode(), "退费失败。如有疑问，请联系客服。", resultResponse.getRecord().getResultDesc());
            }
        }

            //修改订单状态
            order.setRefundDate(new Date());
            order.setOrderStatus("5");  //退费
            order.setStatusRemark("同步退费");
            order.setUpdateTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);

            //成功推送
            jPushNoticeService.refundSuccessNotice(String.valueOf(registeredRecord.getAccountId()),
                    registeredRecord.getPatientName(),orderId);

            Map<String, String> retMap = new HashMap<String, String>();
            retMap.put("outOrderNo", params.get("outOrderNo"));
            return new ResponseData(StatusCode.OK.getCode(), "退费成功,款项会在1-5天内原路退回到你的支付账号，敬请留意。", retMap);
        }else {
        return new ResponseData(StatusCode.REFUND_FAILD.getCode(), "退费失败。如有疑问，请联系客服。", resultResponse.getRecord().getResultDesc());
        }
    }

    private ResponseData getSunshineRefundData(Map<String, String> params, String personalFee, Long reqId) {
        PayCashRefundReq payCashRefundReq = new PayCashRefundReq();
        payCashRefundReq.setId(reqId);
        payCashRefundReq.setOutOrderNo(params.get("outOrderNo"));
        payCashRefundReq.setNoncestr(params.get("nonceStr"));
        payCashRefundReq.setRefundFee(params.get("refundFee"));
        payCashRefundReq.setTradeTotalFee(params.get("tradeTotalFee"));
        payCashRefundReq.setRefundReason(params.get("refundDesc"));
        payCashRefundReq.setAttach(params.get("attach"));
        payCashRefundReq.setRefundNo(params.get("refundNo"));
        payCashRefundReq.setReqData(JsonUtils.toJSon(params));
        payCashRefundReq.setCreateTime(new Date());
        payCashRefundReqMapper.insertSelective(payCashRefundReq);

        String uuid = UUID.randomUUID().toString().replace("-", "");
        params.put("appId", systemProperties.getCacherAppId());
        params.put("merchantNo", systemProperties.getPaymentMarchantNo());
        params.put("nonceStr", uuid);
        if("test".equals(env.getActiveProfiles()[0])) {
            params.put("refundNotifyUrl", systemProperties.getLocalHost() + "/esb_test/followupApp/v1/medicalJournal/notify/payCashRefundNotify");
        } else {
            params.put("refundNotifyUrl", systemProperties.getLocalHost() + "/esb/followupApp/v1/medicalJournal/notify/payCashRefundNotify");
        }
        params.put("signMode", SignatureUtils.SIGN_TYPE_RSA);
        params.put("timeStamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        params.put("tradeTotalFee", personalFee);   //订单金额
        params.put("outOrderNo", params.get("outOrderNo"));
        params.put("refundFee", personalFee);
        params.put("refundDesc", params.get("refundDesc") != null ? String.valueOf(params.get("refundDesc")) : "");
        params.put("outRefundNo", String.valueOf(reqId));

        //待加密字符串
        String content = SignatureUtils.getSortContent(params);
        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getCasherPrivateKey());
        params.put("sign", sign);


        //请求医程通退费接口
        ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getPaymentHost() +
                SilverMedicineConstants.PAY_CASHR_EFUND_URL, params, HashMap.class);
        HashMap<String, String> dataMap = respData.getBody();
        if(dataMap.get("returnCode") == null || StringUtils.isBlank(dataMap.get("returnCode").toString())
                || !"SUCCESS".equals(dataMap.get("returnCode").toString())) {
            logger.error(params.get("outOrderNo")+"退费失败",dataMap);
            return new ResponseData(StatusCode.REFUND_FAILD.getCode(), dataMap.get("returnMsg"), dataMap);
        }

        //验签
        String encryptStr = dataMap.remove("sign"); //秘钥
        logger.info("sign:{}", encryptStr);
        String password = SignatureUtils.getSortContent(dataMap);
        logger.info("SortContent:{}", password);
        boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getCasherPublicKey());
        if(!signFlag) {
            //验签失败
            logger.info(JsonUtils.toJSon(dataMap));
            logger.info("阳光康众返回数据 签名错误");
            return new ResponseData(StatusCode.PAY_SIGN_ERROR.getCode(), StatusCode.PAY_SIGN_ERROR.getMsg(), null);
        }
        payCashRefundReq.setRespData(JsonUtils.toJSon(dataMap));
        payCashRefundReqMapper.updateByPrimaryKeySelective(payCashRefundReq);
        return ResponseUtil.getSuccessResultBean(dataMap);
    }

    /**
     * 退费查询
     * @param params
     * @return
     */
    @Transactional
    @Override
    public ResponseData searchCashierRefund(Map<String, String> params) {
        String orderId = params.get("outOrderNo");
        //根据订单查询payId
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order == null ) {
            return new ResponseData("500", "该订单号不存在", "该订单号不存在");
        }
        //待加密字符串
        String content = SignatureUtils.getSortContent(params);
        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getCasherPrivateKey());
        params.put("sign", sign);
        ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getPaymentHost() + SilverMedicineConstants.PAY_CASHR_EFUND_QUERYURL, params, HashMap.class);
        HashMap<String, String> dataMap = respData.getBody();
        if(dataMap.get("returnCode") == null || StringUtils.isBlank(dataMap.get("returnCode").toString())
                || !"SUCCESS".equals(dataMap.get("returnCode").toString())) {
            return new ResponseData(StatusCode.PAY_ERROR.getCode(), StatusCode.PAY_ERROR.getMsg(), dataMap);
        } else {
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), dataMap);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData searchCashierPay(Map<String, String> params, String orderId) {
        //根据订单查询payId
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order == null ) {
            return new ResponseData("500", "该订单号不存在", "该订单号不存在");
        }
        params.put("outOrderNo", orderId);
        //待加密字符串
        String content = SignatureUtils.getSortContent(params);

        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getCasherPrivateKey());
        params.put("sign", sign);
        ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getPaymentHost() + SilverMedicineConstants.PAY_CASH_QUERY_URL, params, HashMap.class);
        HashMap<String, String> dataMap = respData.getBody();
        if(dataMap.get("returnCode") == null || StringUtils.isBlank(dataMap.get("returnCode").toString())
                || !"SUCCESS".equals(dataMap.get("returnCode").toString())) {
            return new ResponseData(StatusCode.PAY_ERROR.getCode(), StatusCode.PAY_ERROR.getMsg(), dataMap);
        } else {
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), dataMap);
        }
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
