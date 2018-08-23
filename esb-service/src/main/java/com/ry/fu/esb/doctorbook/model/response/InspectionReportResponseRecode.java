package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 9:24 2018/1/29
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectionReportResponseRecode implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -837742814748388792L;


    private String itemCount;
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
    @XmlJavaTypeAdapter(TimeAdapater.class)
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
     *检验标本
     */
    @XmlElement(name = "EXEMPPLAR")
    private String exempPlar;

    /**
     *
     */
    @XmlElementWrapper(name="RESULTS")
    @XmlElement(name="ITEM")
    private List<InspectionReportResponseRecodeResult> inspectionReportResponseResult;
}
