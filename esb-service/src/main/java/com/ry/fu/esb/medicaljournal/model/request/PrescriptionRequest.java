package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-22 18:24
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
public class PrescriptionRequest implements Serializable {
    private static final long serialVersionUID = 3398615565263740117L;

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
    private String registerStartDate= DateTime.now().withMillisOfDay(0).toString().substring(0,10);

    /**
     * 结束时间
     */
    @XmlElement(name = "REGISTERENDDATE")
    private String registerEndDate= DateTime.now().toString().substring(0,10);

    // 默认是就医日志（0或空）   1.医生手册
    private String requestFlag;


}
