package com.ry.fu.esb.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PUSHREQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class PushRequest implements Serializable {

    private static final long serialVersionUID = -2443929086193620882L;

    @XmlElement(required = false,name = "ACCESSTOKEN")
    private String accessToken;

    @XmlElement(name = "REQUESTDATA")
    private String requestData;

}
