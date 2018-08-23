package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.properties.RSAKeyProperties;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.utils.HttpDataUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.medicaljournal.service.PayMedNotifyService;
import com.ry.fu.esb.medicaljournal.util.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/09 15:59
 * @description 医保通知接口
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/medNotify")
public class PayMedNotifyController {

    private static Logger logger = LoggerFactory.getLogger(PayMedNotifyController.class);

    @Autowired
    private PayMedNotifyService payMedNotifyService;

    @Autowired
    private RSAKeyProperties rsaKeyProperties;

    /**
     * 医保 异步授权通知
     * 格式 ： {"tradeNo":["3010208201803239999954"],"payTime":["2018-03-23 15:58:49"]...}
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/payMedAuthNotify", method = RequestMethod.POST, produces = "application/json")
    public String payMedAuthNotify(@RequestBody Map<String, String> params) {
        String retFlag = "fail";
        logger.info("授权异步回调参数---json:{}", JsonUtils.toJSon(params));
        try {
            //验签
            String encryptStr = params.remove("sign"); //秘钥
            logger.info("sign:" + encryptStr);
            String password = SignatureUtils.getSortContent(params);
            logger.info("sortContent:" + password);
            boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getInsurancePublicKey());
            if (signFlag) {
                //验签成功
                logger.info("payMedAuthNotify 验签成功");
                String resultMessage = params.get("resultMessage");
                String authCode = params.get("authCode");
                if (StringUtils.isNotBlank(resultMessage) && "响应成功".equals(params.get("resultMessage")) && StringUtils.isNotBlank(authCode)) {
                    retFlag = payMedNotifyService.authMedNotify(params);
                } else {
                    logger.info("payMedAuthNotify resultMessage == null or resultMessage != 响应成功");
                }
            } else {
                logger.info("payMedAuthNotify 验签失败, 签名失败");
                retFlag = "fail";
            }
        } catch (SystemException e) {
            logger.error("payMedAuthNotify - > SystemException", e);
            e.printStackTrace();
            retFlag = "fail";
        } catch (ESBException e) {
            logger.error("payMedAuthNotify - > ESBException", e);
            e.printStackTrace();
            retFlag = "fail";
        }
        return retFlag;
    }

    /**
     * 医保 同步授权通知
     * 格式 ： {"tradeNo":["3010208201803239999954"],"payTime":["2018-03-23 15:58:49"]...}
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/payMedAuthCallBack", method = RequestMethod.GET)
    public String payMedAuthCallBack(HttpServletRequest request) throws ESBException, SystemException {
        Map<String, String[]> map = request.getParameterMap();
        logger.info("授权回调参数---json:{}", JsonUtils.toJSon(map));
        Map<String, String> params = HttpDataUtils.parseRequestMap(map);
        String retFlag = "fail";
        if (params.size() == 0 || StringUtils.isBlank(params.get("sign"))) {
            return null;
        }
        //验签
        String encryptStr = params.remove("sign"); //秘钥
        logger.info(encryptStr);
        String password = SignatureUtils.getSortContent(params);
        logger.info(password);
        boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getInsuranceServicePublicKey());
        if (signFlag) {
            //验签成功
            logger.info("payMedAuthNotify 验签成功");
            String tradeState = params.get("tradeState") != null ? params.get("tradeState") : "";
            if (params.get("authCode") != null && StringUtils.isNotBlank(params.get("authCode"))
                    && "SUCCESS".equals(params.get("tradeState"))) {
                //授权成功
                //retFlag = payMedNotifyService.authMedNotify(params);
            } else {
                logger.info("payCashNotify tradeState == null or tradeState != SUCCESS");
            }
        } else {
            logger.info("payCashNotify 验签失败, 签名失败");
            retFlag = "fail";
        }
        return "redirect:" + params.get("redirectUrl");
    }

    /**
     * 医保   同步支付回调
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/payMedReturn", method = RequestMethod.GET)
    public String payMedReturn(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        logger.info("同步支付回调参数---json:{}", JsonUtils.toJSon(map));
        Map<String, String> params = HttpDataUtils.parseRequestMap(map);
        if (params.size() == 0 || StringUtils.isBlank(params.get("sign"))) {
            return null;
        }
        String retFlag = "fail";
        //验签
        String encryptStr = params.remove("sign"); //秘钥
        logger.info(encryptStr);
        String password = SignatureUtils.getSortContent(params);
        logger.info(password);
        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(encryptStr)) {
            boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getInsurancePublicKey());
            if (true) {
                logger.info("payMedAuthNotify 验签成功");
                String resultMessage = params.get("resultMessage");
                if (StringUtils.isNotBlank(resultMessage) && "响应成功".equals(resultMessage)) {
                } else {
                    logger.info("payMedAuthNotify resultMessage == null or resultMessage != 响应成功");
                }
            } else {
                logger.info("payMedAuthNotify 验签失败, 签名失败");
                retFlag = "fail";
            }
        }
        return retFlag;
    }

    /**
     * 医保 异步支付回调
     * 格式 ： {"tradeNo":["3010208201803239999954"],"payTime":["2018-03-23 15:58:49"]...}
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/payMedNotify", method = RequestMethod.POST, produces = "application/json")
    public String payMedNotify(@RequestBody Map<String, String> params) {
        logger.info("异步支付回调参数---json:{}", JsonUtils.toJSon(params));
        String retFlag = "fail";
        try {
            String encryptStr = params.remove("sign");
            logger.info(encryptStr);
            String password = SignatureUtils.getSortContent(params);
            logger.info(password);
            boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getInsurancePublicKey());
            if (signFlag) {
                logger.info("payMedNotify 验签成功");
                String resultMessage = params.get("resultMessage");
                if (StringUtils.isNotBlank(resultMessage) && "响应成功".equals(resultMessage)) {
                    retFlag = payMedNotifyService.payMedNotify(params);
                } else {
                    logger.info("payMedNotify orderStatus == null or orderStatus != SUCCESS");
                }
            } else {
                logger.info("payMedNotify 验签失败, 签名失败");
                retFlag = "fail";
            }
        } catch (SystemException e) {
            logger.error("payMedNotify - > SystemException", e);
            e.printStackTrace();
            retFlag = "fail";
        } catch (ESBException e) {
            logger.error("payMedNotify - > ESBException", e);
            e.printStackTrace();
            retFlag = "fail";
        }
        return retFlag;
    }

    /**
     * 收银台  医保退费异步通知
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/payMedRefundNotify", method = RequestMethod.POST, produces = "application/json")
    public ResponseData payMedRefundNotify(@RequestBody Map<String, String> params) throws ESBException, SystemException {
        logger.info("退费异步回调参数---json:{}", JsonUtils.toJSon(params));
        ResponseData retFlag = null;
        if(params == null) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), "参数不能为空！");
        }
        if(params.get("mchOrderNo") == null || StringUtils.isBlank(params.get("mchOrderNo"))) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), "mchOrderNo不能为空！");
        }
        //验签
        String encryptStr = params.remove("sign");
        String password = SignatureUtils.getSortContent(params);
         boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getInsurancePublicKey());
        if (signFlag) {
            //验签成功
            logger.info("payMedRefundNotify 验签成功");
            String resultMessage = params.get("resultMessage");
            if (StringUtils.isNotBlank(resultMessage) && "响应成功".equalsIgnoreCase(resultMessage)) {
                retFlag = payMedNotifyService.payMedRefundNotify(params);
            } else {
                logger.info("payMedNotify orderStatus == null or orderStatus != SUCCESS");
            }
        } else {
            logger.info("payMedRefundNotify 验签失败, 签名失败");
            retFlag = new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),"退费失败");
        }
        return retFlag;
    }
}
