package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author Seaton
 * @version V1.0.0
 * @description 查询未支付订单通知用户支付
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaySucesessRecordVO implements Serializable {

    private static final long serialVersionUID = 8720514962781512932L;


    /**
     * 订单ID
     */
    private String orderId;


    /**
     * 总费用
     */
    private Long totalFee;

    /**
     * 个人费用
     */
    private Long personalFee;

    /**
     * 就诊人
     */
    private String patientName;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 地点
     */
    private String orderDesc;

    /**
     * 费用明细
     */
    @Transient
    private List<OrderDetail> orderDetail;

}
