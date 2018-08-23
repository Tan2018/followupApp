package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 号源锁定
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class RegisterSourceLockedRequest implements Serializable {
    private static final long serialVersionUID = -3804978379162543699L;

    @XmlElement(name = "LOCKID")
    private String lockId;

    @XmlElement(name = "DEPTID")
    private String deptId;

    @XmlElement(name = "DOCTORID")
    private String doctorId;

    @XmlElement(name = "REGDATE")
    private String regDate;

    @XmlElement(name = "TIMEREGINFO")
    private TimeRegInfo timeRegInfo;

    @XmlElement(name = "STARTTIME")
    private String startTime;

    @XmlElement(name = "ENDTIME")
    private String endTime;

    @XmlElement(name = "ORDERTYPE")
    private String orderType;
}
