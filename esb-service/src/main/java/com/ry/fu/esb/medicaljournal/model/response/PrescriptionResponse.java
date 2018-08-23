package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-22 18:26
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PrescriptionResponse implements Serializable{

    private static final long serialVersionUID = -614704560364161348L;
    private ArrayList dateList;
    private ArrayList  rxList;
    @XmlElement(name="RECORD")
    private List<PrescriptionRecord> prescriptionRecord ;
}
