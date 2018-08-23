package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorItemResponse implements Serializable{
    private static final long serialVersionUID = 2756678993015237881L;

    @XmlElement(name = "DOCTORID")
    private Integer doctorId;

    @XmlElement(name = "DOCTORNAME")
    private String doctorName;

    @XmlElement(name = "DOCTORSPELLCODE")
    private String doctorSpellCode;

    @XmlElement(name = "TITLE")
    private String title;

    @XmlElement(name = "GENDER")
    private String gender;

    @XmlElement(name = "DESC")
    private String desc;

    @XmlElement(name = "IMG")
    private String img;
}
