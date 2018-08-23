package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * 取号支付
 *
 * @author ：Seaton
 * @Description ：
 * @create ： 2018-03-31 14:04
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class PayDetailRequest implements Serializable {


    private static final long serialVersionUID = -5700788415571400211L;

    @XmlElement(name="DETAILID")
    private String detailId;

    /*@XmlElement(name="DETAILNAME")
    private String detailName;

    *//**
     * 费别
     *//*
    @XmlElement(name="DETAILFEE")
    private String detailFee;

    @XmlElement(name="DETAILCOUNT")
    private String detailCount;

    @XmlElement(name="DETAILUNIT")
    private String detailUnit;

    @XmlElement(name="DETAILAMOUT")
    private String detailAmout;*/
}
