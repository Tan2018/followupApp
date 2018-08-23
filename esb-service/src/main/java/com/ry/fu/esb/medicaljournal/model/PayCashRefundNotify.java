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
 * @Date 2018/4/4 15:16
 * @description 收银台退费请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_CASH_REFUND_NOTIFY")
public class PayCashRefundNotify extends BaseModel {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 外部订单ID，也就是移动端订单表主键ID
     */
    @Column(name = "OUT_ORDER_NO")
    private String outOrderNo;

    /**
     * 退款流水号
     */
    @Column(name = "REFUND_NO")
    private String refundNo;

    /**
     * 移动后台订单ID
     */
    @Column(name = "NONCESTR")
    private String noncestr;

    /**
     * 退费费用
     */
    @Column(name = "ATTACH")
    private String attach;

    /**
     * 订单金额
     */
    @Column(name = "TRADE_TOTAL_FEE")
    private String tradeTotalFee;

    @Column(name = "REFUND_FEE")
    private String refundFee;

    /**
     * 退费响应数据
     */
    @Column(name = "RESP_DATA")
    private String respData;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

}
