package com.ry.fu.net.common.response;

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
 * @Date 2018/1/8 15:57
 * @description description
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "GETTOKENRESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class TokenResponse implements Serializable {

    @XmlElement(name = "MSGCODE")
    private String msgCode;

    @XmlElement(name = "REQUESTID")
    private String requestId;

    @XmlElement(name = "ACCESSTOKEN")
    private String accessToken;

    @XmlElement(name = "MSGDESC")
    private String msgDesc;
}
