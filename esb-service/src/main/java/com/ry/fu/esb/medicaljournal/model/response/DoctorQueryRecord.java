package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/11 15:15
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorQueryRecord implements Serializable{

    private static final long serialVersionUID = -8050294837801875149L;


    /**
     *科室id
     */
    @XmlElement(name = "DEPTID")
    private String deptId;

    /**
     *医生id
     */
    @XmlElement(name = "DOCTORID")
    private String doctorId;

    /**
     *医生名称
     */
    @XmlElement(name = "DOCTORNAME")
    private String doctorName;

    /**
     *医生类型标识
     */
    @XmlElement(name = "TECHPOSTNO")
    private String techPostNo;

    /**
     *医生类型
     */
    @XmlElement(name = "TITLE")
    private String title;

    /**
     *医生头像
     */
    @XmlElement(name = "IMG")
    private String img;
}
