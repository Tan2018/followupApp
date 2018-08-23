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
 * @Date 2018/3/21 19:27
 * @description description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_ORDER")
public class Order extends BaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "REG_ID")
    private Long regId;

    /**
     * 订单来源
     */
    @Column(name = "ORDER_SOURCE")
    private String orderSource;

    /**
     * HIS挂号ID
     */
    @Column(name = "HIS_REG_NO")
    private String hisRegNo;

    /**
     * 订单类型
     */
    @Column(name = "ORDER_TYPE")
    private String orderType;

    /**
     * 费别：诊查费、总费用
     */
    @Column(name = "FEE_TYPE")
    private String feeType;

    /**
     * 订单状态 ：0.待支付,1.已支付,2.已过期,3.已取消,4.退款中(目前暂未用到),5.已退款
     */
    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    /**
     * HIS的挂号记录状态 ：0-已挂号;1-已取消;2-已支付;3-已取号;4-已退费
     */
    @Column(name = "HIS_ORDER_STATUS")
    private String hisOrderStatus;

    /**
     * 挂号流程状态 ：0.未取号,1.已取号,2.已就诊
     */
    @Column(name = "REGISTER_STATUS")
    private String registerStatus;

    /**
     * 总费用
     */
    @Column(name = "TOTAL_FEE")
    private String totalFee;

    /**
     * 个人自负金额
     */
    @Column(name = "PERSONAL_FEE")
    private String personalFee;

    /**
     * 医保记账金额
     */
    @Column(name = "MEDICAL_FEE")
    private String medicalFee;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 操作人
     */
    @Column(name = "OPERATER")
    private String operater;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 上一次修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 状态描述
     */
    @Column(name = "STATUS_REMARK")
    private String statusRemark;

    /**
     * 订单名称
     */
    @Column(name = "ORDER_NAME")
    private String orderName;

    /**
     * 医嘱id
     */
    @Column(name = "DOCTOR_ADVICE_ID")
    private String doctorAdviceId;


    /**
     * 	缴费成功HIS返回的订单号
     */
    @Column(name = "HIS_ORDER_ID")
    private String hisOrderId;

    /**
     * 自动取消时间
     */
    @Column(name = "AUTO_CANCEL_TIME")
    private Date autoCancelTime;

    @Column(name = "REFUND_DATE")
    private Date refundDate;

    /**
     * 医程通支付流水号
     */
    @Column(name = "TRADE_NO")
    private String tradeNo;

    /**
     * 第三方支付流水号，如医保、支付宝、微信等
     */
    @Column(name = "AGT_TRADE_NO")
    private String agtTradeNo;

    /**
     * 支付方式：如alipay_h5
     */
    @Column(name = "PAY_TYPE")
    private String payType;

    /**
     * 订单相关信息描述(位置信息)
     */
    @Column(name = "ORDERDESC")
    private String orderDesc;

    @Column(name = "PAY_SUCCESS_TIME")
    public Date paySuccessTime;

}
