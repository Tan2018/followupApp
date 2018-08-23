package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/27 15:35
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class MedicalInfoRequest {

    /**
     *员工编码（工号）
     */
    @XmlElement(name="HIP_STAFF_CODES")
    private String hipStaffCodes;

    /**
     *员工ID
     */
    @XmlElement(name="EMPLOYEEID")
    private String employeeId;

    /**
     *科室ID
     */
    @XmlElement(name="DEPARTMENTID")
    private String departmentId;

    /**
     *科室编码
     */
    @XmlElement(name="DEPARTMENTNO")
    private String departmentNo;

    /**
     *科室编码
     */
    private String doctorId;
}
