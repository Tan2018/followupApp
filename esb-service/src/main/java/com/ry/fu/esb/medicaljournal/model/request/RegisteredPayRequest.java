package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/30 10:21
 * @description 挂号支付Request，对应ESB的挂号支付的接口
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisteredPayRequest {

    @XmlElement(name = "ORDERTYPE")
    private String orderType;

    @XmlElement(name = "ORDERID")
    private String orderId;

    @XmlElement(name = "PAYNUM")
    private String payNum;

    @XmlElement(name = "PAYAMOUT")
    private String payMout;

    @XmlElement(name = "PAYTIME")
    private String payTime;

    @XmlElement(name = "PAYDESC")
    private String payDesc;

}
