package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 *@Author: Boven
 *@Description: 影像检查报告
 *@Date:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryReportRecord implements Serializable{


    private static final long serialVersionUID = 3784473861557956913L;
    @XmlElement(name="IMAGEPATH")
    private String imagepath;
}
