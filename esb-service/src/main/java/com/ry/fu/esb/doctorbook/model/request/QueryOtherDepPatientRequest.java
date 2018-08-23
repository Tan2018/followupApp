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
public class QueryOtherDepPatientRequest implements Serializable{
    private static final long serialVersionUID = -3794268902330517956L;

    @XmlElement(name = "TAKEDOCTOR")
    private String takeDoctor;

    @XmlElement(name = "STSTATE")
    private String stState;

    @XmlElement(name = "PAGESIZE")
    private Integer pageSize;

    @XmlElement(name = "PAGESUM")
    private Integer pageSum;

    @XmlElement(name = "DEPID")
    private Integer depId;

    /**
     * 1-表示一级科室
     */
    private Integer deptLevel;
}
