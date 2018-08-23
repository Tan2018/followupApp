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
 * @Description ：住院患者详情
 * @create ： 2018-03-12 10:40
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class InPatientRequest implements Serializable{

    private static final long serialVersionUID = -3658081603059810804L;

    /**
     *患者ID
     */
    @XmlElement(name="PATIENTID",required = true)
    private String patientId;
    /**
     *患者姓名
     */
    @XmlElement(name="PATIENTNAME",required = false)
    private String patientName;

    /**
     *住院患者信息ID
     */
    @XmlElement(name="INPATIENTID",required = true)
    private String inpatientId;
    /**
     *诊疗卡号
     */
    @XmlElement(name="DEPARTMENTID",required = true)
    private String departmentId;

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
