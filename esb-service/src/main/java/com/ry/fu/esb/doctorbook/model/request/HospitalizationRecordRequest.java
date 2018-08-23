package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 患者入院记录信息
 *
 * @author ：Boven
 * @Description ：患者入院记录信息
 * @create ： 2018-01-15 16:07
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class HospitalizationRecordRequest implements Serializable{

    private static final long serialVersionUID = 7509422698490135893L;
    /**
    *住院患者ID
    */
    @XmlElement(name="PATIENTID",required = false)
    private String patientId;
    /**
    *住院号
    */
    @XmlElement(name="IPSEQNOTEXT",required = false)
    private String ipseqNoText;
    /**
    *住院次数
    */
    @XmlElement(name="IPTIMES",required = false)
    private String ipTimes;
}
