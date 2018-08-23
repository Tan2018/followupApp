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
 * @date 2018/4/19 15:23
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@XmlRootElement(name = "RESPONSE")
@XmlAccessorType(XmlAccessType.FIELD)
public class DoctorInfoByPageResponse implements Serializable {
    private static final long serialVersionUID = 1076930632368210164L;

    @XmlElement(name = "RECORD")
    private List<DoctorInfoByPageResponseRecord> records;
}
