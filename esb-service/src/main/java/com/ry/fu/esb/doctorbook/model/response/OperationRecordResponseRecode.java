package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 17:02 2018/1/23
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class OperationRecordResponseRecode implements Serializable{

    private static final long serialVersionUID = -8652988620703303857L;

    /**
    *手术名称
    */
    @XmlElement(name = "LASTOPERATIONTIME")
    private String operationName;
    /**
    *手术日期
    */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "OPERATION_TIME")
    private String operationTime;
    /**
    *手术级别
    */
    @XmlElement(name = "OPERATION_LEVEL")
    private String operationLevel;
    /**
    *患者姓名
    */
    @XmlElement(name = "PATIENT_NAME")
    private String patientName;
    /**
    *患者性别(1表示男，2表示女)
    */
    @XmlElement(name = "PATIENT_SEX")
    private String patientSex;
    /**
    *科室
    */
    @XmlElement(name = "DEPARTMENT")
    private String department;
    /**
    *床号
    */
    @XmlElement(name = "BEDNO")
    private String bedNo;
    /**
    *住院号
    */
    @XmlElement(name = "IPSEQNOTEXT")
    private String ipSeqnoText;
    /**
    *手术风险分级NNIS
    */
    @XmlElement(name = "NNIS_LEVEL")
    private String nnisLevel;
    /**
    *择期手术（1表示是，2表示否）
    */
    @XmlElement(name = "ISSELECTIVEOPERATION")
    private String isSelectiveOperation;
    /**
    *麻醉ASA分级
    */
    @XmlElement(name = "NARCOSIS_ASA_LEVEL")
    private String narcosisAsaLevel;
    /**
    *手术诊断
    */
    @XmlElement(name = "OPERATION_DIAGNOSE")
    private String operationDiagnose;
    /**
    *麻醉方式
    */
    @XmlElement(name = "NARCOSIS_MODE")
    private String narcosisMode;
    /**
    *麻醉师
    */
    @XmlElement(name = "ANAESTHETIST")
    private String anaesthetist;
    /**
    *手术者
    */
    @XmlElement(name = "OPERATOR")
    private String operator;
    /**
    *第一助手
    */
    @XmlElement(name = "FIRST_ASSISTANT")
    private String firstAssistant;
    /**
    *第二助手
    */
    @XmlElement(name = "SECOND_ASSISTANT")
    private String secondAssistant;
    /**
    *手术过程
    */
    @XmlElement(name = "OPERATION_PROCESS")
    private String operationProcess;
    /**
    *PDF地址
    */
    @XmlElement(name = "REPORT_URL")
    private String reportUrl;
    /**
     *手术次数
     */
    @XmlElement(name = "OPERATIONTIMES")
    private String operationTimes;
    /**
     *手术时间
     */
    private List<String>  aryDate;
    /**
     *Pdf
     */
    private List<String> aryPdf;


}
