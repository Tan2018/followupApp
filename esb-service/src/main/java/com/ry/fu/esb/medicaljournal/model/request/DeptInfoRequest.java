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
public class DeptInfoRequest implements Serializable{

    private static final long serialVersionUID = 1388985001222562595L;

    @XmlElement(name = "DEPTID")
    private String deptId;

    @XmlElement(name = "FLAG")
    private long flag;
}
