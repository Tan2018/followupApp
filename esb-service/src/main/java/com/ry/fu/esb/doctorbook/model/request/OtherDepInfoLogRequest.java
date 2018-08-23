package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class OtherDepInfoLogRequest implements Serializable {

    private static final long serialVersionUID = -6442868183727158532L;

    @XmlElement(name = "PATIENTID")
    private String patientId;

    @XmlElement(name = "SHIFTDOCTOR")
    private String shiftDoctor;

    @XmlElement(name = "ORGID")
    private String orgId;

    @XmlElement(name = "GROUPID")
    private String groupId;

    @XmlElement(name = "TYPE")
    private Integer type;

    @XmlElement(name = "PAGESIZE")
    private Integer pageSize;

    @XmlElement(name = "PAGESUM")
    private Integer pageSum;
}
