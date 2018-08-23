package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "ORDERDETAILINFO")
@Data
public class OrderDetailInfo implements Serializable {


    private static final long serialVersionUID = -7062442753559333655L;

    /**
     *缴费细目流水号，要求唯一
     */
    //@XmlElement(name = "DETAILID")
    private String detailId;




}
