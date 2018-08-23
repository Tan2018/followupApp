package com.ry.fu.esb.medicaljournal.util;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.http.HttpRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/7/5 11:22
 * @description 短信接口，主要有两个厂家
 *     1-医院内部91160
 *     2-云片厂家
 */
public class SMSUtil {

    //---------------------------云片厂家------------------------------------------------

    //查账户信息的http地址
    private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";
    //智能匹配模板发送接口的http地址
    private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
    //模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
    //发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";
    //绑定主叫、被叫关系的接口http地址
    private static String URI_SEND_BIND = "https://call.yunpian.com/v2/call/bind.json";

    //解绑主叫、被叫关系的接口http地址
    private static String URI_SEND_UNBIND = "https://call.yunpian.com/v2/call/unbind.json";

    /**
     * 医院原有接口 - 发送验证码
     * @param phoneNumber 手机号
     * @param verifyCode 验证码
     * @return
     */
    public static String sendVerifyCode(String phoneNumber, String verifyCode) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        String url = Constants.GET_VERIFY_CODE_URL + phoneNumber + "&content=验证码为:" + verifyCode;
        //{"sslid":"990001935734","msg":"发送成功","state":1}
        return HttpRequest.post(url, URLEncodedUtils.format(list, "UTF-8"), Constants.DEFAULT_CONTENTTYPE).trim();
    }

    /**
     *  云片短信厂家
     * @param phoneNumber 手机号
     * @param verifyCode 验证码
     * @return
     */
    public static String sendVerifyCodeByYunPin(String phoneNumber, String verifyCode) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        String apiKey = "d9b51267f4bd9a4c5bff1693652076d0";
        BasicNameValuePair apikey = new BasicNameValuePair("apikey", apiKey);
        list.add(apikey);
        String content = "【孙逸仙纪念医院】验证码为:"+verifyCode;
        BasicNameValuePair text = new BasicNameValuePair("text", content);
        list.add(text);
        BasicNameValuePair mobile = new BasicNameValuePair("mobile", phoneNumber);
        list.add(mobile);
        return HttpRequest.post(URI_SEND_SMS, list);
    }

    public static void main(String[] args) {
        System.out.println(sendVerifyCodeByYunPin("18802007610", "123456"));
    }

}
