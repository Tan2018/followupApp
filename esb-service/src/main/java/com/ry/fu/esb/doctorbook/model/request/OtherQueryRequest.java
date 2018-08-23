package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:其他科室
* @Date: Created in 14:20 2018/4/2
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class    OtherQueryRequest implements Serializable {

    private static final long serialVersionUID = 3766985653268251902L;
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
     *患者来源： 1门诊；2住院或空
     */
    @XmlElement(name="PATIENTSOURCEFLAG",required = false)
    private String patientFlag;
    /**
     *住院号
     */
    @XmlElement(name="IPSEQNOTEXT",required = true)
    private String ipSeqNoText;
    /**
     *住院次数
     */
    @XmlElement(name="IPTIMES",required = true)
    private String ipTimes;

}
