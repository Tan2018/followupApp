package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/4/12 17:40
 * @description 用户待缴费记录支付
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class PayRequest implements Serializable {

    /**
     * 用户证件类型
     */
    @XmlElement(name = "USERCARDTYPE")
    private String userCardType ="0";

    /**
     * 用户证件号码
     */
    @XmlElement(name = "USERCARDID")
    private String userCardId;

    /**
     * 就诊登记号
     */
    @XmlElement(name = "INFOSEQ")
    private String infoSeq;

    /**
     * 外部预约系统的收费订单号
     */
    @XmlElement(name = "ORDERID")
    private String orderId;

    /**
     * 支付流水号
     */
    @XmlElement(name = "PAYCARDNUM")
    private String payCardNum;

    /**
     * 支付金额(单位“分”)
     */
    @XmlElement(name = "PAYAMOUT")
    private String payAmout;

    /**
     * 支付方式
     */
    @XmlElement(name = "PAYMODE")
    private String payMode = "40";

    /**
     * 交易时间，格式：YYYY-MM-DD HI24:MI:SS
     */
    @XmlElement(name = "PAYTIME")
    private String payTime;

    /**
     * 缴费细目流水号
     */
    //@XmlElementWrapper(name = "ORDERDETAILINFO")
    @XmlElement(name = "DETAILID")
    private String detailId;


    private String outOrderNo;

    /*//private Long payId;

    public void setUserCardType(String userCardType) {
        this.userCardType = "1";
    }

    public void setPayMode(String payMode) {
        this.payMode = "40";
    }*/
}
