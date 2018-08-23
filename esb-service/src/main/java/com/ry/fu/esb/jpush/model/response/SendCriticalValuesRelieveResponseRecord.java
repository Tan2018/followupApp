package com.ry.fu.esb.jpush.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class SendCriticalValuesRelieveResponseRecord implements Serializable {

    /**
     * 处理结果代码：0-成功，1失败
     */
    @XmlElement(name = "RESULTCODE")
    private Long resultCode;
    /**
     * 处理结果描述
     */
    @XmlElement(name = "RESULTDESC")
    private String resultDesc;
}
