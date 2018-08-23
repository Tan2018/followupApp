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
 * @Date 2018/3/26 10:34
 * @description 收银台支付通知
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PAY_CASH_NOTIFY")
public class PayCashNotify extends BaseModel {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "REQ_ID")
    private Long reqId;

    @Column(name = "MERCHANT_NO")
    private String merchantNo;

    @Column(name = "NONCESTR")
    private String noncestr;

    @Column(name = "OUT_ORDER_NO")
    private String outOrderNo;

    @Column(name = "ATTACH")
    private String attach;

    @Column(name = "NOTIFY_TYPE")
    private String notifyType;

    @Column(name = "NOTIFY_DATA")
    private String notifyData;

    @Column(name = "CREATE_TIME")
    private Date createTime;

}
