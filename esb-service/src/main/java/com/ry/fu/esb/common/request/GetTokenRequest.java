package com.ry.fu.esb.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GETTOKENREQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class GetTokenRequest extends Request{

    private static final long serialVersionUID = -8821468614464700759L;

    @XmlElement(required = true, name="SYSTEMCODE")
    private String systemCode;

    @XmlElement(required = true, name="SYSTEMPASSWORD")
    private String systemPassword;

}
