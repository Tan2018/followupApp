package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;


/**
 * @author ：Joker
 * @Description ：取药窗口
 * @create ： 2018-05-21 11:42
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PrescriptionWindowRecord implements Serializable {


    private static final long serialVersionUID = 8548478809617569599L;
    /**
     *处方编号
     */
    @XmlElement(name = "RXNO")
    private String rxNo;

    /**
    *急诊标识：0-普通,1-急诊
    */
    @XmlElement(name = "EMERGENCYFLAG")
    private String emergencyFlag;

    /**
     *患者ID
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;

    /**
     *患者姓名
     */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;

    /**
     *处方类型：1-西药,2-中成药,3-中草药
     */
    @XmlElement(name = "RECIPEFLAG")
    private String recipeFlag;

    /**
     *挂号ID
     */
    @XmlElement(name = "REGISTERID")
    private String registerId;

    /**
     *挂号日期
     */
    @XmlElement(name = "REGISTERDATE")
    private String registerDate;

    /**
     *发药窗口名称
     */
    @XmlElement(name = "SENDMEDICINEWINDOWNAME")
    private String sendMedicineWindowName;




}
