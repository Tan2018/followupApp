package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-02-28 16:16
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorRegisterSourceResponeRecord implements Serializable {

    private static final long serialVersionUID = -6846592210074602434L;
    @XmlElement(name="REGINFO")
    private DoctorRegisterSourceResponeRecodeRegInfo doctorRegisterSourceResponeRecodeRegInfo;
}
