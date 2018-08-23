package com.ry.fu.esb.instantmessaging.model.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 注册成功返回的网易云通信信息
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
public class NetEaseCloudLetterResponse implements Serializable{
    /**
     * 网易云通信ID
     */
    @XmlElement(name="RECORD")
    private String accid;
    /**
     * 网易云通信token
     */
    @XmlElement(name="RECORD")
    private String token;
    /**
     * 网易云港通信ID昵称
     */
    @XmlElement(name="RECORD")
    private String name;
}
