package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 曾挂号医生请求报文
 * @author walker
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisteredDoctorItemResponse implements Serializable{

    private static final long serialVersionUID = 1388985001222562595L;

    @XmlElement(name = "DOCTORID")
    private String doctorId;

    @XmlElement(name = "DOCTORNAME")
    private String doctorName;

    @XmlElement(name = "DEPTID")
    private String deptId;

    @XmlElement(name = "DEPTNAME")
    private String deptName;

    @XmlElement(name = "TITLE")
    private String title;

    @XmlElement(name = "IMG")
    private String img;

    @XmlElement(name = "COUNT")
    private String count = "0";

    @XmlElement(name = "REGDATE")
    private String regDate;

    @XmlElement(name = "REGISTRATIONTYPE")
    private String registrationType;

    @XmlElement(name = "TIMENAME")
    private String timeName;

    @XmlElement(name = "DESC")
    private String desc;

    @XmlElement(name = "PRICE")
    private String price;
}
