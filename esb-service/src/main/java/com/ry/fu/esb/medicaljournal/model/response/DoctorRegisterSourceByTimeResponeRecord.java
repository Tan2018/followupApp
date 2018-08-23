package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * 医生号源分时
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-02-28 16:16
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorRegisterSourceByTimeResponeRecord implements Serializable{
    private static final long serialVersionUID = -3913435084777013760L;

    @XmlElement(name = "DEPTID")
    private String deptId;

    @XmlElement(name = "TIMEID")
    private String timeId;

    @XmlElement(name = "TIMEREGINFO")
    private List<DoctorRegisterSourceByTimeResponeRecordTimeRegInfo> DoctorRegisterSourceByTimeResponeRecordTimeRegInfo;

}
