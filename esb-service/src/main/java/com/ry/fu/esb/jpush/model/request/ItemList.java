package com.ry.fu.esb.jpush.model.request;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemList implements Serializable {
    /**
     * 项目ID
     */
    @XmlElement(required = true, name = "ITEMID")
    private String itemId;
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
     * 接收危急值时间
     */
    @XmlElement(required = true, name = "RECEIVEDATE")
    private String receiveDate;
}
