package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.properties.RSAKeyProperties;
import com.ry.fu.esb.common.properties.SystemProperties;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.utils.ResponseUtil;
import com.ry.fu.esb.medicaljournal.constants.SilverMedicineConstants;
import com.ry.fu.esb.medicaljournal.model.Order;
import com.ry.fu.esb.medicaljournal.model.PaySucesessRecordVO;
import com.ry.fu.esb.medicaljournal.model.RegisterSucesessRecordVO;
import com.ry.fu.esb.medicaljournal.service.OrderService;
import com.ry.fu.esb.medicaljournal.service.PayService;
import com.ry.fu.esb.medicaljournal.util.SignatureUtils;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/13 15:26
 * @description 移动端请求的接口
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/pay")
public class PayController {

    private static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private RSAKeyProperties rsaKeyProperties;

    @Autowired
    private PayService payService;

    @Autowired
    private  OrderService orderService;

    @Autowired
    private Environment env;

    /**
     * 收银台 预支付接口
     * @param
     * @return
     */
    @RequestMapping(value = "/preCashierPay", method =  RequestMethod.POST, produces="application/json")
    public ResponseData preCashierPay(@RequestBody Map<String, String> params) {
        logger.info("支付请求参数:{}", params);

        try{
            //校验参数
            if(params == null || params.size() == 0) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if(params.get("outOrderNo") == null || StringUtils.isBlank(params.get("outOrderNo"))) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "outOrderNo不能为空");
            }
            if(params.get("tradeTotalFee") == null || StringUtils.isBlank(params.get("tradeTotalFee"))) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "tradeTotalFee不能为空");
            }
            if(params.get("returnUrl") == null || StringUtils.isBlank(params.get("returnUrl"))) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "returnUrl不能为空");
            }

            String uuid = UUID.randomUUID().toString().replace("-", "");

            params.put("appId", systemProperties.getCacherAppId());
            params.put("merchantNo", systemProperties.getPaymentMarchantNo());
            params.put("nonceStr", uuid);
            params.put("channelCode", SilverMedicineConstants.ALIPAY_H5);
            params.put("outTime", "");
            params.put("signMode", SignatureUtils.SIGN_TYPE_RSA);
            if("test".equals(env.getActiveProfiles()[0])) {
                params.put("notifyUrl", systemProperties.getLocalHost() + "/esb_test/followupApp/v1/medicalJournal/notify/payCashNotify");
                params.put("refundNotifyUrl", systemProperties.getLocalHost() + "/esb_test/followupApp/v1/medicalJournal/notify/payCashRefundNotify");
            } else {
                params.put("notifyUrl", systemProperties.getLocalHost() + "/esb/followupApp/v1/medicalJournal/notify/payCashNotify");
                params.put("refundNotifyUrl", systemProperties.getLocalHost() + "/esb/followupApp/v1/medicalJournal/notify/payCashRefundNotify");
            }
            params.put("timeStamp", DateFormatUtils.format(new Date(), Constants.DATE_YYYY_MM_DD_HH_MM_SS));
            return payService.preCashierPay(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("preCashierPay异常", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), null);
        }

    }

    /**
     * 支付返回数据  由前端调用，将参数全部扔回后端即可
     * @param params
     * @return
     */
    @RequestMapping(value = "/payReturn", method =  RequestMethod.POST, produces="application/json")
    public ResponseData payReturn(@RequestBody Map<String, String> params) {
        logger.info("支付Return参数：{}", params);

        try {
            if(params == null || params.size() == 0) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if(params.get("outOrderNo") == null || StringUtils.isBlank(params.get("outOrderNo"))) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "outOrderNo不能为空");
            }

            //验签
            String encryptStr = params.remove("sign"); //秘钥
            String password = SignatureUtils.getSortContent(params);
            boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getCasherPublicKey());
            if(signFlag) {
                //验签成功
                logger.info("验签成功");
                if(params.get("tradeState") != null && StringUtils.isNotBlank(params.get("tradeState").toString())
                        && "SUCCESS".equals(params.get("tradeState").toString())) {
                    //通过
                    return payService.payReturn(params);
                }
            } else {
                logger.info("验签失败 sign:{}", encryptStr);
                return new ResponseData(StatusCode.PAY_SIGN_ERROR.getCode(), StatusCode.PAY_SIGN_ERROR.getMsg(), null);
            }
            return new ResponseData(StatusCode.PAY_FAILD.getCode(), StatusCode.PAY_FAILD.getMsg(), params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("payReturn异常", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), params);
        }
    }

    /**
     * 收银台 退费接口 params {outOrderNo}
     *
     *  强制退款：{"outOrderNo":"", "tradeTotalFee":1000,"ignore":"true"}
     *
     * @param params
     * @return
     */
    @RequestMapping("/refundCashierPay")
    public ResponseData refundCashierPay(@RequestBody Map<String, String> params) {
        if(params == null) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), "参数不能为空！");
        }
        if(params.get("outOrderNo") == null || StringUtils.isBlank(params.get("outOrderNo"))) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), "outOrderNo不能为空！");
        }

        try {
            return payService.refundCashierPay(params);
        } catch (ESBException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), "退费失败");
    }

    /**
     * 收银台 查询接口
     * @param params  {outOrderNo} 订单表的订单号
     * @return
     */
    @RequestMapping("/searchCashierPay")
    public ResponseData searchCashierPay(@RequestBody Map<String, String> params) {
        if(params.get("outOrderNo") == null || StringUtils.isBlank(params.get("outOrderNo"))) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "outOrderNo不能为空");
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");

        params.put("appId", systemProperties.getCacherAppId());
        params.put("merchantNo", systemProperties.getPaymentMarchantNo());
        params.put("nonceStr", uuid);
        params.put("signMode", SignatureUtils.SIGN_TYPE_RSA);
        params.put("timeStamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        return payService.searchCashierPay(params, params.get("outOrderNo"));
    }

    /**
     * 退费查询
     * @return
     */
    @RequestMapping("/searchCashierRefund")
    public ResponseData searchCashierRefund(@RequestBody Map<String, String> params) {
        if(params.get("outOrderNo") == null || StringUtils.isBlank(params.get("outOrderNo"))) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "outOrderNo不能为空");
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");

        params.put("appId", systemProperties.getCacherAppId());
        params.put("merchantNo", systemProperties.getPaymentMarchantNo());
        params.put("nonceStr", uuid);
        params.put("signMode", SignatureUtils.SIGN_TYPE_RSA);
        params.put("timeStamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return payService.searchCashierRefund(params);
    }

    /**
     * 待缴费支付(供调试已支付&His没有收到)
     */
    @RequestMapping(value = "/prePay", method = RequestMethod.POST)
    public ResponseData prePay(@RequestBody Map<String, String> retMap)  {
        Boolean b = false;
        try {
             b = orderService.paySuccess(retMap);

        } catch (SystemException e) {
            e.printStackTrace();
        } catch (ESBException e) {
            e.printStackTrace();
        }
        return b == true? ResponseUtil.getSuccessResultBean(b.toString()) : ResponseUtil.getFailResultBean(b.toString());
    }

    /**
     * 支付成功页面(挂号)
     */
    @RequestMapping(value = "/registerSucesessRecord", method = RequestMethod.POST)
    public ResponseData registerSucesessRecord(@RequestBody Map<String,String> map)  {
        String orderId = map.get("orderId");
        RegisterSucesessRecordVO registerSucesessRecordVO = orderService.registerSucesessRecord(orderId);
        return ResponseUtil.getSuccessResultBean(registerSucesessRecordVO);
    }

    /**
     * 支付成功页面
     */
    @RequestMapping(value = "/paySucesessRecord", method = RequestMethod.POST)
    public ResponseData paySucesessRecord(@RequestBody Map<String,String> map)  {
        String orderId = map.get("orderId");
        Order order = orderService.selectByPrimaryKey(orderId);
        if(order == null){
            return ResponseUtil.getSuccessResultBean(null);
        }
        if("诊查费".equals(order.getFeeType())){
            RegisterSucesessRecordVO registerSucesessRecordVO = orderService.registerSucesessRecord(orderId);
            return ResponseUtil.getSuccessResultBean(registerSucesessRecordVO);
        }
        if("总费用".equals(order.getFeeType())){
            PaySucesessRecordVO paySucesessRecordVO = orderService.paySucesessRecord(orderId);
            return ResponseUtil.getSuccessResultBean(paySucesessRecordVO);
        }
        return ResponseUtil.getSuccessResultBean(null);
    }

    /**
     * 查询当前订单是否可支付
     * TODO:后续直接调用registerSucesessRecord,准备废弃
     */
    @RequestMapping(value = "/isValid", method = RequestMethod.POST)
    public ResponseData isValid(@RequestBody Map<String,String> map)  {
        String orderId = map.get("orderId");
        Boolean flag = orderService.isValid(orderId);
        Map resultMap = new HashMap(2);
        resultMap.put("isValid", String.valueOf(flag));
        return ResponseUtil.getSuccessResultBean(resultMap);
    }

}
