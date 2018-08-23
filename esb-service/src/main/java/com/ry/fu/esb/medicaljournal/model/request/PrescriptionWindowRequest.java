package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author ：Joker
 * @Description ：
 * @create ： 2018-05-21 11:17
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
public class PrescriptionWindowRequest implements Serializable {


    private static final long serialVersionUID = 92278198312681637L;
    /**
     * 就诊卡号
     */
    @XmlElement(name = "PATIENTCARDNO")
    private String patientCardNo;

    /**
     * 患者ID
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;

    /**
     * 0（为空）-全部，1-西药，2-中成药，3-中草药
     */
    @XmlElement(name = "SOURCETYPE")
    private String sourceType;

    /**
     * 开始时间
     */
    @XmlElement(name = "REGISTERSTARTDATE")
    private String registerStartDate;

    /**
     * 结束时间
     */
    @XmlElement(name = "REGISTERENDDATE")
    private String registerEndDate;



}
