package com.ry.fu.net.im.controller;

import com.ry.fu.net.common.http.HttpRequest;
import com.ry.fu.net.common.utils.JsonUtils;
import com.ry.fu.net.im.constant.NetEaseCloudLetterConstant;
import com.ry.fu.net.jpush.controller.CommunalJpushConntroller;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 网易云信controller
 */
@RequestMapping("/netService/v1/instantmessaging/netEaseCloudLetter")
@RestController
public class NetEaseCloudLetterController {

    private static Logger logger = LoggerFactory.getLogger(CommunalJpushConntroller.class);

    /**
     * 网易云公共请求消息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/imSendHttpRequest", method = RequestMethod.POST)
    public String imSendHttpRequest(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
        long t1 = System.currentTimeMillis();
        System.out.println("即时通讯公共接口请求参数---json：{}" + JsonUtils.toJSon(params) + ",调用人ip：" + request.getRemoteAddr());
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        String result = null;
        if (params.get("requestType").toString().equals("CREATE_COMMUNICATION_ID")) {//注册网易云信id
            if (params.get("test") == null) {
                nvps.add(new BasicNameValuePair("accid", params.get("accid").toString()));
                nvps.add(new BasicNameValuePair("token", params.get("token").toString()));
                nvps.add(new BasicNameValuePair("name", params.get("name").toString()));
                logger.info("正式appkey注册网易云信id接口请求参数：json{}" + JsonUtils.toJSon(nvps));
                result = HttpRequest.postRequest(NetEaseCloudLetterConstant.CREATE_COMMUNICATION_ID, nvps, NetEaseCloudLetterConstant.APP_KEY, NetEaseCloudLetterConstant.APP_SECRET);
                logger.info("正式appkey注册网易云信id接口响应参数：json{}" + JsonUtils.toJSon(result));
            } else {
                nvps.add(new BasicNameValuePair("accid", params.get("accid").toString()));
                nvps.add(new BasicNameValuePair("token", params.get("token").toString()));
                nvps.add(new BasicNameValuePair("name", params.get("name").toString()));
                logger.info("测试appkey注册网易云信id接口请求参数：json{}" + JsonUtils.toJSon(nvps));
                result = HttpRequest.postRequest(NetEaseCloudLetterConstant.CREATE_COMMUNICATION_ID, nvps, NetEaseCloudLetterConstant.TEST_APP_KEY, NetEaseCloudLetterConstant.TEST_APP_SECRET);
                logger.info("测试appkey注册网易云信id接口响应参数：json{}" + JsonUtils.toJSon(result));
            }
        } else if (params.get("requestType").toString().equals("GET_USERINFO")) {//获取用户名片
            if (params.get("test") == null) {
                nvps.add(new BasicNameValuePair("accids", params.get("accids").toString()));
                logger.info("正式appkey获取用户名片接口请求参数：json{}" + JsonUtils.toJSon(nvps));
                result = HttpRequest.postRequest(NetEaseCloudLetterConstant.GET_USERINFO, nvps, NetEaseCloudLetterConstant.APP_KEY, NetEaseCloudLetterConstant.APP_SECRET);
                logger.info("正式appkey请求获取用户名片接口响应参数：json{}" + JsonUtils.toJSon(result));
            } else {
                nvps.add(new BasicNameValuePair("accids", params.get("accids").toString()));
                logger.info("测试appkey获取用户名片接口请求参数：json{}" + JsonUtils.toJSon(nvps));
                result = HttpRequest.postRequest(NetEaseCloudLetterConstant.GET_USERINFO, nvps, NetEaseCloudLetterConstant.TEST_APP_KEY, NetEaseCloudLetterConstant.TEST_APP_SECRET);
                logger.info("测试appkey请求获取用户名片接口响应参数：json{}" + JsonUtils.toJSon(result));
            }
        } else if (params.get("requestType").toString().equals("UPDATE_ACCID")) {//修改网易云通信密码
            if (params.get("test") == null) {
                nvps.add(new BasicNameValuePair("accid", params.get("accid").toString()));
                nvps.add(new BasicNameValuePair("token", params.get("token").toString()));
                logger.info("正式appkey修改网易云信密码接口请求参数：json{}" + JsonUtils.toJSon(nvps));
                result = HttpRequest.postRequest(NetEaseCloudLetterConstant.UPDATE_ACCID, nvps, NetEaseCloudLetterConstant.APP_KEY, NetEaseCloudLetterConstant.APP_SECRET);
                logger.info("正式appkey请求修改网易云信密码接口响应参数：json{}" + JsonUtils.toJSon(result));
            } else {
                nvps.add(new BasicNameValuePair("accid", params.get("accid").toString()));
                nvps.add(new BasicNameValuePair("token", params.get("token").toString()));
                logger.info("测试appkey修改网易云信密码接口请求参数：json{}" + JsonUtils.toJSon(nvps));
                result = HttpRequest.postRequest(NetEaseCloudLetterConstant.UPDATE_ACCID, nvps, NetEaseCloudLetterConstant.TEST_APP_KEY, NetEaseCloudLetterConstant.TEST_APP_SECRET);
                logger.info("测试appkey请求修改网易云信密码接口响应参数：json{}" + JsonUtils.toJSon(result));
            }
        }
        long t2 = System.currentTimeMillis();
        logger.info("---net-service响应时长---" + (t2 - t1));
        return result;
    }
}
