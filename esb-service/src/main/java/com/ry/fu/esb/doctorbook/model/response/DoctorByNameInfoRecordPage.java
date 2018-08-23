package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.response.BaseModel;
import com.ry.fu.esb.medicaljournal.model.response.DoctorByNameInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorByNameInfoRecordPage extends BaseModel {


    @XmlElement(name="RECORD")
    private List<DoctorByNameInfo> record;

    @XmlElement(name="TOTAL")
    private Integer total;
}
