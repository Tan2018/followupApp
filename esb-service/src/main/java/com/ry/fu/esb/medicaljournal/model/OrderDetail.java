package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_ORDER_DETAIL")
public class OrderDetail implements Serializable {


    private static final long serialVersionUID = -28652112768563763L;

    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * order ID
     */
    @Column(name = "ORDER_ID")
    private String orderId;

    /**
     *缴费费别
     */
    @Column(name = "DETAIL_FEE")
    private String detailFee;

    /**
     *缴费细目流水号，要求唯一
     */
    @Column(name = "DETAIL_ID")
    private String detailID;

    /**
     *缴费细目名称
     */
    @Column(name = "DETAIL_NAME")
    private String detailName;

    /**
     *缴费细目数量
     */
    @Column(name = "DETAIL_COUNT")
    private String detailCount;

    /**
     * 缴费细目单位
     */
    @Column(name = "DETAIL_UNIT")
    private String detailUnit;

    /**
     * 缴费细目金额(单位“分”)
     */
    @Column(name = "DETAIL_AMOUT")
    private String detailAmout;




}
