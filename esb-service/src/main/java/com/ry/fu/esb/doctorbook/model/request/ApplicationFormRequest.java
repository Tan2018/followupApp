package com.ry.fu.esb.doctorbook.model.request;

import com.ry.fu.esb.doctorbook.model.response.DepListResponse;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/28 10:40
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class ApplicationFormRequest implements Serializable{

    /**
     * 会诊id
     */
    @XmlElement(required = true, name = "CONSULTATION_ID")
    private String consultationId;

    /**
     * 会诊id
     */
    @XmlElement(required = true, name = "REPORT_ID")
    private String reportId;

    /**
     * 当前节点
     */
    @XmlElement(required = true, name = "CURRENT_NODE")
    private String currentNode;

    /**
     * 会诊类型
     */
    @XmlElement(required = true, name = "CONSULTATION_TYPE")
    private String consultationType;


    /**
     * 会诊等级
     */
    @XmlElement(required = true, name = "CONSULTATION_LEVEL")
    private String consultationLevel;

    /**
     * 申请科室ID
     */
    @XmlElement(required = true, name = "APPLY_DEPARTMENT_ID")
    private String applyDepartmentId;

    /**
     * 申请科室名称
     */
    @XmlElement(required = false, name = "APPLY_DEPARTMENT_NAME")
    private String applyDepartmentName;

    /**
     * 申请医师ID
     */
    @XmlElement(required = true, name = "APPLY_DOCTOR_ID")
    private String applyDoctorId;

    /**
     * 申请医师姓名
     */
    @XmlElement(required = false, name = "APPLY_DOCTOR_NAME")
    private String applyDoctorName;

    /**
     * 审核医师ID
     */
    @XmlElement(required = true, name = "APPROVE_DOCTOR_ID")
    private String approveDoctorId;

    /**
     * 审核医师姓名
     */
    @XmlElement(required = false, name = "APPROVE_DOCTOR_NAME")
    private String approveDoctorName;

    /**
     * 申请会诊时间（科室审核通过时间）
     */
    @XmlElement(required = false, name = "APPLY_CONSULTATION_TIME")
    private String applyConsultationTime;

    /**
     * 患者住院ID
     */
    @XmlElement(required = true, name = "INPATIENTID")
    private String inpatientId;

    /**
     * 住院号
     */
    @XmlElement(required = true, name = "IPSEQNOTEXT")
    private String ipseqnoText;

    /**
     * 住院次数
     */
    @XmlElement(required = true, name = "IPTIMES")
    private String ipTimes;

    /**
     * 患者姓名
     */
    @XmlElement(required = true, name = "PATIENTNAME")
    private String patientName;

    /**
     * 患者姓名
     */
    @XmlElement(required = true, name = "SICKBEDNO")
    private String sickBedno;

    /**
     * 患者姓名
     */
    @XmlElement(required = true, name = "SEXFLAG")
    private String sexFlag;

    /**
     * 患者姓名
     */
    @XmlElement(required = true, name = "AGE")
    private String age;


    /**
     * 病情简介及会诊目的
     */
    @XmlElement(required = true, name = "CONSULTATION_PROFILE")
    private String consultationProfile;

    /**
     * 主因
     */
    @XmlElement(required = true, name = "MAIN_CAUSE")
    private String mainCause;

    /**
     * 诊断
     */
    @XmlElement(required = true, name = "DIAGNOSE")
    private String diagnose;

    /**
     * 辅助检查
     */
    @XmlElement(required = true, name = "SUPPORT_CHECK")
    private String supportCheck;

    /**
     * 会诊意见
     */
    @XmlElement(required = true, name = "CONSULTATION_ADVICE")
    private String consultationAdvice;

    /**
     * 预约会诊时间
     */
    @XmlElement(required = true, name = "COMPLETE_TIME")
    private String completeTime;

    /**
     * 会诊完成时间
     */
    @XmlElement(required = true, name = "MEETTING_TIME")
    private String meettingTime;

    /**
     * 填写会诊意见医生id
     */
    @XmlElement(required = true, name = "RECORD_DOCTOR_ID")
    private String recordDoctorId;

    /**
     * 填写会诊意见医生姓名
     */
    @XmlElement(required = true, name = "RECORD_DOCTOR_NAME")
    private String recordDoctorName;

    /**
     * 审核意见
     */
    @XmlElement(required = true, name = "OPTIONS")
    private String options;

    /**
     * 当前操作时间
     */
    @XmlElement(required = true, name = "SUBMIT_TIME")
    private String submitTime;

    /**
     * 当前操作人
     */
    @XmlElement(required = true, name = "OPERATER")
    private String operater;

    /**
     * 操作类型
     */
    @XmlElement(required = false, name = "OPERATERTYPE")
    private String operaterType;


    /**
     * 会诊医生集合
     */
    @XmlElement(required = true, name = "DEPLIST")
    private List<DepListResponse> depList;


    /**
     * 操作人集合
     */
    @XmlElement(required = true, name = "PROCLIST")
    private List<ProcListRequest> procList;

    /**
     * 会诊科室地址
     */
    @XmlElement(required = true, name = "CONSULTATIONADDRESS")
    private String consultationAddress;

}
