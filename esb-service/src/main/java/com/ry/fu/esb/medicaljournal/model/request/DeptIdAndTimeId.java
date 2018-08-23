package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * 号源锁定<TIMEREGINFO>
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class DeptIdAndTimeId implements Serializable {
    private static final long serialVersionUID = -3804978379162543699L;

    @XmlElement(name = "DEPTID")
    private String deptId;

    @XmlElement(name = "TIMEID")
    private List<String> timeId;
}
