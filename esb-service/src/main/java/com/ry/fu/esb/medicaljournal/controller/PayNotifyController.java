package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.properties.RSAKeyProperties;
import com.ry.fu.esb.common.utils.HttpDataUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.medicaljournal.service.PayNotifyService;
import com.ry.fu.esb.medicaljournal.util.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/13 15:26
 * @description 移动端请求的接口
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/notify")
public class PayNotifyController {

    private static Logger logger = LoggerFactory.getLogger(PayNotifyController.class);

    @Autowired
    private PayNotifyService payNotifyService;

    @Autowired
    private RSAKeyProperties rsaKeyProperties;

    /**
     * 收银台 支付通知
     *  格式 ： {"tradeNo":["3010208201803239999954"],"payTime":["2018-03-23 15:58:49"]...}
     * @param
     * @return
     */
    @RequestMapping(value = "/payCashNotify",method =  RequestMethod.POST)
    public String payCashNotify(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        logger.info("异步支付回调参数---json:{}",JsonUtils.toJSon(map));
        Map<String, String> params = HttpDataUtils.parseRequestMap(map);

        String retFlag = "fail";
        try {
            //验签
            String encryptStr = params.remove("sign"); //秘钥
            logger.info(encryptStr);
            String password = SignatureUtils.getSortContent(params);
            logger.info(password);
            boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getCasherPublicKey());
            if(signFlag) {
                //验签成功
                logger.info("payCashNotify 验签成功");
                String tradeState = params.get("tradeState") != null ? params.get("tradeState") : "";
                if(tradeState != null && StringUtils.isNotBlank(tradeState)
                        && "SUCCESS".equals(tradeState)) {
                    //支付成功
                    retFlag = payNotifyService.payCashNotify(params);
                } else {
                    logger.info("payCashNotify tradeState == null or tradeState != SUCCESS");
                }
            } else {
                logger.info("payCashNotify 验签失败, 签名失败");
                retFlag = "fail";
            }
        } catch (SystemException e) {
            logger.error("payCashNotify - > SystemException", e);
            e.printStackTrace();
            retFlag = "fail";
        } catch (ESBException e) {
            logger.error("payCashNotify - > ESBException", e);
            e.printStackTrace();
            retFlag = "fail";
        }
        return retFlag;
    }

    /**
     * 收银台 退费通知，HIS线下退费，医程通主动调用
     * params:{"outOrderNo":"1234"}等数据格式
     * @return
     */
    @RequestMapping("/payCashRefundNotify")
    public String payCashRefundNotify(HttpServletRequest request) {
        try {
            Map<String, String[]> map = request.getParameterMap();
            logger.info("支付退费回调参数---json:{}",JsonUtils.toJSon(map));
            if(map.size() == 0) {
                logger.error("退费异步回调参数个数为0");
                return "fail";
            }
            Map<String, String> params = HttpDataUtils.parseRequestMap(map);
            payNotifyService.payCashRefundNotify(params);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }


}
