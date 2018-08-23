package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：Boven
 * @Description ：随访系统登录响应实体
 * @create ： 2018-02-05 17:10
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemLoginResponseRecode  implements Serializable{
    private static final long serialVersionUID = -6633723272389038221L;
    /**
     *平台人员编码
     */
    @XmlElement(name = "DOCTORCODE")
    private String doctorCode;
    /**
     *平台人员Id
     */
    @XmlElement(name = "DOCTORID")
    private String doctorId;
    /**
     *平台人员姓名
     */
    @XmlElement(name = "DOCTORNAME")
    private String doctorName;
    /**
     *性别（1男，2女，3未知）
     */
    @XmlElement(name = "GENDER")
    private String gender;
    /**
     *手机号码
     */
    @XmlElement(name = "MOBILE")
    private String mobile;
    /**
     *联系电话
     */
    @XmlElement(name = "TELEPHONE")
    private String telePhone;
    /**
     *现聘职称
     */
    @XmlElement(name = "JOB_TITLE")
    private String jobTitle;
    /**
     *附属人员Code
     */
    @XmlElement(name = "SUBDOCTORCODE")
    private String subDoctorCode;
    /**
     *附属人员name
     */
    @XmlElement(name = "SUBDOCTORNAME")
    private String subDoctorName;
    /**
     *科室ID
     */
    @XmlElement(name = "ORG_ID")
    private String orgId;
    /**
     *UUID 网易云信token
     */
    private String netEaseToken;
    /**
     *UUID 网易云信Id
     */
    private String netEaseId;

    /**
     * 首次登陆时间
     */
    private Date firstLoginTime;
    /**
     * 最近一次登录时间
     */
    private Date lastLoginTime;
    /**
     * 最近一次登录平台
     */
    private String appId;
    /**
     * 医生状态 ，0.正式医生、1.附属账号、2未知、3其他
     */
    private String doctorFlag;
    /**
     * 统计权限 0-普通统计、1-高级统计
     */
    private String countType;
    /**
     * 医务科 0-普通科室，1-医务科
     */
    private String medicalDepartment;
    /**
     *
     */
    @XmlElementWrapper(name="ORGLIST")
    @XmlElement(name="ORG")
    private List<SystemLoginResponseRecodeOrgList> systemLoginResponseRecodeOrgList;


}
