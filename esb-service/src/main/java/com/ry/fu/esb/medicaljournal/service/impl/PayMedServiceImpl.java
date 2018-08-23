package com.ry.fu.esb.medicaljournal.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
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
import com.ry.fu.esb.medicaljournal.constants.SilverMedicineConstants;
import com.ry.fu.esb.medicaljournal.mapper.*;
import com.ry.fu.esb.medicaljournal.model.*;
import com.ry.fu.esb.medicaljournal.service.PayMedService;
import com.ry.fu.esb.medicaljournal.util.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/09 16:02
 * @description 医保支付实现类
 */
@Service
public class PayMedServiceImpl implements PayMedService {

    private static Logger logger = LoggerFactory.getLogger(PayMedServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private RSAKeyProperties rsaKeyProperties;

    @Autowired
    private PayMedReqMapper payMedReqMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PayMedRefundReqMapper payMedRefundReqMapper;

    @Autowired
    private PayMedAuthReqMapper payMedAuthReqMapper;

    @Autowired
    private PayMedRefundNotifyMapper payMedRefundNotifyMapper;

    @Autowired
    private PublicService publicService;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData preMedAuth(Map<String, String> params) {
        PayMedAuthReq payAuthMedReq = new PayMedAuthReq();
        String key = BeanMapUtils.getTableSeqKey(payAuthMedReq);
        long id = redisIncrementGenerator.increment(key, 1);
        payAuthMedReq.setId(id);
        payAuthMedReq.setMedUserName(params.get("userName"));
        payAuthMedReq.setUserPhone(params.get("phoneNumber"));
        payAuthMedReq.setIdCard(params.get("idCard"));
        payAuthMedReq.setRedirectUrl(params.get("redirectUrl"));
        payAuthMedReq.setReqData(JsonUtils.toJSon(params));
        payAuthMedReq.setAttach(id + "");
        payMedAuthReqMapper.insertSelective(payAuthMedReq);
        params.put("attach", payAuthMedReq.getId() + "");
        //加密sign
        String content = SignatureUtils.getSortContent(params);
        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getInsurancePrivateKey());
        params.put("sign", sign);
        params.put("formAction", systemProperties.getMedPaymentHost() + SilverMedicineConstants.MED_PAY_AUTH_URL);
        logger.info("授权请求参数：{}",JsonUtils.toJSon(params));
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), params);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData preMedPay(Map<String, String> params) {
        //先存取支付表,是否需要分割？
        String orderId = params.get("mchOrderNo");
        Order order = orderMapper.selectByPrimaryKey(orderId);
        order.setPayType("sunshier_wallet");
        orderMapper.updateByPrimaryKeySelective(order);
        //存储数据库
        PayMedReq payMedReq = new PayMedReq();
        String payMedReqKey = BeanMapUtils.getTableSeqKey(payMedReq);
        Long reqId = redisIncrementGenerator.increment(payMedReqKey, 1);
        payMedReq.setId(reqId);
        payMedReq.setAuthCode(params.get("authCode"));
        payMedReq.setMedCreateTime(new Date());
        payMedReq.setMedUserName(params.get("medicareUserName"));
        payMedReq.setReqData(JSONUtils.toJSONString(params));
        payMedReq.setProductName(params.get("productName"));
        payMedReq.setProdesc(params.get("productDesc"));
        payMedReq.setOrderStatus(order.getOrderStatus());
        payMedReq.setUserPhone(params.get("phoneNumber"));
        payMedReq.setAttach(params.get("attach"));
        payMedReq.setRedirectUrl(params.get("redirectUrl"));
        //支付请求中商户订单号
        payMedReq.setMchOrderNo(orderId);
        payMedReqMapper.insertSelective(payMedReq);
        params.put("mchOrderNo", orderId);

        //加密sign
        String content = SignatureUtils.getSortContent(params);
        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getInsurancePrivateKey());
        params.put("sign", sign);
        params.put("formAction", systemProperties.getMedPaymentHost() + SilverMedicineConstants.MED_PAY_CASH_URL);
        logger.info("支付入参params:{}", JsonUtils.toJSon(params));
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), params);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseData refundMedPay(Map<String, String> params) throws ESBException, SystemException {
        String orderId = params.get("mchOrderNo");
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            return new ResponseData(StatusCode.ORDER_UNFOUND.getCode(), StatusCode.ORDER_UNFOUND.getMsg(), null);
        }
        params.put("refundAmount", order.getTotalFee());   //订单金额
        params.put("mchOrderNo", orderId);
        params.put("refundReason", params.get("refundReason") != null ? String.valueOf(params.get("refundReason")) : "");
        PayMedRefundReq payMedRefundReq = new PayMedRefundReq();
        String refundReqKey = BeanMapUtils.getTableSeqKey(payMedRefundReq);
        Long reqId = redisIncrementGenerator.increment(refundReqKey, 1);
        //商户退费订单号用请求id
        params.put("mchRefundOrderNo", String.valueOf(reqId));
        //待加密字符串
        String content = SignatureUtils.getSortContent(params);
        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getInsurancePrivateKey());
        params.put("sign", sign);
        logger.info("退费入参：{}",params);
        //请求插入数据
        payMedRefundReq.setId(reqId);
        payMedRefundReq.setMchOrderNo(orderId);
        payMedRefundReq.setRefundReason(params.get("refundDesc"));
        payMedRefundReq.setAttach(params.get("attach"));
        payMedRefundReq.setReqData(JsonUtils.toJSon(params));
        payMedRefundReq.setCreateTime(new Date());
        payMedRefundReqMapper.insertSelective(payMedRefundReq);
        ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getMedPaymentHost() + SilverMedicineConstants.PAY_MED_EFUND_URL, params, HashMap.class);
        HashMap<String, String> dataMap = respData.getBody();
        String resultMessage = dataMap.get("resultMessage");
        if (StringUtils.isBlank(resultMessage) || !"响应成功".equalsIgnoreCase(resultMessage)) {
            return new ResponseData(StatusCode.PAY_ERROR.getCode(), StatusCode.PAY_ERROR.getMsg(), dataMap);
        }
        //验签
        String encryptStr = dataMap.remove("sign"); //秘钥
        encryptStr = StringUtils.isBlank(encryptStr) ? "" : encryptStr;
        String password = SignatureUtils.getSortContent(dataMap);
        if (StringUtils.isNotBlank(encryptStr)) {
            boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getInsurancePublicKey());
            if (!signFlag) {
                //验签失败
                logger.info(JsonUtils.toJSon(dataMap));
                logger.info("阳光康众返回数据 签名错误");
                return new ResponseData(StatusCode.PAY_SIGN_ERROR.getCode(), StatusCode.PAY_SIGN_ERROR.getMsg(), null);
            }
        }
        payMedRefundReq.setRespData(JsonUtils.toJSon(dataMap));
        payMedRefundReqMapper.updateByPrimaryKeySelective(payMedRefundReq);
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
    }


    @Override
    public ResponseData getPatientMessage(String patientId) {
        Patient patient = new Patient();
        patient.setEsbPatientId(Long.valueOf(patientId));
        List<Patient> patientList = patientMapper.select(patient);
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), patientList.get(0));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData queryOrderStatus(Map<String, String> params) {
        Order order = orderMapper.selectByPrimaryKey(params.get("mchOrderNo"));
        params.put("mchOrderNo", params.get("mchOrderNo"));
        String content = SignatureUtils.getSortContent(params);
        String sign = SignatureUtils.rsa256Sign(content, rsaKeyProperties.getInsurancePrivateKey());
        params.put("sign", sign);
        logger.info("查询入参：{}",JSONUtils.toJSONString(params));
        ResponseEntity<HashMap> respData = this.postForEntity(systemProperties.getMedPaymentHost() + SilverMedicineConstants.MED_PAY_QUERY_URL, params, HashMap.class);
        HashMap<String, String> dataMap = respData.getBody();
        if (dataMap.get("resultMessage") == null || StringUtils.isBlank(dataMap.get("resultMessage").toString())) {
            return new ResponseData(StatusCode.PAY_ERROR.getCode(), StatusCode.PAY_ERROR.getMsg(), dataMap);
        }
        logger.info("查询出参：{}",JSONUtils.toJSONString(dataMap));
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), dataMap);
    }

    /**
     * 公共的POST请求接口
     *
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

    public <T> ResponseEntity<T> postForForm(String url, MultiValueMap<String, String> request, Class<T> responseType) {
        logger.info("pre pay -- url : {}", url);
        logger.info("pre pay -- request : {}", request);
        HttpHeaders httpHeaders = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        //  MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(request, httpHeaders);
        ResponseEntity<T> respData = restTemplate.postForEntity(url, requestEntity, responseType);
        logger.info("pre pay -- respData : {}", respData.getBody());
        return respData;
    }
}
