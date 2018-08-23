package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Joker
 * @version V1.0.0
 * @Date 2018/3/30 10:21
 * @description 缴费明细记录Request，对应ESB的缴费明细记录的接口
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class PayTheFeesRequest implements Serializable {


    private static final long serialVersionUID = -6748132588153734943L;

    /**
     * 操作来源：8-医享网微信;9-医享网支付宝;13-翼健康;11-平安挂号;40-医程通
     */
    @XmlElement(name = "OPERATETYPE")
    private String OperateType;

    /**
     * 用户证件类型:0-患者身份证件号码;1-患者诊疗卡号码;2-患者市民卡号码;3-患者医保卡号码;4-患者监护人身份证件号码;5-患者电话
     */
    @XmlElement(name = "USERCARDTYPE")
    private String userCardType;

    /**
     * 用户证件号码
     */
    @XmlElement(name = "USERCARDID")
    private String userCardId;

    /**
     * HIS就诊登记号
     */
    @XmlElement(name = "INFOSEQ")
    private String infoSeq;


}
