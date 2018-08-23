package com.ry.fu.esb.medicaljournal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Walker
 * @version V1.0.0
 * @Date 2018/3/26 20:02
 * @description 患者表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PATIENT")
public class Patient extends BaseModel {

    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * ESB患者Id
     */
    @JsonProperty(value = "patientId")
    @Column(name = "ESB_PATIENT_ID")
    private Long esbPatientId;

    /**
     * 注册类型
     */
    @Column(name = "REGISTER_SOURCE")
    private String registerSource;

    /**
     * 姓名
     */
    @JsonProperty(value = "patientName")
    @Column(name = "NAME")
    private String name;

    /**
     * 性别
     */
    @JsonProperty(value = "userGender")
    @Column(name = "SEX")
    private String sex;

    /**
     * 密码
     */
    @Column(name = "PASSWD")
    private String passwd;

    /**
     * 身份证号
     */
    @JsonProperty(value = "userIdCard")
    @Column(name = "ID_CARD")
    private String idCard;

    /**
     * 是否绑定医保
     */
    @Column(name = "IS_MEDICARE")
    private String isMedicare;

    /**
     * 头像
     */
    @Column(name = "HEAD_IMG")
    private String headImg;

    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 诊疗卡号
     */
    @JsonProperty(value = "userJkk")
    @Column(name = "HEALTH_CARD_NO")
    private String healthCardNo;

    /**
     * 手机号
     */
    @JsonProperty(value = "userMobile")
    @Column(name = "PHONE")
    private String phone;

    /**
     * 状态
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "CREATE_USER")
    private String createUser;

    /**
     * 生日
     */
    @JsonProperty(value = "userBirthday")
    @Column(name = "BIRTHDAY")
    private Date birthday;

    /**
     * 医保授权码
     */
    @Column(name = "AUTH_CODE")
    private String authCode;

    /**
     * 医保状态 1、医保支付授权成功  2、未查询到医保卡信息,请携带本人医保卡到线下进行绑定
     */
    @Column(name = "AUTH_STATUS")
    private String authStatus;

    /**
     * 网易云信accid
     */
    @Transient
    private String neteaseId;

    /**
     * 网易云信Token
     */
    @Column(name = "NETEASE_TOKEN")
    private String neteaseToken;

    /**
     * 患者主索引
     */
    @Column(name = "PRIMARY_INDEX")
    private String primaryIndex;

    public String getNeteaseId() {
        return "p"+primaryIndex;
    }
}
