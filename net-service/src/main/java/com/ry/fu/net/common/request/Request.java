package com.ry.fu.net.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class Request implements Serializable {

    private static final long serialVersionUID = 526295864811154739L;

    @XmlElement(required = false,name = "REQUESTID")
    private String requestId;

    @XmlElement(required = false, name = "REQUESTIP")
    private String requestIp;

}
