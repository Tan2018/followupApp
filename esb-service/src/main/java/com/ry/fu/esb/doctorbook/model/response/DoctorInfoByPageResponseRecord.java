package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/19 15:23
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@XmlAccessorType(XmlAccessType.FIELD)
public class DoctorInfoByPageResponseRecord implements Serializable {
    private static final long serialVersionUID = 3617131964635203783L;

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
     *平台科室名称
     */
    @XmlElement(required = true,name = "DEPTNAME")
    private String deptName;
}
