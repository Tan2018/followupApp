package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/20 15:56
 * @description 支付请求-请求收银台的Request数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayCashReqVo extends BaseModel {

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 透传参数
     */
    private String attach;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 请求时间
     */
    private String nonceStr;

    /**
     * 签名字符串
     */
    private String notifyUrl;

    /**
     * 返回URL
     */
    private String returnUrl;

    /**
     * 外部订单号
     */
    private String outOrderNo;

    /**
     * 支付编码
     */
    private String channelCode;

    /**
     * 超时时间
     */
    private String outTime;

    /**
     * 商户信息
     */
    private String subject;

    /**
     * 签名方式
     */
    private String signMode;

    /**
     * 时间
     */
    private String timeStamp;

    /**
     * 支付金额
     */
    private String tradeTotalFee;

    /**
     * 签名
     */
    private String sign;

    /**
     * 商户退费订单号
     */
    private String outRefundNo;

    /**
     * 退费金额
     */
    private String refundFee;

    /**
     * 退费原因
     */
    private String refundDesc;

    /**
     * 退费回调地址
     */
    private String refundNotifyUrl;


}
