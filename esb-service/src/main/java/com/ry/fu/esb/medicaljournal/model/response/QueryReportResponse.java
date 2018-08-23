package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 *@Author: Boven
 *@Description: 影像检查报告
 *@Date:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class QueryReportResponse implements Serializable{


    private static final long serialVersionUID = -3926686004250049372L;
    @XmlElement(name="RECORD")
    private List<QueryReportRecord> imageList;

}
