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
 * @date 2018/4/2 17:35
 * @description
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientInformationResponse implements Serializable {
    private static final long serialVersionUID = 1197944750575714674L;
    @XmlElement(name="RECORD")
    private List<PatientInformationResponseRecord> inpatientInfoDetailResponse;
}
