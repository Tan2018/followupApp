package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-05-16 17:06
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientQueryRequest implements Serializable {

    private static final long serialVersionUID = -352145038071976451L;
    //键入值
    private String queryValue;
    /**
     *诊疗卡号
     */
    @XmlElement(name="PATIENTCARDNO",required = true)
    private String patientCardNo;
    /**
     *患者姓名
     */
    @XmlElement(name="PATIENTNAME",required = false)
    private String patientName;
    /**
     *患者ID
     */
    @XmlElement(name="PATIENTID",required = true)
    private String patientId;
    /**
     *住院号
     */
    @XmlElement(name="IPSEQNOTEXT",required = false)
    private String ipSeqNoText;
    /**
     *住院次数
     */
    @XmlElement(name="IPTIMES",required = true)
    private String ipTimes;
    /**
     *患者来源： 1门诊；2住院或空
     */
    @XmlElement(name="PATIENTSOURCEFLAG",required = false)
    private String patientFlag;
}
