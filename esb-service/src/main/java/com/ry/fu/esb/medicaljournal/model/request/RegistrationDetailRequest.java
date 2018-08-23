package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 挂号详情
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-04-11 17:45
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class RegistrationDetailRequest implements Serializable{
    private static final long serialVersionUID = 1667515670124714789L;
    /**
    *预约来源 1-健康之路 2-EMEM 3-电话（114）7-农行网点 8-医享网微信 9-医享网支付宝 13-翼健康 11-平安挂号 40-医程通

     */
    @XmlElement(name = "ORDERTYPE")
    private String orderType;
    /**
    * 外部预约系统的订单号
    */
    @XmlElement(name = "ORDERID")
    private String orderId;


}
