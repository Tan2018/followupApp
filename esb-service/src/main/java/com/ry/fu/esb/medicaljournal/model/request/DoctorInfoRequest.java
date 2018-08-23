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
public class DoctorInfoRequest implements Serializable {
    private static final long serialVersionUID = -3804978379162543699L;

    @XmlElement(name = "DEPTID")
    private Integer deptId;

    @XmlElement(name = "DOCTORID")
    private Integer doctorId;

    @XmlElement(name = "TYPE")
    private Integer type = 1;

}
