package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class HandoverStatisticsRequest {
    @XmlElement(name = "DATE")
    private String date;

    @XmlElement(name = "ORGID")
    private Integer orgId;

    @XmlElement(name = "PAGESIZE")
    private Integer pageSize;

    @XmlElement(name = "PAGESUM")
    private Integer pageSum;
}
