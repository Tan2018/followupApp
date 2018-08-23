package com.ry.fu.esb.medicaljournal.model.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorSearchReq {

    @XmlElement(name = "CONTENT")
    private String content;

    @XmlElement(name = "PAGESIZE")
    private String pageSize;

    @XmlElement(name = "PAGENUM")
    private String pageNum;

    @XmlElement(name = "DOCTORTYPE")
    private String doctorType;

    private String doctorId;

}
