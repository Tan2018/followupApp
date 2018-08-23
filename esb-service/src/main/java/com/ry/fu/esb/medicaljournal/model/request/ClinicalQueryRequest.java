package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author ：Boven
 * @Description ：门诊查询
 * @create ： 2018-03-23 15:29
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class ClinicalQueryRequest implements Serializable {


    private static final long serialVersionUID = -2565317586051336457L;
    /**
    *周标志：
     1 星期一 2 星期二3 星期三4 星期四5 星期五6 星期六7 星期日
     */
    @XmlElement(name = "WEEK")
    private String week;
    /**
    *门诊类型：
     1 教授门诊2 专科门诊3 特需门诊
     */
    @XmlElement(name = "TYPE")
    private String type;

}
