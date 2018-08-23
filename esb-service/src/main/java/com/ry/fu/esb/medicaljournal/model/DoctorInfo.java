package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/16 14:57
 * @description 医生信息表，主要是用来缓存医生的基本信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_DOCTOR")
public class DoctorInfo extends BaseModel {

    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 科室ID
     */
    @Column(name = "ORG_ID")
    private Long orgId;

    /**
     * 科室名称
     */
    @Column(name = "ORG_NAME")
    private String orgName;

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
     * 头像
     */
    @Column(name = "HEAD_IMG")
    private String headImg;

    /**
     * 擅长
     */
    @Column(name = "GOOD_AT")
    private String goodAt;

    /**
     * 职称名称
     */
    @Column(name = "PROFESS_NAME")
    private String processName;

    /**
     * 中文名
     */
    @Column(name = "CH_NAME")
    private String chName;

    /**
     * 用户登录名
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 状态
     */
    @Column(name = "STATUS")
    private String status;
    /**
     * 查看统计权限
     */
    @Column(name = "COUNT_TYPE")
    private String countType;

    @Transient
    private Integer count;

}
