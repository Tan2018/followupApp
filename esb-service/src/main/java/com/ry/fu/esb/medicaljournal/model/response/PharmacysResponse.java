package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.medicaljournal.model.OrderDetail;
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
 * @create ： 2018-06-04 15:33
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PharmacysResponse implements Serializable{


    private static final long serialVersionUID = 2156698364163133400L;

    @XmlElement(name="RECORD")
    private List<PharmacysRecord> record ;
}
