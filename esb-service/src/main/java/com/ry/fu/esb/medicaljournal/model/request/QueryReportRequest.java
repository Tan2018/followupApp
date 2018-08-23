package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 查看报告
 *
 * @author ：Boven
 * @Description ：影像检查报告
 * @create ： 2018-04-16 15:27
 **/
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@EqualsAndHashCode(callSuper = false)
public class QueryReportRequest implements Serializable {

    private static final long serialVersionUID = -4981517462874605143L;
    /**
     * 患者住院ID
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;
    /**
     * 检查单号
     */
    @XmlElement(name = "FUNCTIONREQUESTFORPACSID")
    private String funcId;
}
