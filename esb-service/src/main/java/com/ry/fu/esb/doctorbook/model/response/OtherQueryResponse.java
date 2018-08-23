package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *@Author: Boven
 *@Description: 其他科室查询
 *@Date:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class OtherQueryResponse implements Serializable{


    private static final long serialVersionUID = -2270306210130270229L;
    @XmlElement(name="RECORD")
    private ArrayList <OtherqueryRecord> HospitalPatientsResponseRecord;
}
