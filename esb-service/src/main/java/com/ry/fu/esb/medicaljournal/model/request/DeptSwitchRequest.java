package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class DeptSwitchRequest implements Serializable{

    private static final long serialVersionUID = -5091535062812392363L;

    @XmlElement(name = "STARTDATE")
    private String startDate;

    @XmlElement(name = "ENDDATE")
    private String endDate;

    @XmlElement(name = "DOCTORID")
    private String doctorId;
}
