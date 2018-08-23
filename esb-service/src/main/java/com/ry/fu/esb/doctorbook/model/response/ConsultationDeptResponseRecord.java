package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/27 9:29
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ConsultationDeptResponseRecord implements Serializable{
    private static final long serialVersionUID = -1100228606096596538L;
    /**
     *会诊科室id
     */
    @XmlElement(name = "DEPARTMENT_ID")
    private String deptrtmentId;

    /**
     *会诊科室编码
     */
    @XmlElement(name = "DEPARTMENT_CODE")
    private String departmentCode;

    /**
     *会诊科室名称
     */
    @XmlElement(name = "DEPARTMENT_NAME")
    private String departmentName;

    /**
     *主治医师ID
     */
    @XmlElement(name = "CHARGE_DOCTOR_ID")
    private String chargeDoctorId;

    /**
     *主治医师姓名
     */
    @XmlElement(name = "CHARGE_DOCTOR_NAME")
    private String chargeDoctorName;

    /**
     *各大医师ID
     */
    @XmlElement(name = "DOCTOR_ID")
    private String doctorId;

    /**
     *各大医师姓名
     */
    @XmlElement(name = "DOCTOR_NAME")
    private String doctorName;


}
