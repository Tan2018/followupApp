package com.ry.fu.net.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class ExmRequest implements Serializable{
    private static final long serialVersionUID = 1956078705986879472L;

    @XmlElement(name = "PATIENTID")
    private Integer patient_id;

    @XmlElement(name = "REQUESTEXECUTIVEDATETIME")
    private String fmt_req_exec_date;
}
