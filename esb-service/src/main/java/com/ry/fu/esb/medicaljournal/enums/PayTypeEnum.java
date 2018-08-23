package com.ry.fu.esb.medicaljournal.enums;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/16 17:34
 * @description 支付类型枚举
 */
public enum PayTypeEnum {
    wechat_jsapi("1","微信服务窗支付"),
    wechat_app("2","微信APP支付"),
    wechat_instcard("3","微信医保支付"),
    wechat_scan_code("4","微信扫码支付"),
    wechat_h5("5","微信H5支付"),
    wechat_common_jsapi("6","微信服务窗支付(特约商户)"),
    wechat_common_app("7","微信APP支付(特约商户)"),
    alipay_h5("8","支付宝H5支付"),
    alipay_app("9","支付宝APP支付"),
    alipay_instcard("10","支付宝医保支付"),
    unionpay_sdk("11","银联sdk支付(支持APP支付)"),
    unionpay_apply_pay("12","银联applyPay(仅支持IOS)"),
    unionpay_h5("13","银联H5支付"),
    unionpay_ds("14","银联代收支付"),
    sunshier_wallet("15","阳光钱包支付"),
    //阳光医保支付
    sunshier_instcard("16","阳光医保支付"),
    unionpay_small_quick("17","银联小额快捷（QR）");

    private String code;
    private String msg;

    private PayTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //根据枚举的code获取msg的方法
    public static String getMsgByCode(String code){
        for(PayTypeEnum responseEnum : PayTypeEnum.values()) {
            if(responseEnum.getCode().equals(code)){
                return responseEnum.msg;
            }
        }
        return null;
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
