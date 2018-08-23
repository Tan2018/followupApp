package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
*科室号源
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class DeptRegInfoResponse implements Serializable{


    private static final long serialVersionUID = -5500667854493927753L;
    @XmlElement(name="RECORD")
    private List<DeptRegInfoResponseRecord> deptRegInfoResponseRecode;

    @XmlElement(name="DEPTID")
    private String deptId;

    @XmlElement(name="DEPTNAME")
    private String deptName;
}
