package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @Author: Boven
 * @Description:检查报告
 * @Date: Create in 9:27 2018/1/15
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class TestDetailsResponseReportList implements Serializable{
    private static final long serialVersionUID = 2756259587339957599L;
    /**
     * 报告状态0.未出报告 1.已出报告
     */
    private  String flagReport="0";
    /**
     * 检查申请单号
     */
    @XmlElement(name = "FUNC_REQ_ID")
    private String funcReqId;
    /**
     * 检查类型（1MR 2X光 3CT 4超声 5内镜 6核医学 7心电图 8病理资料 9ALO 10ALO-随医拍 99其他）
     */
    @XmlElement(name = "FUNC_TYPE")
    private String funcType;
    /**
     * 检查部位或类别
     */
    @XmlElement(name = "PROCEDURESTEP_NAME")
    private String procedurestepName;
    /**
     * 申请医生工号
     */
    @XmlElement(name = "REQ_DOCTOR_EMPLOYEENO")
    private String reqDoctorEmployeeNo;
    /**
     * 申请医生名称
     */
    @XmlElement(name = "REQ_DOCTOR_EMPLOYEENAME")
    private String reqDoctorEmployeeName;
    /**
     * 申请科室ID
     */
    @XmlElement(name = "REQ_DEPTID")
    private String reqDeptId;
    /**
     * 申请科室名称（开单科室）
     */
    @XmlElement(name = "REQ_DEPTNAME")
    private String reqDeptName;
    /**
     * 检查科室ID
     */
    @XmlElement(name = "EXAM_DEPTID")
    private String examDeptId;
    /**
     * 检查科室名称
     */
    @XmlElement(name = "EXAM_DEPTNAME")
    private String examDeptName;
    /**
     * 申请时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "REQ_TIME")
    private String reqTime;
    /**
     * 报告时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "REPORT_TIME")
    private String reportTime;
    /**
     * 患者ID
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;
    /**
     * 患者姓名
     */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;
    /**
     * 诊疗卡号
     */
    @XmlElement(name = "PATIENTCARDNO")
    private String patientCardNo;
    /**
     * 住院号
     */
    @XmlElement(name = "IPSEQNOTEXT")
    private String ipSeqnoText;
    /**
     * 住院次数
     */
    @XmlElement(name = "IPTIMES")
    private String ipTimes;
    /**
     * 检查结果
     */
    @XmlElement(name = "REPORT_RESULTS")
    private String reportResults;
    /**
     * 检查诊断
     */
    @XmlElement(name = "REPORT_DIAGNOSE")
    private String reportDiagnose;
    /**
     * 报告地址
     */
    @XmlElement(name = "REPORT_PATH")
    private String reportPath;
    /**
     * 本地报告地址（fastdfs）
     */
    @XmlElement(name = "LOCAL_REPORT_PATH")
    private String localReportPath;


}
