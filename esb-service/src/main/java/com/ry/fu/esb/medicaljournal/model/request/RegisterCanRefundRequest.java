package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/4/9 19:19
 * @description 6.1.26	6.1.25.1	申请挂号退费，只判断是否允许退费
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class RegisterCanRefundRequest implements Serializable {

    @XmlElement(name = "ORDERTYPE")
    private String orderType;

    @XmlElement(name = "ORDERID")
    private String orderId;

    @XmlElement(name = "RETURNAMOUT")
    private String returnAmout;

}
