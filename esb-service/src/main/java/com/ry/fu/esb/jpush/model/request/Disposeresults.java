package com.ry.fu.esb.jpush.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class Disposeresults {

    /**
     * 项目id
     */
    @XmlElement(required = true, name = "ITEMID")
    private Long itemId;
    /**
     * 项目编码
     */
    @XmlElement(required = true, name = "ITEMCODE")
    private String itemCode;
    /**
     * 项目名称
     */
    @XmlElement(required = true, name = "ITEMNAME")
    private String itemName;
    /**
     * 处理时长(分钟)
     */
    @XmlElement(required = true, name = "DISPOSEMINUTES")
    private Long disposeMinutes;
}
