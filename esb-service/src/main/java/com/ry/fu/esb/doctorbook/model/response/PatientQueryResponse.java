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
 * @author ：Boven
 * @Description ：
 * @create ： 2018-05-16 17:02
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientQueryResponse implements Serializable{

    private Integer patientCount;
    private static final long serialVersionUID = 4711394253021201630L;
    @XmlElement(name="RECORD")
    private ArrayList<PatientQueryRecord> patientQueryRecord;
}
