package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class OtherDepRequest implements Serializable {
    private static final long serialVersionUID = 554039763480746248L;

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

    /*
   * 接班医生id
   * */
    @XmlElement(name = "SHIFTDOCTOR")
    private Long shiftDoctor;

    /*
   * 床号
   * */
    @XmlElement(name = "BEDNO")
    private Long bedNo;

    /*
   * 住院天数
   * */
    @XmlElement(name = "HOSPITALDAY")
    private String hospitalDay;

    /*
   * 患者年龄
   * */
    @XmlElement(name = "PAGE")
    private String pAge;

    /*
   * 患者性别
   * */
    @XmlElement(name = "PSEX")
    private String pSex;

    /*
   * 入院诊断
   * */
    @XmlElement(name = "STRDIAGNOSIS")
    private String strDiagnosis;

    /*
   * 患者名称
   * */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;

    /**
     * 患者名称
     */
    @XmlElement(name = "ORGID")
    private String orgId;
}
