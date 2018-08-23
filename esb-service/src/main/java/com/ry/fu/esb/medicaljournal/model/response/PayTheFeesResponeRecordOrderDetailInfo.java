package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * 用户待缴费记录支付
 * @author ：Joker
 * @Description ：
 * @create ： 2018-4-12
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class PayTheFeesResponeRecordOrderDetailInfo implements Serializable{


    private static final long serialVersionUID = -5905828324373352307L;

    /**
     *缴费费别
     */
    @XmlElement(required = false,name = "DETAILFEE")
    private String detailFee;

    /**
     *缴费细目流水号，要求唯一
     */
    @XmlElement(required = false,name = "DETAILID")
    private String detailID;

    /**
     *缴费细目名称
     */
    @XmlElement(required = true,name = "DETAILNAME")
    private String detailName;

    /**
     *缴费细目数量
     */
    @XmlElement(required = true,name = "DETAILCOUNT")
    private String detailCount;

    /**
     * 缴费细目单位
     */
    @XmlElement(required = true,name = "DETAILUNIT")
    private String detailUnit;

    /**
     * 缴费细目金额(单位“分”)
     */
    @XmlElement(required = true,name = "DETAILAMOUT")
    private String detailAmout;

}
