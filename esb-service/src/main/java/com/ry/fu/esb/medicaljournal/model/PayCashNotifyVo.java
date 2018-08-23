package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/20 16:46
 * @description 支付回调通知
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayCashNotifyVo extends BaseModel {

    /**
     * 响应结果代码
     */
    private String tradeState;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 返回时间
     */
    private String timeStamp;

    /**
     * 随机数
     */
    private String nonceStr;

    /**
     * 签名方式
     */
    private String signMode;

    /**
     * 平台支付订单号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String outOrderNo;

    /**
     * 支付金额
     */
    private String totalFee;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 透传参数
     */
    private String attach;

    /**
     * 支付平台
     */
    private String platformCode;

    /**
     * 支付渠道
     */
    private String channelCode;

}
