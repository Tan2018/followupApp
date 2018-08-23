package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 9:24 2018/1/29
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectReportResponseRecord implements Serializable{

    private static final long serialVersionUID = 52687910859850194L;
    @XmlElement(name = "ITEMARRY")
    ArrayList<String> itemArry;
    @XmlElement(name = "DATEARRY")
    ArrayList<String> dateArry;
    @XmlElement(name = "LISTARRY")
    ArrayList<String> listArry ;
    private Map<String,String> map;

    private  String flagReport="0";
    /**
     *检验申请单ID
     */
    @XmlElement(name = "ExamineRequestID")
    private String examineRequestId;
    /**
     *检验申请单号
     */
    @XmlElement(name = "ExamineRequestNO")
    private String examineRequestNo;
    /**
     *项目组合
     */
    @XmlElement(name = "ITEMSET")
    private String itemSet;
    /**
     *申请医生
     */
    @XmlElement(name = "REQUESTDOCTOR")
    private String requestDoctor;
    /**
     *申请科室
     */
    @XmlElement(name = "REQUESTDEPARTMENT")
    private String requestDepartment;
    /**
     *申请时间
     */
 /*   @XmlJavaTypeAdapter(TimeAdapater.class)*/
    @XmlElement(name = "REQUESTTIME")
    private String requestTime;
    /**
     *标本号
     */
    @XmlElement(name = "LIST_NO")
    private String listNo;
    /**
     *检验报告PDF地址
     */
    @XmlElement(name = "REPORT_PDFURL")
    private String reportPdfurl;
    /**
     *
     */

    @XmlElementWrapper(name="RESULTS")
    @XmlElement(name="ITEM")
    private ArrayList<InspectReportResponseRecordResult> inspectReportResponseRecordResult;
}
