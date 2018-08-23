package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * 科室切换科室列表
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptSwitchRecord implements Serializable{

    private static final long serialVersionUID = -8111235599066741055L;

    @XmlElement(name="DEPTID")
    private String deptId;

    @XmlElement(name="DEPTNAME")
    private String deptName ;

    @XmlElement(name="DISTRICTDEPTID")
    private String districtDeptId ;

    @XmlElement(name="DISTRICTDEPTNAME")
    private String districtDeptName ;

}
