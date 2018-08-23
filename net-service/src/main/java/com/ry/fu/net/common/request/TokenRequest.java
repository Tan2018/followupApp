package com.ry.fu.net.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/1/8 14:47
 * @description description
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "GETTOKENREQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class TokenRequest implements Serializable {

    @XmlElement(name = "REQUESTID")
    private String requestId;

    @XmlElement(name = "REQUESTIP")
    private String requestIp;

    @XmlElement(name = "SYSTEMCODE")
    private String systemCode;

    @XmlElement(name = "SYSTEMPASSWORD")
    private String systemPassword;

}
