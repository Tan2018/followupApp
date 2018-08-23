package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
* @Author:Boven
* @Description:科室号源响应实体
* @Date: Created in 11:06 2018/2/28
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptRegInfoResponseRecord implements Serializable {

    private static final long serialVersionUID = -1100228606096596538L;

    @XmlElement(required = true,name = "DOCTORID")
    private String doctorId;

    @XmlElement(required = true,name = "DOCTORNAME")
    private String doctorName;

    @XmlElement(required = true,name = "DEPTID")
    private String deptId;

    @XmlElement(required = true,name = "DEPTNAME")
    private String deptName;

    @XmlElement(required = true,name = "TITLE")
    private String title;

    @XmlElement(name = "DOCTORSPELLCODE")
    private String doctorSpellCode;

    @XmlElement(name = "GENDER")
    private String gender;

    @XmlElement(name = "DESC")
    private String desc;

    @XmlElement(name = "IMG")
    private String img;

    @XmlElement(name = "TIMEREGINFOLIST")
    private List<TimeRegInfoList> timeRegInfoList;
}
