package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/24 18:09
 **/

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class DoctorBookAppSetRequest {

    private static final long serialVersionUID = 1388985001222562595L;

    @XmlElement(name = "DEPTID")
    private String deptId;
}
