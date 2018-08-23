package com.ry.fu.net.transfer.controller;

import com.ry.fu.net.transfer.util.SMSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/7/13 16:46
 * @description SMS短信接口问题
 */
@RestController
@RequestMapping("/netService/v1/transfer/sms")
public class SMSController {

    private static Logger logger = LoggerFactory.getLogger(SMSController.class);

    @RequestMapping(value = "/sendSMSByYunPian", method = RequestMethod.POST)
    public String sendSMSByYunPian(@RequestBody Map<String, String> map) {
        String phoneNumber = map.get("phoneNumber");    //手机号
        String verifyCode = map.get("verifyCode");      //验证码
        String retData = SMSUtil.sendVerifyCodeByYunPin(phoneNumber, verifyCode);
        logger.info("phoneNumber : {}, verifyCode: {}, retData:{}", phoneNumber, verifyCode, retData);
        return retData;
    }

}
