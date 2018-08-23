package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * 取号支付
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-31 14:04
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class PayMentRequest implements Serializable {
    private static final long serialVersionUID = 4950412393444516034L;
    /**
    *预约来源
     1-健康之路 2-EMEM 3-电话（114）7-农行网点 8-医享网微信 9-医享网支付宝 11-平安挂号 13-翼健康 40-医程通
     */
    @XmlElement(name = "ORDERTYPE")
    private String orderType;
    /**
    *外部预约系统的订单号
    */
    @XmlElement(name = "ORDERID")
    private String orderId;
    /**
    *通知取号时间 格式：YYYY-MM-DD HI24:MI:SS
     */
    @XmlElement(name = "INFOTIME")
    private String infoTime=new Date().toString().substring(0,10) ;
    /**
    *HIS的订单ID
    */
    @XmlElement(name = "ORDERIDHIS")
    private String orderIdHis ;


}
