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
public class DoctorRegisterSourceResponeRecodeRegInfo implements Serializable{

    private static final long serialVersionUID = 5481644395109282056L;
    /**
     *平台医生ID
     */
    @XmlElement(required = true,name = "DOCTORID")
    private String doctorId;
    /**
     *平台医生名称
     */
    @XmlElement(required = true,name = "DOCTORNAME")
    private String doctorName;
    /**
     *平台医生职称
     */
    @XmlElement(required = true,name = "DOCTORTITLE")
    private String doctorTitle;
    /**
     *平台科室ID
     */
    @XmlElement(required = true,name = "DEPTID")
    private String deptId;
    /**
     *平台科室名称
     */
    @XmlElement(required = true,name = "DEPTNAME")
    private String deptName;

    @XmlElement(name = "TIMEREGINFOLIST")
    private List<DoctorRegisterSourceResponeRecodeTimeRegInfoList> doctorRegisterSourceResponeRecodeTimeRegInfoList;
}
