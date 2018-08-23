package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/31 13:48
 * @description
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class DoctorInfoResponse implements Serializable {
    private static final long serialVersionUID = 280516694574646153L;
    @XmlElement(name="RECORD")
    private List<DoctorInfoResponseRecord> list;
}
