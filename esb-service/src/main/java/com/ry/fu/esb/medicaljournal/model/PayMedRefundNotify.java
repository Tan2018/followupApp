package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/10 19:30
 * @description 医保退费通知
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_MED_REFUND_NOTIFY")
public class PayMedRefundNotify extends BaseModel {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 请求id
     */
    @Column(name = "REQ_ID")
    private Long reqId;

    /**
     * 支付平台订单号
     */
    @Column(name = "ORDER_ID")
    private Long orderNo;

    /**
     * 支付平台退费订单号
     */
    @Column(name = "REFUND_ORDER_NO")
    private String refundOrderNo;

    /**
     * 支付平台订单退费时间
     */
    @Column(name = "ORDER_REFUND_TIME")
    private String orderRefundTime;


    /**
     * 商户订单号
     */
    @Column(name = "MCH_ORDER_NO")
    private String mchOrderNo;

    /**
     * 商户退费订单号
     */
    @Column(name = "MCH_REFUND_ORDER_NO")
    private String mchRefundOrderNo;

    /**
     * 订单状态
     */
    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    /**
     * 退费请求数据
     */
    @Column(name = "REQ_DATA")
    private String reqData;

    /**
     * 退费响应数据
     */
    @Column(name = "RESP_DATA")
    private String respData;

}
