package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.properties.RSAKeyProperties;
import com.ry.fu.esb.common.properties.SystemProperties;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.service.PayMedService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/09 15:59
 * @description 医保请求的接口
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/payMed")
public class PayMedController {

    private static Logger logger = LoggerFactory.getLogger(PayMedController.class);

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private Environment env;

    @Autowired
    private RSAKeyProperties rsaKeyProperties;

    @Autowired
    private PayMedService payMedService;

    @RequestMapping(value = "getPatientMessage", method = RequestMethod.POST, produces = "application/json")
    public ResponseData getPatientMessage(@RequestBody Map<String, String> params) {
        logger.info("查询患者信息请求参数:{}", params);
        String patientId = params.get("patientId");
        if (patientId == null || StringUtils.isBlank(patientId)) {
            return new ResponseData(StatusCode.ARGU_ERROR.getCode(), StatusCode.ARGU_ERROR.getMsg(), null);
        }
        return payMedService.getPatientMessage(patientId);
    }

    /**
     * 医保支付授权接口
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/preMedAuth", method = RequestMethod.POST, produces = "application/json")
    public ResponseData preMedAuth(@RequestBody Map<String, String> params) {
        try {
            //校验参数
            if (params == null || params.size() == 0) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (params.get("idCard") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "idCard不能为空");
            }
            if (params.get("userName") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "userName不能为空");
            }
            if (params.get("phoneNumber") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "phoneNumber不能为空");
            }
            if (params.get("mchUserId") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "mchUserId不能为空");
            }
            params.put("notifyUrl", systemProperties.getLocalHost() + "/esb/followupApp/v1/medicalJournal/medNotify/payMedAuthNotify");
            params.put("mchId", systemProperties.getPaymentMedMarchantNo());
            params.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            if ("test".equals(env.getActiveProfiles()[0])) {
                params.put("notifyUrl", systemProperties.getLocalHost() + "/esb_test/followupApp/v1/medicalJournal/medNotify/payMedAuthNotify");
            } else {
                params.put("notifyUrl", systemProperties.getLocalHost() + "/esb/followupApp/v1/medicalJournal/medNotify/payMedAuthNotify");
            }
            return payMedService.preMedAuth(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("preCashierPay异常", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), null);
        }


    }


    /**
     * 医保 支付接口
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/preMedCashierPay", method = RequestMethod.POST, produces = "application/json")
    public ResponseData preMedCashierPay(@RequestBody Map<String, String> params) {
        try {
            //校验参数
            if (params == null || params.size() == 0) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (params.get("phoneNumber") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "phoneNumber不能为空");
            }
            if (params.get("medicareUserName") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "medicareUserName不能为空");
            }
            if (params.get("productName") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "productName不能为空");
            }
            if (params.get("productDesc") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "productDesc不能为空");
            }
            if (params.get("orderAmount") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "orderAmount不能为空");
            }
            if (params.get("mchUserId") == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "mchUserId不能为空");
            }
            params.put("mchPlaceOrderTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            params.put("mchId", systemProperties.getPaymentMedMarchantNo());
            params.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            //判断是否是测试环境
            if ("test".equals(env.getActiveProfiles()[0])) {
                params.put("notifyUrl", systemProperties.getLocalHost() + "/esb_test/followupApp/v1/medicalJournal/medNotify/payMedNotify");
            } else {
                params.put("notifyUrl", systemProperties.getLocalHost() + "/esb/followupApp/v1/medicalJournal/medNotify/payMedNotify");
            }
            return payMedService.preMedPay(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("preCashierPay异常", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), null);
        }

    }

    /**
     * 医保退费接口 params {outOrderNo}
     *
     * @param params
     * @return
     */
    @RequestMapping("/refundMedPay")
    public ResponseData refundMedPay(@RequestBody Map<String, String> params) throws SystemException, ESBException {
        params.put("mchRefundTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        params.put("mchId", systemProperties.getPaymentMedMarchantNo());
        params.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        params.put("attach", "a=1&b=yct");
        if ("test".equals(env.getActiveProfiles()[0])) {
            params.put("notifyUrl", systemProperties.getLocalHost() + "/esb_test/followupApp/v1/medicalJournal/medNotify/payMedRefundNotify");
        } else {
            params.put("notifyUrl", systemProperties.getLocalHost() + "/esb/followupApp/v1/medicalJournal/medNotify/payMedRefundNotify");
        }
        return payMedService.refundMedPay(params);
    }

    /**
     * 医保 查询订单状态
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryOrderStatus", produces = "application/json", method = RequestMethod.POST)
    public ResponseData queryOrderStatus(@RequestBody Map<String, String> params) {
        String mchOrderNo = params.get("mchOrderNo");
        params.put("mchId", systemProperties.getPaymentMedMarchantNo());
        params.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        params.put("attach", "a=1&b=yct");
        //mchUserId mchOrderNo 表单提交
        return payMedService.queryOrderStatus(params);
    }
}
