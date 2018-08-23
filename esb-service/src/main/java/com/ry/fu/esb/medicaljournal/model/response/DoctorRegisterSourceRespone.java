package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-02-28 16:16
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class DoctorRegisterSourceRespone implements Serializable {
    private static final long serialVersionUID = -2770079862546183292L;
    @XmlElement(name="RECORD")
    private List<DoctorRegisterSourceResponeRecodeRegInfo> doctorRegisterSourceResponeRecodeRegInfos;
}
