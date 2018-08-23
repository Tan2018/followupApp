package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 检查检验费用
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-04-03 9:52
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class InspectionCostRequest implements Serializable {
    private static final long serialVersionUID = -3175440721980502524L;
    /**
    *诊疗卡号
    */
    @XmlElement(name = "PATIENTCARDNO")
    private String patientCardNo;
    /**
     *患者ID
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;
    /**
     *0（为空）-全部，4-检查，5-检验
     */
    @XmlElement(name = "SOURCETYPE")
    private String sourceType;
    /**
     *开始挂号时间（yyyy-mm-dd）
     */
    @XmlElement(name = "REGISTERSTARTDATE")
    private String registerStartDate=new DateTime().minusMonths(6).toString().substring(0,10);
    /**
     *结束挂号时间（yyyy-mm-dd）
     */
    @XmlElement(name = "REGISTERENDDATE")
    private String registerEndDate =new DateTime().toString().substring(0,10);


}
