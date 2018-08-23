package com.ry.fu.esb.jpush.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ry.fu.esb.common.adapter.TimeAdapater;
import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26 10:34
 * @description 危急值患者表，主要保存患者的基本基本信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_CRITICAL_VALUE_LIST")
public class PatientInformation extends BaseModel{
    /**
     * 患者表id
     */
    @Id
    @Column(name = "ID")
    private Long id;
    /**
     * 患者名
     */
    @Column(name = "PATIENT_NAME")
    private String patientName;
    /**
     * 性别
     */
    @Column(name = "SEX")
    private String sex;
    /**
     * 年龄
     */
    @Column(name = "AGE")
    private String age;
    /**
     * 病区
     */
    @Column(name = "INPATIENT_AREA")
    private String inpatientArea;
    /**
     * 床位
     */
    @Column(name = "SEAT")
    private String seat;
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @JsonIgnore
    private Date createTime;
    /**
     * 修改时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @Column(name = "UPDATE_TIME")
    @JsonIgnore
    private Date updateTime;
    /**
     * 发送人
     */
    @Column(name = "SENDER")
    private String sender;
    /**
     * 接收人
     */
    @Column(name = "OPEND_ID")
    private String opendId;
    /**
     * 是否处理：0-未处理,1-已处理
     */
    @Column(name = "IS_HANDLER")
    private String isHandler;
    /**
     * 用户接收危机值时间
     */
    @Column(name = "CRISIS_TIME")
    @JsonIgnore
    private Date crisisTime;
    /**
     * 患者id
     */
    @Column(name = "PATIENT_ID")
    private String patientId;
    /**
     * 住院次数
     */
    @Column(name = "IP_TIMES")
    private String ipTimes;
    /**
     * 住院号
     */
    @Column(name = "IP_SEQ_NO_TEXT")
    private String ipSeqNoText;
    /**
     * 住院患者id
     */
    @Column(name = "INPATIENT_ID")
    private String inpatientId;
    /**
     * 保存患者检验项目
     */
    @Transient
    private List<CrisisProject> crisisProjectList;
    /**
     * 检验标本号
     */
    @Column(name = "LIS_LABLE_NO")
    private Long lisLableNo;
    /**
     * 检验申请单id
     */
    @Column(name = "EXAMINE_REQUEST_ID")
    private Long examineRequestId;
    /**
     * 员工id
     */
    @Column(name = "PERSON_ID")
    private String personId;
    /**
     * 员工工号
     */
    @Column(name = "DOCTOR_CODE")
    private String doctorCode;
    /**
     * 员工姓名
     */
    @Column(name = "PERSON_NAME")
    private String personName;
    /**
     * 开单医生
     */
    @Column(name = "NO_TELEVEL")
    private Long noTelevel;
    /**
     * 1：员工，2：科室
     */
    @Column(name = "NO_TETYPE")
    private Long noTetype;
    /**
     * 超时时间
     */
    @Column(name = "NO_TETIMEOUT")
    private Long noTetimeout;
    /**
     * 前端显示的危急值接收时间
     */
    @Transient
    private String crisisTimeStr;
    /**
     * 前端显示创建时间
     */
    @Transient
    private String createTimeStr;
    /**
     * 前端显示修改时间
     */
    @Transient
    private String updateTimeStr;
    /**
     * 通知类型：1：危急值通知
     */
    @Column(name = "NOTICETYPE")
    private String noticeType;
    /**
     * 1：门诊患者，2：住院患者
     */
    @Column(name = "PATIENTFLAG")
    private String patientFlag;
 }
