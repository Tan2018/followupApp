package com.ry.fu.esb.instantmessaging.model.request;

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
 * @description 发送普通消息
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class SendMsg implements Serializable{
    /**
     * 发送者accid
     */
    @XmlElement(name = "FROM")
    private String from;
    /**
     * 0：点对点个人消息，1：群消息（高级群），其他返回414
     */
    @XmlElement(name = "OPE")
    private int ope;
    /**
     * ope==0是表示accid即用户id，ope==1表示tid即群id
     */
    @XmlElement(name = "TO")
    private String to;
    /**
     *   0 表示文本消息,
         1 表示图片，
         2 表示语音，
         3 表示视频，
         4 表示地理位置信息，
         6 表示文件，
         100 自定义消息类型
     */
    @XmlElement(name = "TYPE")
    private int type;
    /**
     * body消息体，为一个json字符串，示例：{"msg":"hello"}
     */
    @XmlElement(name = "BODY")
    private String body;
}
