package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 住院患者病案首页信息
 *
 * @author ：Boven
 * @Description ：住院患者病案首页信息
 * @create ： 2018-01-15 15:43
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientCaseRequest implements Serializable{
    private static final long serialVersionUID = -6487448971796154850L;
    /**
    *患者主索引
    */
    @XmlElement(name="PATIENTID",required = true)
    private String patientId;
    /**
     *患者姓名
     */
    @XmlElement(name="PATIENTNAME",required = true)
    private String patientName;
    /**
     *住院信息ID
     */
    @XmlElement(name="INPATIENTID",required = true)
    private String inpatientId;
    /**
     *住院次数
     */
    @XmlElement(name="IPTIMES",required = true)
    private String ipTimes;

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
