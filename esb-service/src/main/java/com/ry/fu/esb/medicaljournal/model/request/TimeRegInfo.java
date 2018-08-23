package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 号源锁定<TIMEREGINFO>
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class TimeRegInfo implements Serializable {
    private static final long serialVersionUID = -3804978379162543699L;

    @XmlElement(name = "TIMEID")
    private String timeId;

    @XmlElement(name = "REGFEE")
    private String regFee;

    @XmlElement(name = "TREATFEE")
    private String treatFee;
}
