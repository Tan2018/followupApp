package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/21 9:31
 * @description 支付请求数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PAY_CASH_REQ")
public class PayCashReq extends BaseModel {

    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 请求URL
     */
    @Column(name = "REQ_URL")
    private String reqUrl;

    /**
     * 商户号
     */
    @Column(name = "MERCHANT_NO")
    private String merchantNo;

    /**
     * 随机数
     */
    @Column(name = "NONCESTR")
    private String noncestr;

    /**
     * 支付渠道编码
     */
    @Column(name = "CHANNEL_CODE")
    private String channelCode;

    /**
     * 支付金额
     */
    @Column(name = "TRADE_TOTAL_FEE")
    private String tradeTotalFee;

    /**
     * 商户订单号
     */
    @Column(name = "OUT_ORDER_NO")
    private String outOrderNo;

    /**
     * 透传参数
     */
    @Column(name = "ATTACH")
    private String attach;

    /**
     * returnUrl(同步回调地址)
     */
    @Column(name = "RETURN_URL")
    private String returnUrl;

    /**
     * 支付方式编码
     */
    @Column(name = "PAY_TYPE_CODE")
    private String payTypeCode;

    /**
     * 请求数据
     */
    @Column(name = "REQ_DATA")
    private String reqData;

    /**
     * 响应数据
     */
    @Column(name = "RESP_DATA")
    private String respData;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
}
