package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/10 17:19
 * @description 医保退费请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_MED_REFUND_REQ")
public class PayMedRefundReq extends BaseModel {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 移动后台订单ID
     */
    @Column(name = "MCH_ORDER_NO")
    private String mchOrderNo;

    /**
     * ATTACH
     */
    @Column(name = "ATTACH")
    private String attach;

    /**
     * 退费金额
     */
    @Column(name = "REFUND_AMOUNT")
    private String refundAmount;

    /**
     * 退费原因
     */
    @Column(name = "REFUND_REASON")
    private String refundReason;

    /**
     * 授权码
     */
    @Column(name = "AUTH_CODE")
    private Long authCode;



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

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

}
