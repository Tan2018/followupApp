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
* @Author:Boven
* @Description:主索引查询
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PrimaryIndexQueryPatientListResponse implements Serializable {


    private static final long serialVersionUID = -5383404588047860660L;
    @XmlElement(name="RECOUNT")
    private String reCount;
    private String patientCount;
    private String rePageSize;
    @XmlElement(name="RECORD")
    private List<PrimaryIndexQueryPatientRecord> patientRecord;

}
