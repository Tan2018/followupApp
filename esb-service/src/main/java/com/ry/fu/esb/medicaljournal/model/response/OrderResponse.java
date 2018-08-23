package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author jane
 * @Date 2018/5/3
 * 获取历史订单
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderResponse implements Serializable {
    private static final long serialVersionUID = 3532666846288627645L;

    /**
     * 订单ID
     */
    @XmlElement(name = "ORDERID")
    private String orderId;
    /**
     * 费用类型
     */
    @XmlElement(name = "FEETYPE")
    private String feeType;
    /**
     * 医生
     */
    @XmlElement(name = "DOCTORNAME")
    private String doctorName;
    /**
     * 订单创建时间
     */

    @XmlElement(name = "CREATETIME")
    private String createTime;
    /**
     * 订单状态
     */
    @XmlElement(name = "ORDERSTATUS")
    private String orderStatus;
    /**
     * 订单总费用
     */
    @XmlElement(name = "TOTALFEE")
    private String totalFee;

    /**
     * 医保支付
     */
    @XmlElement(name = "MEDICALFEE")
    private String medicalFee;

    /**
     * 自费支付
     */
    @XmlElement(name = "PERSONALFEE")
    private String personalFee;

    /**
     * 科室
     */
    @XmlElement(name = "ORGNAME")
    private String orgName;

    /**
     * 患者姓名
     */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;

    /**
     * 患者id
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;

    /**
     * 患者身份证号
     */
    @XmlElement(name = "IDCARD")
    private String idCard;

    /**
     * 诊疗卡号
     */
    @XmlElement(name="HEALTHCARDNO")
    private String healthCardNo;

    /**
     * 患者电话
     */
    @XmlElement(name="PHONE")
    private String phone;


}
