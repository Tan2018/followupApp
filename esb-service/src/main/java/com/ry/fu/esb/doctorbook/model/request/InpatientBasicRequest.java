package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 患者基本信息
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-02-01 17:49
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientBasicRequest implements Serializable {
    private static final long serialVersionUID = -4386103972501931760L;
    /**
     *住院信息ID
     */
    @XmlElement(name="INPATIENTID",required = true)
    private String inpatientId;
    /**
     *住院次数
     */
    @XmlElement(name="IPTIMES",required = false)
    private String ipTimes;
    /**
     *患者主索引
     */
    @XmlElement(name="PATIENTID",required = true)
    private String patientId;
    /**
     *患者姓名
     */
    @XmlElement(name="PATIENTNAME",required = false)
    private String patientName;
    /**
     *住院号
     */
    @XmlElement(name="IPSEQNOTEXT",required = true)
    private String ipseqNoText;
    /**
     *住院科室ID(平台)
     */
    @XmlElement(name="DEPARTMENTID",required = true)
    private String departmentId;

}
