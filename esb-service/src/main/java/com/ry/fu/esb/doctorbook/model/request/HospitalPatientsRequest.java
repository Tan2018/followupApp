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
* @Description:住院信息查询
*
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class HospitalPatientsRequest implements Serializable{


    private static final long serialVersionUID = 8106489752655239858L;

    private String queryValue;

    /**
     *姓名
     */
    @XmlElement(name="PATIENTNAME ")
    private String patientName;
    /**
     *住院号
     */
    @XmlElement(name="IPSEQNOTEXT")
    private String ipSeqNoText;
    /**
     *诊疗卡号
     *
     */
    @XmlElement(name="IPSEQNOTEXT")
    private String healthCardNo;

    /**
     *患者类型
     *
     */
    @XmlElement(name="PATIENTSOURCEFLAG")
    private String patientFlag;




}
