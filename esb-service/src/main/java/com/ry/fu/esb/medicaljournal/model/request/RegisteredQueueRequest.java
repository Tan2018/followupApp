package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * 报道排号请求报文
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class RegisteredQueueRequest implements Serializable {


    private static final long serialVersionUID = -8097340295655271495L;
    @XmlElement(name = "PATIENTIDS")
    private String patientIds;

    @XmlElement(name = "PAGENUM")
    private String pageNum;

    @XmlElement(name = "PAGESIZE")
    private String pageSize="20";

    @XmlElement(name = "STARTDATE")
    private String startDate= DateFormatUtils.format(new Date(),"yyyy-MM-dd");

    @XmlElement(name = "ENDDATE")
    private String endDate=DateFormatUtils.format(new Date(),"yyyy-MM-dd");



}
