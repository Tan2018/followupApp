package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/16 14:57
 * @description 医生信息表，主要是用来缓存医生的基本信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_DOCTOR_LOGIN")
public class DoctorLoginInfo extends BaseModel {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 中文名
     */
    @Column(name = "DOCTOR_NAME")
    private String doctorName;

    /**
     * 用户登录名
     */
    @Column(name = "DOCTOR_CODE")
    private String doctorCode;
    /**
     * 医生ID
     */
    @Column(name = "DOCTOR_ID")
    private Long doctorId;
    /**
     * 性别
     */
    @Column(name = "SEX")
    private String sex;
    /**
     * 移动电话
     */
    @Column(name = "MOBILE")
    private String mobile;
    /**
     * 联系电话
     */
    @Column(name = "TELEPHONE")
    private String telePhone;
    /**
     * 职称名称
     */
    @Column(name = "PROFESS_NAME")
    private String processName;
    /**
     * 科室名称
     */
    @Column(name = "ORG_NAME")
    private String orgName;
    /**
     * 科室ID
     */
    @Column(name = "ORG_ID")
    private String orgId;
    /**
     * 附属账号人员Code
     */
    @Column(name = "SUB_DOCTOR_CODE")
    private String subDctorCode;
    /**
     * 附属账号人员姓名
     */
    @Column(name = "SUB_DOCTOR_NAME")
    private String subDoctorName;
    /**
     * 登录医生标记状态：0.正式医生、1.附属账号、2未知、3其他
     */
    @Column(name = "DOCTOR_FLAG")
    private String doctorFlag;
    /**
     * 首次登陆时间
     */
    @Column(name = "FIRST_LOGIN_TIME")
    private Date firstLoginTime;
    /**
     * 上次登陆时间
     */
    @Column(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;
    /**
     * 网易云信token
     */
    @Column(name = "NETEASE_TOKEN")
    private String neteaseToken;
    /**
     * 登陆平台
     */
    @Column(name = "APP_ID")
    private String appId;
    @Transient
    private Integer count;

}
