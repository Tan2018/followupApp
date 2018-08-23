package com.ry.fu.esb.medicaljournal.enums;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/12 18:08
 * @description 银医通响应Code
 */
public enum SliverMedicaneResponseCode {

    //---------------------------- 医保部分   请求参数校验状态码   ------------------------
    A0("0", "响应成功"),
    A40001("40001", "mchId参数不能为空"),
    A40002("40002", "mchUserId参数不能为空"),
    A40003("40003", "mchUserId参数最大长度不能大于50个字符"),
    A40004("40004", "timestamp参数不能为空"),
    A40005("40005", "timestamp参数格式不正确"),
    A40006("40006", "attach参数最大长度不能大于500个字符"),
    A40007("40007", "sign参数不能为空"),
    A40008("40008", "phoneNumber参数不能为空"),
    A40009("40009", "phoneNumber参数格式不正确"),
    A40010("40010", "medicareUserName参数不能为空"),
    A40011("40011", "redirectUrl参数不能为空"),
    A40012("40012", "verificationCode参数不能为空"),
    A40013("40013", "ygkzUserId参数不能为空"),
    A40014("40014", "productName参数不能为空"),
    A40015("40015", "productName参数最大长度不能大于50"),
    A40016("40016", "productDesc参数最大长度不能大于100"),
    A40017("40017", "mchOrderNo参数不能为空"),
    A40018("40018", "mchOrderNo参数最大长度不能大于50"),
    A40019("40019", "mchPlaceOrderTime参数不能为空"),
    A40020("40020", "mchPlaceOrderTime参数格式不正确"),
    A40021("40021", "orderAmount参数不能为空"),
    A40022("40022", "orderAmount参数只能是正整数"),
    A40023("40023", "orderAmount参数超过允许的最大值"),
    A40024("40024", "mchRefundOrderNo参数不能为空"),
    A40025("40025", "mchRefundOrderNo参数最大长度不能大于50"),
    A40026("40026", "orderNo参数不能为空"),
    A40027("40027", "mchRefundTime参数不能为空"),
    A40028("40028", "mchRefundTime参数格式不正确"),
    A40029("40029", "refundAmount参数不能为空"),
    A40030("40030", "refundAmount参数只能是正整数"),
    A40031("40031", "refundAmount参数超过允许的最大值"),
    A40032("40032", "refundReason参数不能为空"),
    A40033("40033", "refundReason参数最大长度不能大于100"),
    A40034("40034", "authCode参数不能为空"),

    //----------------------------   商户接入信息校验状态码   ------------------------
    A50001("50001", "根据mchId获取商户接入信息失败,请确认mchId是否正确"),
    A50002("50002", "此商户已被禁用"),
    A50003("50003", "商户公钥未上传"),
    A50004("50004", "未换取支付平台公钥"),
    A50005("50005", "未填写支付结果通知回调url"),
    A50006("50006", "未填写退费结果通知回调url"),
    A50007("50007", "未填写医保绑卡结果通知回调url"),
    A50008("50008", "未填写商户支付授权目录"),

    //----------------------------   商户医保支付信息校验状态码   ------------------------
    A60001("60001", "根据mchId获取商户医保支付配置信息失败,请确认mchId是否正确"),
    A60002("60002", "未配置商户对应的银联医保商户号"),
    A60003("60003", "未配置商户对应的银联医保终端号"),
    A60004("60004", "未配置商户对应的银联医保终端主密钥"),
    A60005("60005", "未获取到银联医保MAC密钥,请签到获取"),

    //----------------------------   医保业务状态码   ------------------------
    A70001("70001", "短信验证码错误"),
    A70002("70002", "未查询到医保卡授权信息"),
    A70003("70003", "订单不存在"),
    A70004("70004", "错误的订单,订单信息不一致"),
    A70005("70005", "重复的订单"),
    A70006("70006", "订单不是已支付状态,不允许退费"),


    //----------------------------   异常类状态码   ------------------------
    A100001("100001", "不合法的mime类型"),
    A100002("100002", "没有获取到任何请求参数"),
    A100003("100003", "验签失败,非法的请求参数"),
    A100004("100004", "网络异常"),

    //--------------------------------- 收银台状态码 -------------------------
    SUCCESS("SUCCESS", "请求成功"),
    FAIL("FAIL", "请求失败"),
    SYSTEMERROR("SYSTEMERROR", "接口后台错误"),
    PARAM_ERROR("PARAM_ERROR", "请求参数错误"),
    PARAM_JSON_FORMAT_ERROR("PARAM_JSON_FORMAT_ERROR", "请求参数JSON格式错误"),
    SIGN_FAIL("SIGN_FAIL", "签名验证失败"),
    TIME_STAMP_ERROR("TIME_STAMP_ERROR", "请使用标准网络时间"),
    METHOD_ERROR("METHOD_ERROR", "不支持的请求方式"),
    INVALID_APPID_OR_MERCHANTNO("INVALID_APPID_OR_MERCHANTNO", "appId或者商户号错误"),
    OUTORDERNO_USED("OUTORDERNO_USED", "商户订单号重复"),
    INVALID_MERCHANTNO("INVALID_MERCHANTNO", "商户号错误或商户未启用"),
    SERVICE_UPGRADE("SERVICE_UPGRADE", "服务正在升级中，敬请期待"),
    OUTORDERNO_NO_EXIST("OUTORDERNO_NO_EXIST", "商户订单不存在"),
    OUTORDERTRADE_ERROR("OUTORDERTRADE_ERROR", "商户订单状态不支持退款"),
    OUTORDERTRADE_EXCEPTION("OUTORDERTRADE_EXCEPTION", "商户订单异常"),
    OUTORDERTRADE_FREE_ERROR("OUTORDERTRADE_FREE_ERROR", "商户订单金额错误"),

    NO_PAYMENT("NO_PAYMENT", "待支付"),
    PAYMENT("PAYMENT", "已支付"),
    REFUND("REFUND", "已退费"),
    PAYMENTING("PAYMENTING", "支付中"),
    REFUNDING("REFUNDING", "退款中"),
    CLOSE("CLOSE", "订单关闭"),
    ;

    private String code;
    private String name;

    private SliverMedicaneResponseCode(final String code, final String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

}
