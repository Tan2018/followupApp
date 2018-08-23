package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 取消挂号请求(普遍情况)
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class CancelRegisterRequest implements Serializable {
    private static final long serialVersionUID = -3804978379162543699L;

    @XmlElement(name = "ORDERTYPE")
    private String orderType;

    @XmlElement(name = "ORDERID")
    private String orderId;

    @XmlElement(name = "CANCELTIME")
    private String cancelTime;

    @XmlElement(name = "CANCELREASON")
    private String cancelReason;
}
