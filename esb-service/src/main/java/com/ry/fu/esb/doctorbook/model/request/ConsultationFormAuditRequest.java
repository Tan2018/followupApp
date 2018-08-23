package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/3 14:45
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class ConsultationFormAuditRequest {

    /**
     * 操作类型
     */
    @XmlElement(required = true, name = "OPERATE_TYPE")
    private String operateType;

    /**
     * 会诊ID
     */
    @XmlElement(required = true, name = "CONSULTATION_ID")
    private String consultationId;

    /**
     * 审核/会诊科室id
     */
    @XmlElement(required = true, name = "CONS_DEPARTMENT_ID")
    private String consDepartmentId;

    /**
     * 审核/会诊科室名称
     */
    @XmlElement(required = true, name = "CONS_DEPARTMENT_NAME")
    private String consDepartmentName;

    /**
     * 审核/会诊医师id
     */
    @XmlElement(required = true, name = "CONS_DOCTOR_ID")
    private String consDoctorId;

    /**
     * 审核/会诊医师姓名
     */
    @XmlElement(required = true, name = "CONS_DOCTOR_NAME")
    private String consDoctorName;

    /**
     * 意见
     */
    @XmlElement(required = false, name = "OPTIONS")
    private String options;

    /**
     * 审核时间
     */
    @XmlElement(required = true, name = "CONFIRM_TIME")
    private String confirmTime;

    /**
     * 审核结果
     */
    @XmlElement(required = true, name = "CONFIRM_STATUS")
    private String confirmStatus;
}
