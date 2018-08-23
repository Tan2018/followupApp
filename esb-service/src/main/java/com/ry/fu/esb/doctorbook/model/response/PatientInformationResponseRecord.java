package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.DateAdapater;
import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/2 17:36
 * @description
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientInformationResponseRecord implements Serializable {
    private static final long serialVersionUID = -721868648090500849L;

    /**
     *初步诊断
     */
    private ArrayList<String> preliminaryDiagnosis;

    /**
     *初步诊断
     */
    private String  strDiagnosis;

    /**
     *住院号
     */
    @XmlElement(name = "IPSEQNOTEXT",required = true)
    private String ipSeqNoText;

    /**
     *患者ID
     */
    @XmlElement(name = "PATIENTID",required = true)
    private String patientId;
    /**
     *患者姓名
     */
    @XmlElement(name = "PATIENTNAME",required = true)
    private String patientName;

    /**
     *责任医师
     */
    @XmlElement(name = "RESPONSIBLEDOCTOR",required = true)
    private String responsibleDoctor;

    /**
     *性别：0-未知,1-男,2-女
     */
    @XmlElement(name = "SEXFLAG",required = true)
    private String sexFlag;

    /**
     *出生日期
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "BIRTHDAY",required = true)
    private String birthday;

    /**
     *护理级别：0-一般护理,1-一级护理,2-二级护理,3-三级护理,4-特级护理
     */
    @XmlElement(name = "NURSEFLAG",required = false)
    private String nurseFlag;

    /**
     *床位号
     */
    @XmlElement(name = "SICKBEDNO",required = false)
    private String sickBedNo;

    /**
     *入院时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "INDATE",required = true)
    private String inDate;

    /**
     *出院时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "OUTDATE",required = false)
    private String outDate;

    /**
     * 年纪，又出生日日期计算，该字段非文档字段
     */
    private String age;

    /**
     *住院次数
     */
    @XmlElement(name = "IPTIMES",required = true)
    private String ipTimes;

    /**
     *新增医嘱时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "DAINPUTDATETIME",required = true)
    private String daInputDateTime;
    private String daInputDate;


    /**
     *是否为临床路径:0其它,1临床路径
     */
    @XmlElement(name = "LCLJFLAG",required = false)
    private String lcljFlag;

    /**
     *是否为转科:0其它,1转科,2转入
     */
    @XmlElement(name = "CHANGEDEPTFLAG",required = false)
    private String changeDeptFlag;

    /**
     *是否为转科:0 默认,1临床 2转科
     */
    private String patientNameFlag;

    private String patientFlag;

    /**
     *最后一次手术时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "OPERATION_TIME",required = false)
    private String operationTime;

    /**
     *诊断信息
     */
    @XmlElementWrapper(name = "DIAGNOSELIST")
    @XmlElement(name = "DIAGNOSE",required = false)
    private List<InpatientInfoDiagnose> patientDiagnos;

    /**
     *术后天数
     */
    private  String postoperativeDay;

    /**
     * 入院天数
     */
    private  Integer inpatientDayStatus;

    /**
     * 交班状态0-未交班，1-已交班
     */
    private String shiftStatus = "0";

    /**
     * 接班状态0-未接班，1-已接班
     */
    private String takeStatus = "0";

    /**
     * 是否是自己的患者0-不是，1-是
     */
    private String flag = "1";


    /**
     * 患者类型（0.危急值患者，1.当天执行医嘱，2入院第一天 4.默认显示）。
     */
    private String patientStatus;


    private List<Long> doctorIdList;

}
