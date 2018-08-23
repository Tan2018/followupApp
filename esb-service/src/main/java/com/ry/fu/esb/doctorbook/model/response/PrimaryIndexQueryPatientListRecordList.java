package com.ry.fu.esb.doctorbook.model.response;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
* @Author:Boven
* @Description:主索引查询
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PrimaryIndexQueryPatientListRecordList implements Serializable {


    private static final long serialVersionUID = -719020420609013854L;
    @XmlElement(name="PATIENT")
    private List<PrimaryIndexQueryPatientListRecordPatient> patientsGroup;
}
