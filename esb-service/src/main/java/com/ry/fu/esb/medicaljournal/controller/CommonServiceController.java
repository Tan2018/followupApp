package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 公共服务
 *
 * @author :walker
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/common")
public class CommonServiceController {
    private Logger logger = LoggerFactory.getLogger(CommonServiceController.class);

    @Autowired
    private PublicService publicService;

    /**
     * 获取验证码
     *
     * @param
     * @return
     * @Author:walker
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
    public ResponseData verifyCode(@RequestBody Map<String, String> params) {
        String phone = params.get("phoneNumber");
        if (StringUtils.isBlank(phone)) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "手机号phoneNumber不能为空");
        }
        try {
            ResponseData verifyCode = publicService.getVerifyCode(phone);
            return verifyCode;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(StatusCode.ESB_ERROR.getCode(), "发送短信失败", "发送短信失败");
        }

    }

    /**
     * 注册
     */
    @RequestMapping(value = "/userRegistration", method = RequestMethod.POST)
    public ResponseData userRegistration(@RequestBody Map<String, Object> params) {
        Map<String, Boolean> result = publicService.handleRegistration(params);
        if (result.get("verifyCodeExpired") != null && result.get("verifyCodeExpired")) {
            return sendData("500", "验证码已过期", result);
        } else if (result.get("hasRegistration") != null && result.get("hasRegistration")) {
            return sendData("500", "该号码已注册，请直接登录", result);
        }
        return sendData("200", "注册成功", result);
    }

    /**
     * 登录
     * @param params
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public ResponseData userLogin(@RequestBody Map<String, Object> params) {
        return publicService.handleLogin(params);
    }

    /**
     * 重置密码
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseData resetPassword(@RequestBody Map<String, Object> params) {
        logger.info(params.toString());
        try {
            Map<String, Boolean> result = publicService.resetPassword(params);
            if (result.get("verifyCodeExpired") != null && result.get("verifyCodeExpired")) {
                return sendData("500", "验证码已过期，请重新获取验证码！", result);
            }
            return sendData("200", "重置密码成功！", result);
        } catch (Exception e) {
            e.printStackTrace();
            return sendData("500", "重置失败！",null);
        }
    }

    public ResponseData sendData(String status, String msg, Map<String, Boolean> data) {
        ResponseData responseData = new ResponseData();
        responseData.setData(data);
        responseData.setStatus(status);
        responseData.setMsg(msg);
        return responseData;
    }
}
