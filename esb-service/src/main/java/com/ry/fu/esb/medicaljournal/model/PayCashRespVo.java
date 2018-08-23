package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/20 15:56
 * @description 支付请求的响应数据-请求收银台的Response数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayCashRespVo extends BaseModel {

    /**
     * 响应结果代码
     */
    private String returnCode;

    /**
     * 响应结果描述
     */
    private String returnMsg;

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
     * 签名字符串
     */
    private String sign;

    /**
     * 签名方式
     */
    private String signMode;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 商户订单号
     */
    private String outOrderNo;

    /**
     * 平台交易订单号
     */
    private String tradeNo;

    /**
     * 支付金额
     */
    private String totalFee;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 请求支付方式地址
     */
    private String cashierUrl;

    /**
     * 透传参数
     */
    private String attach;

    /**
     * 交易标识
     */
    private String cashierId;

    /**
     * 支付平台
     */
    private String platformCode;

    /**
     * 支付渠道
     */
    private String channelCode;

    /**
     * 商户退费订单号
     */
    private String outRefundNo;

    /**
     * 平台退费订单号
     */
    private String refundNo;

    /**
     * 退费金额
     */
    private String refundFee;

    /**
     * 退费原因
     */
    private String refundDesc;

}
