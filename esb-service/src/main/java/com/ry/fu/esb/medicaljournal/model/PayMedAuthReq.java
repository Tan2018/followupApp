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
 * @Date date 2018/04/09 16:00
 * @description 医保授权请求数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_MED_AUTH_REQ")
public class PayMedAuthReq extends BaseModel {

    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 医保用户名称
     */
    @Column(name = "MED_USER_NAME")
    private String medUserName;

    /**
     * 用户身份证
     */
    @Column(name = "ID_CARD")
    private String idCard;

    /**
     * redirectUrl
     */
    @Column(name = "REDIRECT_URL")
    private String redirectUrl;

    /**
     * 用户手机号码
     */
    @Column(name = "USER_PHONE")
    private String userPhone;

    /**
     * 渗透参数（值同id，目的授权响应：payMedAuthNotify通过此找到req）
     */
    @Column(name = "ATTACH")
    private String attach;

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



}
