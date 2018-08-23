package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Seaton
 * @version V1.0.0
 * @description 查询未支付订单通知用户支付
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegOrderVO implements Serializable {

    private static final long serialVersionUID = -3587139126241290945L;
    /**
     * 用户ID
     */
    private Long accountId;

    /**
     * 患者ID
     */
    private Long patientId;

    private String patientName;

    private String orderId;

    /**
     * 订单创建时间
     */
    private Date createdTime;

    /**
     * 候诊时间
     */
    private  Date waitTime;

    /**
     * 就诊时段
     */
    private  String visitTime;

    /**
     * 候诊室
     */
    private  String diagnoseRoom;



}
