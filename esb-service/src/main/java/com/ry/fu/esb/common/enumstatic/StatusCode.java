package com.ry.fu.esb.common.enumstatic;

/**
 * @Author: telly
 * @Description:错误码枚举
 * @Date: Create in 19:28 2018/1/15
 */
public enum StatusCode {
    //请求成功
    OK("200", "请求成功"),
    //内部错误
    ESB_ERROR("500", "ESB内部错误"),
    //ESB请求超时
    ESB_OUTTIME("501", "ESB请求超时"),
    ESB_ARGU_ERROR("502", "ESB返回数据有误或者请求参数不正确"),

    ESB_USER_NOT_FOUND("503", "用户名不存在"),
    ESB_USER_NOT_LOGIN("504", "账户或密码错误"),
    ESB_NOT_QUERY_DATA("200", "未查询到数据"),


    //内部错误
    INSIDE_ERROR("550", "后台内部错误"),


    XML_FORMAT_ERROR("551", "后台XML格式转换错误"),

    ARGU_ERROR("552", "请求参数错误"),

    AUTH_ERROR("553", "未授权"),

    ARGU_EMPTY("554", "请求的参数不能为空"),

    ARGU_DEFECT("555", "参数缺失"),

    ORDER_UNFOUND("556", "订单号不存在"),

    HTTP_NOT_SUPPORT("557", "HTTP方法不支持"),

    //支付模块
    PAY_ERROR("601", "支付错误"),

    PAY_FAILD("602", "支付失败"),

    NO_PAY("603", "未支付"),

    //退款失败
    REFUND_FAILD("604", "退款失败"),

    PAY_SIGN_ERROR("605", "签名错误"),

    //医保错误返回
    ACCOUNT_ERROR_ONE("660", "医保编码ICD错误"),
    ACCOUNT_ERROR_TWO("661", "医保结果的自负金额小于0"),
    ACCOUNT_ERROR_THREE("662", "其他异常情况");


    private String code;
    private String msg;

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
