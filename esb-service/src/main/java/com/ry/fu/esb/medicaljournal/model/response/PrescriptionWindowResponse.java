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
 * @author ：Joker
 * @Description ：
 * @create ： 2018-05-21 11:40
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PrescriptionWindowResponse implements Serializable{


    private static final long serialVersionUID = 7684253870571508594L;

    @XmlElement(name="RECORD")
    private List<PrescriptionWindowRecord> record;
}
