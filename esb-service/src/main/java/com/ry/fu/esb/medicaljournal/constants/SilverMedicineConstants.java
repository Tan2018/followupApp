package com.ry.fu.esb.medicaljournal.constants;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/12 14:34
 * @description 银医通通过描述
 */
public class SilverMedicineConstants {

    //======================== 支付编码 =================================

    /**
     * 微信服务窗支付
     */
    public final static String WECHAT_JSAPI = "wechat_jsapi";
    /**
     * 微信APP支付
     */
    public final static String WECHAT_APP = "wechat_app";
    /**
     * 微信医保支付
     */
    public final static String WECHAT_INSTCARD = "wechat_instcard";
    /**
     * 微信H5支付
     */
    public final static String WECHAT_H5 = "wechat_h5";
    /**
     * 微信服务窗支付
     */
    public final static String WECHAT_COMMON_JSAPI = "wechat_common_jsapi";
    /**
     * 微信APP支付(特约商户)
     */
    public final static String WECHAT_COMMON_APP = "wechat_common_app";
    /**
     * 支付宝H5支付
     */
    public final static String ALIPAY_H5 = "alipay_h5";
    /**
     * 支付宝APP支付
     */
    public final static String ALIPAY_APP = "alipay_app";
    /**
     * 支付宝医保支付
     */
    public final static String ALIPAY_INSTCARD = "alipay_instcard";
    /**
     * 银联sdk支付(支持APP支付)
     */
    public final static String UNIONPAY_SDK = "unionpay_sdk";
    /**
     * 银联applyPay(仅支持IOS)
     */
    public final static String UNIONPAY_APPLY_PAY = "unionpay_apply_pay";
    /**
     * 银联H5支付
     */
    public final static String UNIONPAY_H5 = "unionpay_h5";
    /**
     * 银联代收支付
     */
    public final static String UNIONPAY_DS = "unionpay_ds";
    /**
     * 阳光钱包支付
     */
    public final static String SUNSHIER_WALLET = "sunshier_wallet";
    /**
     * 阳光医保支付
     */
    public final static String SUNSHIER_INSTCARD = "unionpay_ds";
    /**
     * 银联小额快捷（QR）
     */
    public final static String UNIONPAY_SMALL_QUICK = "unionpay_ds";

    /**
     * 微信标识
     */
    public final static String WECHAT = "wechat";

    /**
     * 支付宝标识
     */
    public final static String ALIPAY = "alipay";

    /**
     * 银联支付标识
     */
    public final static String UNIONPAY = "unionpay";

    /**
     * 阳光支付标识
     */
    public final static String SUNSHIEN = "sunshien";

    /**
     * 支付请求
     */
    public final static String PAY_CASH_URL = "/services/rest/v1/trade/pay/";

    /**
     * 医保支付请求
     */
    public final static String MED_PAY_CASH_URL = "/api/medicare/cashier";
    /**
     * 医保授权请求
     */
    public final static String MED_PAY_AUTH_URL = "/api/medicare/authorize";

    /**
     * 医保查询订单状态
     */
    public final static String MED_PAY_QUERY_URL = "/api/medicare/queryOrderStatus";

    /**
     * 医保退费接口
     */
    public final static String PAY_MED_EFUND_URL = "/api/medicare/refund";

    /**
     * 支付查询
     */
    public final static String PAY_CASH_QUERY_URL = "/services/rest/v1/trade/payQuery/";


    /**
     * 申请退费
     */
    public final static String PAY_CASHR_EFUND_URL = "/services/rest/v1/trade/refund/";


    /**
     * 退费查询
     */
    public final static String PAY_CASHR_EFUND_QUERYURL = "/services/rest/v1/trade/refundQuery/";

    /**
     * 小额支付
     */
    public final static String PAY_CASH_LIMIT_URL = "/services/rest/v1/trade/limitPay/";


}
