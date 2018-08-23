package com.ry.fu.esb.common.constants;

import org.apache.http.entity.ContentType;

public class Constants {

    private Constants(){

    }

    public final static String UTF_8 = "UTF-8";
    public final static Integer STATUS = 0;
    public final static String MSGCODE = "操作成功";

    public final static String MSG_CODE = "000000";//接口返回代码
    public final static String AUTH_ERROR_CODE = "000401";//未授权
    public final static String ARGU_ERROR_CODE = "000601";//未授权

    public final static String DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    public final static ContentType DEFAULT_CONTENTTYPE = ContentType.create("text/plain", Constants.UTF_8);

    public final static ContentType APPLICATION_JAON = ContentType.create("application/json", Constants.UTF_8);

    public final static ContentType URLENCODED_CONTENTTYPE = ContentType.create("application/x-www-form-urlencoded", Constants.UTF_8);

    /**
     * Http请求成功消息Code
     */
    public final static int HTTP_RESPONSE_SUCCESS = 200;

    /**
     * 验证码
     */
    public final static int VERIFY_CODE_LENTH = 6;
    /**
     * 验证码有效时间
     */
    public final static int VERIFY_CODE_VALID_TIME = 9000;

    public final static int ESB_TOKEN_CHECK_TIME = 10 * 60;

    public final static String VERIFY_CODE_PREFIX = "VERIFY_CODE_";

    public final static String SILVER_MEDICINE_TYPE_NO = "40";//银医通通道40


    /**
     * 订单对前端显示超时关闭时限(分钟)
     */
    public final static int ORDER_OVER_TIME = 10;

    /**
     * 后台订单超时关闭时限(分钟)
     */
    public final static int ORDER_CLOSE_TIME = 15;

    public final static String ORDER_NO_PAY_STATUS = "0";
    public final static String ORDER_PAID_STATUS = "1";
    public final static String ORDER_EXPIRED_STATUS = "2";
    public final static String ORDER_CANCELLED_STATUS = "3";
    public final static String ORDER_REFUNDED_STATUS = "5";



    /**
     * 序列名称
     */
    public final static String M_ACCOUNT_SEQ = "SQUENCES.M_ACCOUNT_SEQ";
    public final static String M_ORG_SEQ = "SQUENCES.M_ORG_SEQ";
    public final static String M_PATIENT_SEQ = "SQUENCES.M_PATIENT_SEQ";
    public final static String M_CONTACTS_SEQ = "SQUENCES.M_CONTACTS_SEQ";
    public final static String M_DOCTOR_SEQ = "SQUENCES.M_DOCTOR_SEQ";
    public final static String M_ORDERDETAIL_SEQ = "SQUENCES.M_ORDERDETAIL_SEQ";
    public final static String M_FAVORITEDOCTOR_SEQ = "SQUENCES.M_FAVORITEDOCTOR_SEQ";
    public final static String M_REGISTRATION_SEQ = "SQUENCES.M_REGISTRATION_SEQ";
    public final static String M_ORDER_SEQ = "SQUENCES.M_ORDER_SEQ";
    public final static String SQUENCES = "SQUENCES.";

    /**
     * 短信的域名
     */
    public final static String GET_VERIFY_CODE_URL = "http://csmsms.91160.com/index.php?token=4asdiq3JLIfq3fijlffkiUofqiersdkjsaiKIkjfeL&mobile=";

    /**
     * 短信验证码的Token
     */
    public final static String VERIFY_CODE_TOKEN = "4asdiq3JLIfq3fijlffkiUofqiersdkjsaiKIkjfeL";

    public final static String ESB_ACCESS_TOKEN = "ACCESS_TOKEN_FUA_SYSTEM";

    /**
     * ESB查询到 未查询数据
     */
    public final static String ESB_FIND_NO_DATA = "未查询到数据";
    /**
     * ESB
     */
    //获取Token的地址
    public final static String ESB_TOKEN_ADDRESS = "/hip/hipService/getToken";
    //ESB 发送数据的地址
    public final static String ESB_SEND_CONTENT_ADDRESS = "/hip/hipService/queryPort";
    //网易云信请求地址
    public final static String INSTANT_MESSAGING = "/net/netService/v1/instantmessaging/netEaseCloudLetter/imSendHttpRequest";
    //极光推送请求推送地址
    public final static String JPUSH_ADDRESS = "/net/netService/v1/jpush/communalJpush/publicSendPush";

    public final static String JPUSH_SEND_ADDRESS = "/net/netService/v1/jpush/communalJpush/sendPush";

    //云片网短信接口
    public final static String SMS_YUNPIAN_URL = "/net/netService/v1/transfer/sms/sendSMSByYunPian";


}
