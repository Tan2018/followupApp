package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:科室号源响应实体
* @Date: Created in 11:06 2018/2/28
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class TimeRegInfoItem implements Serializable {

    private static final long serialVersionUID = -1100228606096596538L;

    @XmlElement(name = "TIMEID")
    private String timeId;

    @XmlElement(name = "TIMENAME")
    private String timeName;

    @XmlElement(name = "REGFEE")
    private String regFee;

    @XmlElement(name = "TREATFEE")
    private String treatFee;

    @XmlElement(required = true,name = "REGLEAVECOUNT")
    private String regLeaveCount;

    @XmlElement(name = "REGTYPE")
    private String regType;

    @XmlElement(name = "STATUSTYPE")
    private String statusType;
    @XmlElement(name = "ISTIMEREG")
    private String isTimeReg;
}
