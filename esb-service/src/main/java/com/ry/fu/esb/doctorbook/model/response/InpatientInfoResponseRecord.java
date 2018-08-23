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
* @Author:Boven
* @Description:	医生住院患者信息
* @Date: Created in 9:33 2018/1/22
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientInfoResponseRecord implements Serializable{
    private static final long serialVersionUID = 877065100650032289L;




    /**
     *初步诊断
     */
    private ArrayList<String> preliminaryDiagnosis;

    private String  strDiagnosis;
    /**
    *住院信息ID
    */
    @XmlElement(name = "INPATIENTID",required = true)
    private String inpatientId;
    /**
    *住院号
    */
    @XmlElement(name = "IPSEQNOTEXT",required = true)
    private String ipSeqNoText;
    /**
    *住院次数
    */
    @XmlElement(name = "IPTIMES",required = true)
    private String ipTimes;
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
    *床位ID
    */
    @XmlElement(name = "SICKBEDID",required = false)
    private String sickBedId;
    /**
    *床位号
    */
    @XmlElement(name = "SICKBEDNO",required = false)
    private String sickBedNo;
    /**
    *住院科室ID(入住科室)
    */
    @XmlElement(name = "DEPARTMENTID",required = true)
    private String departmentId;
    /**
    *住院科室编码(入住科室)
    */
    @XmlElement(name = "DEPARTMENTNO",required = true)
    private String departmentNo;
    /**
    *住院科室名称(入住科室)
    */
    @XmlElement(name = "DEPARTMENTNAME",required = true)
    private String departmentName;
    /**
    *入院科室ID
    */
    @XmlElement(name = "INDEPARTMENTID",required = false)
    private String indepartmentId;
    /**
    *入院科室编码
    */
    @XmlElement(name = "INDEPARTMENTNO",required = false)
    private String indepartmentNo;
    /**
    *入院科室名称
    */
    @XmlElement(name = "INDEPARTMENTNAME",required = false)
    private String indepartmentName;
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
    *无效标志：0-有效,1-无效
    */
    @XmlElement(name = "INVALIDFLAG",required = true)
    private String invalidFlag;
    /**
    *新增医嘱时间
    */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "DAINPUTDATETIME",required = true)
    private String daInputDateTime;
    private String daInputDate;

    /**
    *住院状态：0-入院登记,1-在院,2-转科,3-批准出院,4-出院返回,5-确认出院
    */
    @XmlElement(name = "IPFLAG",required = false)
    private String ipFlag;
    /**
    *护理级别：0-一般护理,1-一级护理,2-二级护理,3-三级护理,4-特级护理
    */
    @XmlElement(name = "NURSEFLAG",required = false)
    private String nurseFlag;
    /**
    *病情标志：0-其它,1-危,2-急,3-一般
    */
    @XmlElement(name = "ILLNESSSTATEFLAG",required = false)
    private String illnessstateFlag;
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
    *出院类型：0-出院,1-治愈,2-好转,3-未愈,4-死亡,5-其它,6-转科
    */
    @XmlElement(name = "OUTTYPEFLAG",required = false)
    private String outTypeFlag;




    /**
     * 年纪，又出生日日期计算，该字段非文档字段
     */
    private String age;

    /**
     *责任医师
     */
    @XmlElement(name = "RESPONSIBLEDOCTOR",required = true)
    private String responsibleDoctor;
    /**
     *手术次数
     */
    @XmlElement(name = "OPERATIONTIMES",required = true)
    private String operationTimes;
    /**
     *最后一次手术时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "LASTOPERATIONTIME",required = false)
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
    private  String hospitalDay;
    /**
    *
    */
    private  String postoperativeDay;
    /**
     * 入院天数
     */
    private  Integer inpatientDayStatus;
    /**
     * 患者状态
     */
    private  String patientStatus;




}
