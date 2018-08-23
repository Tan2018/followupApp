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
 * @Date date 2018/04/09 16:01
 * @description 医保支付通知
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PAY_MED_NOTIFY")
public class PayMedNotify extends BaseModel {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "REQ_ID")
    private Long reqId;

    /**
     * 订单状态 ：0-未支付、1-已支付、2-已过期、3-已取消、
     4-退款中、5-已退款、6-退款失败，7-已取号
     */
    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "ORDER_PAY_TIME")
    private String orderPayTime;

    @Column(name = "NOTIFY_DATA")
    private String notifyData;










//    @Column(name = "MERCHANT_NO")
//    private String merchantNo;
//
//    @Column(name = "NONCESTR")
//    private String noncestr;
//
//    @Column(name = "OUT_ORDER_NO")
//    private String outOrderNo;
//
//    @Column(name = "ATTACH")
//    private String attach;
//
//    @Column(name = "NOTIFY_TYPE")
//    private String notifyType;
//
//
//    @Column(name = "CREATE_TIME")
//    private Date createTime;

}
