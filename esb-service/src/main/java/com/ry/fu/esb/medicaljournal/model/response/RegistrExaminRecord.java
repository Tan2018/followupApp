package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-23 15:38
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data

@EqualsAndHashCode(callSuper = false)
public class RegistrExaminRecord implements Serializable {

    @XmlElement(name="REPORT_ID")
    private String  reportId;
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name="DATE")
    private String  date;
    @XmlElement(name="COST")
    private String  cost;
}
