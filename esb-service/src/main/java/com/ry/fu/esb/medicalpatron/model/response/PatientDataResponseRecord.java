package com.ry.fu.esb.medicalpatron.model.response;

import com.ry.fu.esb.common.adapter.DateAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * 根据条件查找患者信息响应实体详情类
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/17 9:53
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@XmlAccessorType(XmlAccessType.NONE)
public class PatientDataResponseRecord implements Serializable{
    private static final long serialVersionUID = -3888639398268183163L;

    /**
     * 诊疗卡号
     */
    @XmlElement(name = "PATIENTCARDNO")
    private String patientCardNo;

    /**
     * 患者ID
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;

    /**
     * 患者姓名
     */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;

    /**
     * 手机号码
     */
    @XmlElement(name = "PHONE")
    private String phone;

    /**
     * 出生日期
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "BIRTHDATE")
    private String birthDate;

    /**
     * 性别 1-男,2-女,3-未知
     */
    @XmlElement(name = "GENDER")
    private String gender;

    /**
     * 身份证号
     */
    @XmlElement(name = "IDCARDNO")
    private String idCardNo;

    /**
     * 年龄
     */
    private String age;
}
