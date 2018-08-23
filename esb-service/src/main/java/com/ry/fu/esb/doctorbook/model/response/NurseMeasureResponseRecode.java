package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：护理三测响应参数
 * @create ： 2018-01-15 16:51
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class NurseMeasureResponseRecode implements Serializable{

    private static final long serialVersionUID = -9056806688917806220L;
    /**
    *URL地址
    */
    @XmlElement(name="URL",required = true)
    private String url;
    /**
    *住院号
    */
    @XmlElement(name="IPSEQNOTEXT",required = true)
    private String ipSeqNoText;
    /**
    *住院次数
    */
    @XmlElement(name="IPTIMES",required = true)
    private String ipTimes;
}
