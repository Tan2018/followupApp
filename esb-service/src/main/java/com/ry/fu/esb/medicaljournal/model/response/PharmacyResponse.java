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
 * @author ：Joker
 * @Description ：取药详情
 * @create ： 2018-05-8 11:03
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PharmacyResponse implements Serializable{

    private static final long serialVersionUID = -6673444569448445014L;

    @XmlElement(name="RECORD")
    private List<PharmacyRecord> record ;
}
