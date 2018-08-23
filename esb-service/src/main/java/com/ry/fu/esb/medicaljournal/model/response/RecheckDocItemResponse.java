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
public class RecheckDocItemResponse implements Serializable{

    private static final long serialVersionUID = -5656084825956656766L;

    @XmlElement(name = "DEPTID")
    private String deptId;

    @XmlElement(name = "DOCTORID")
    private String doctorId;

    @XmlElement(name = "DOCTORNAME")
    private String doctorName;

    @XmlElement(name = "IMG")
    private String img;

    @XmlElement(name = "COUNT")
    private String count;

    @XmlElement(name = "REGDATE")
    private String regDate;

    @XmlElement(name = "REGISTRATIONTYPE")
    private String registrationType;

    @XmlElement(name = "TECHPOST")
    private String techPost;

    @XmlElement(name = "REGISTRATIONTYPENAME")
    private String registrationTypeName;

    @XmlElement(name = "TIMENAME")
    private String timeName;

    @XmlElement(name = "DESC")
    private String desc;

    @XmlElement(name = "PRICE")
    private String price;

    /**
     * 职称编码
     */
    @XmlElement(name = "TECHPOSTNO")
    private String techPostNo;


}
