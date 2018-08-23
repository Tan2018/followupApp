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
 * @Date date 2018/04/09 16:01
 * @description 医保支付请求数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PAY_MED_REQ")
public class PayMedReq extends BaseModel {

    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 用户手机号码
     */
    @Column(name = "USER_PHONE")
    private String userPhone;

    /**
     * 医保用户名称
     */
    @Column(name = "MED_USER_NAME")
    private String medUserName;

    /**
     * 商品名称
     */
    @Column(name = "PRODUCT_NAME")
    private String productName;

    /**
     * 商品描述
     */
    @Column(name = "PRODUCT_DESC")
    private String prodesc;

    /**
     * 医保支付授权码
     */
    @Column(name = "AUTH_CODE")
    private String authCode;

    /**
     * 支付平台订单号(回调结果中获取)
     */
    @Column(name = "ORDER_NO")
    private String orderNo;

    /**
     * 商户订单号 对应Order表的ID
     */
    @Column(name = "MCH_ORDER_NO")
    private String mchOrderNo;

    /**
     * 创建时间
     */
    @Column(name = "MED_CREATE_TIME")
    private Date medCreateTime;

    /**
     * 订单状态 ：0-未支付、1-已支付、2-已过期、3-已取消、
     4-退款中、5-已退款、6-退款失败，7-已取号
     */
    @Column(name = "ORDER_STATUS")
    private String orderStatus;

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
     * attach
     */
    @Column(name = "ATTACH")
    private String attach;

    /**
     * redirectUrl
     */
    @Column(name = "REDIRECT_URL")
    private String redirectUrl;


}
