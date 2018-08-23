package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 护理三测
 *
 * @author ：Boven
 * @Description ：护理三测信息
 * @create ： 2018-01-15 15:53
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class NurseMeasureRequest implements Serializable {
    private static final long serialVersionUID = 4466383679228051980L;
   /**
   *住院号
   */
    @XmlElement(name="IPSEQNOTEXT",required = true)
    private String ipseqNoText;
    /**
    *住院次数
    */
    @XmlElement(name="IPTIMES",required = true)
    private String ipTimes;

}
