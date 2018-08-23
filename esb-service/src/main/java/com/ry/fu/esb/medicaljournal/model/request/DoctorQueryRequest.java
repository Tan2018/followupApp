package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/11 15:28
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class DoctorQueryRequest implements Serializable{

    private static final long serialVersionUID = -8175449809355926897L;

    /**
     * 科室id
     */
    @XmlElement(name = "DEPTID")
    private String deptId ;

    /**
     * 医生id
     */
    @XmlElement(name = "DOCTORID")
    private String doctorId ;
}
