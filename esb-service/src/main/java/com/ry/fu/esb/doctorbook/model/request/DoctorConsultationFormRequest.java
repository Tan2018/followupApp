package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/3 14:38
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class DoctorConsultationFormRequest {

    /**
     * 会诊id
     */
    @XmlElement(required = false, name = "CONSULTATION_ID")
    private String consultationId;

    /**
     * 会诊类型
     */
    @XmlElement(required = false, name = "CONSULTATION_TYPE")
    private String consultationType;

    /**
     * 会诊级别
     */
    @XmlElement(required = false, name = "CONSULTATION_LEVEL")
    private String consultationLevel;

    /**
     * 申请科室ID
     */
    @XmlElement(required = false, name = "APPLY_DEPARTMENT_ID")
    private String applyDepartmentId;

    /**
     * 会诊科室ID
     */
    @XmlElement(required = false, name = "CONSULTATION_DEPARTMENT_ID")
    private String consultationDepartmentId;

    /**
     * 会诊状态
     */
    @XmlElement(required = false, name = "CURRENT_NODE")
    private String currentNode;

    /**
     * 会诊医生ID
     */
    @XmlElement(required = false, name = "CONS_DOCTOR_ID")
    private String consDoctorId;

    /**
     * 申请会诊医生ID
     */
    @XmlElement(required = false, name = "APPLY_DOCTOR_ID")
    private String applyDoctorId;

    /**
     * 申请审核医生ID
     */
    @XmlElement(required = false, name = "CHECK_DOCTOR_ID")
    private String checkDoctorId;

    /**
     * 排序模式
     */
    @XmlElement(required = false, name = "ORDERMODE")
    private String orderMode;
}
