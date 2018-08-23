package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/27 15:32
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class MedicalInfoResponseRecord {

    /**
     *员工编码(医院工号)
     */
    @XmlElement(name="HIP_STAFF_CODE")
    private String hipStaffCode;

    /**
     *身份证
     */
    @XmlElement(name="IDENTITYCARD")
    private String identitycCard;

    /**
     *登录名
     */
    @XmlElement(name="LOGINNAME")
    private String loginName;

    /**
     *出生日期
     */
    @XmlElement(name="BIRTHDAY")
    private String birthday;

    /**
     *所属科室ID
     */
    @XmlElement(name="DEPARTMENTID")
    private String departmentId;

    /**
     *所属科室编码
     */
    @XmlElement(name="DEPARTMENTNO")
    private String departmentNo;

    /**
     *所属科室名称
     */
    @XmlElement(name="DEPARTMENTNAME")
    private String departmentName;

    /**
     *学历标志
     */
    @XmlElement(name="EDUCATEDFLAG")
    private String educatedFlag;

    /**
     *员工类别标志
     */
    @XmlElement(name="EMPLOYEEASSORTFLAG")
    private String employeeAssortFlag;

    /**
     *员工ID
     */
    @XmlElement(name="EMPLOYEEID")
    private String employeeId;

    /**
     *员工名称
     */
    @XmlElement(name="EMPLOYEENAME")
    private String employeeName;

    /**
     *在职标志
     */
    @XmlElement(name="ONDUTYFLAG")
    private String ondutyFlag;

    /**
     *性别
     */
    @XmlElement(name="SEXFLAG")
    private String sexFlag;
}
