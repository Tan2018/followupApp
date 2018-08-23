package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/3 15:10
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DeprecListRecord {

    /**
     * 会诊科室id
     */
    @XmlElement(required = true, name = "CONS_DEPARTMENT_ID")
    private String consDepartmentId;

    /**
     * 会诊科室名称
     */
    @XmlElement(required = true, name = "CONS_DEPARTMENT_NAME")
    private String consDepartmentName;

    /**
     * 会诊医师id
     */
    @XmlElement(required = true, name = "CONS_DOCTOR_ID")
    private String consDoctorId;

    /**
     * 会诊医师姓名
     */
    @XmlElement(required = true, name = "CONS_DOCTOR_NAME")
    private String consDoctorName;

    /**
     * 授权人id
     */
    @XmlElement(required = true, name = "AUTHORIZATION_ID")
    private String authorizationId;

    /**
     * 授权人姓名
     */
    @XmlElement(required = true, name = "AUTHORIZATION_NAME")
    private String authorizationName;

    /**
     * 授权时间
     */
    @XmlElement(required = true, name = "AUTHORIZATION_TIME")
    private String authorizationTime;

    /**
     * 确认状态
     */
    @XmlElement(required = true, name = "CONFIRM_STATUS")
    private String confirmStatus;

    /**
     * 确认时间
     */
    @XmlElement(required = true, name = "CONFIRM_TIME")
    private String confirmTime;

    /**
     * 操作类型：1新增；2修改；3删除
     */
    @XmlElement(required = true, name = "OPERATERTYPE")
    private String operaterType;

    /**
     * 会诊医生电话
     */
    private String consultationDoctorPhone;
}
