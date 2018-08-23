package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/31 13:54
 * @description
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class DoctorInfomationRequest implements Serializable {
    private static final long serialVersionUID = 1919385570762021562L;
    @XmlElement(name = "DEPTID")
    private Integer deptId;

    @XmlElement(name = "DOCTORID")
    private Integer doctorId;

}
