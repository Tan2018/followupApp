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
 * @Author:Tom
 * @Description:
 * @create: 2018/4/11 15:24
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class DoctorQueryResponse implements Serializable{


    private static final long serialVersionUID = -7442093588848799542L;

    @XmlElement(name="RECORD")
    private List<DoctorQueryRecord> doctorQueryRecord;
}

