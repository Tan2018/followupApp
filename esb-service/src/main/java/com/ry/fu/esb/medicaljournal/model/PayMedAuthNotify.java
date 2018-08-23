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
 * @description 医保授权通知
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_MED_AUTH_NOTIFY")
public class PayMedAuthNotify extends BaseModel {

    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 授权请求id
     */
    @Column(name = "REQ_ID")
    private Long reqId;

    /**
     * 用户手机号码
     */
    @Column(name = "USER_PHONE")
    private String userPhone;

    /**
     * 用户名称
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 渗透参数
     */
    @Column(name = "ATTACH")
    private String attach;

    /**
     * 授权码
     */
    @Column(name = "AUTH_CODE")
    private String authCode;

    /**
     * 授权状态
     */
    @Column(name = "AUTU_STATUS")
    private String authStatus;

    /**
     * 授权回调结果中的时间戳
     */
    @Column(name = "TIME_STAMP")
    private String timestamp;

    /**
     * 商户用户id
     */
    @Column(name = "MCH_USER_ID")
    private String mchUserId;

    /**
     * 商户id
     */
    @Column(name = "MCH_ID")
    private String mchId;

    /**
     * 结果信息
     */
    @Column(name = "RESULT_MESSAGE")
    private String resultMessage;

    /**
     * 结果码
     */
    @Column(name = "RESULT_CODE")
    private String resultCode;

    /**
     * 响应数据
     */
    @Column(name = "NOTIFY_DATA")
    private String notifyData;

}
