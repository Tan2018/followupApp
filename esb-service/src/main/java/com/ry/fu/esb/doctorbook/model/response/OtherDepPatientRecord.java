package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @Author:thankbin
 * @Description:其它科室交接班
 * @Date: Created in 20:01 2018/5/17
 */

@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class OtherDepPatientRecord implements Serializable{
    private static final long serialVersionUID = -1012954130366147984L;


    @XmlElement(name = "ID")
    private Long id;

    /*
    * 交班医生id
    * */
    @XmlElement(name = "TAKEDOCTOR")
    private Long takeDoctor;

    /*
    * 患者id
    * */
    @XmlElement(name = "PATIENTID")
    private Long patientId;

    @XmlElement(name = "SHIFTDOCTOR")
    private Long shiftDoctor;

    @XmlElement(name = "BEDNO")
    private Long bedNo;

    @XmlElement(name = "HOSPITALDAY")
    private String hospitalDay;

    @XmlElement(name = "PAGE")
    private Integer pAge;

    @XmlElement(name = "PSEX")
    private Integer pSex;

    @XmlElement(name = "STRDIAGNOSIS")
    private Integer strDiagnosis;

    @XmlElement(name = "STSTATE")
    private Integer stState;

    @XmlElement(name = "SHIFTTIME")
    private String shiftTime;

    @XmlElement(name = "TAKETIME")
    private String takeTime;

    @XmlElement(name = "PATIENTNAME")
    private String patientName;

    @XmlElement(name = "ORGID")
    private Integer orgId;
}
