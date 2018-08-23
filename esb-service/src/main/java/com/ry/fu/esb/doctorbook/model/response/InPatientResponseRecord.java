package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.DateAdapater;
import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:	医生住院患者信息
* @Date: Created in 9:33 2018/1/22
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InPatientResponseRecord implements Serializable{
    private static final long serialVersionUID = 877065100650032289L;



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
     *出生日期
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "BIRTHDAY",required = true)
    private String birthday;
    /**
     *住院科室ID
     */
    @XmlElement(name = "DEPARTMENTID",required = false)
    private String departmentId;
    /**
     *住院科室编码
     */
    @XmlElement(name = "DEPARTMENTNO",required = false)
    private String departmentNo;
    /**
     *住院科室名称
     */
    @XmlElement(name = "DEPARTMENTNAME",required = false)
    private String departmentName;
    /**
     *入院病情0 其他1 危2 急3 一般
     */
    @XmlElement(name = "DISEASE_STATE_FLAG",required = false)
    private String diseaseStateFlag;
    /**
     *入院科室ID
     */
    @XmlElement(name = "INDEPARTMENTID",required = false)
    private String InDepartmentId;
    /**
     *入院科室编码
     */
    @XmlElement(name = "INDEPARTMENTNO",required = false)
    private String InDepartmentNo;
    /**
     *入院科室名称
     */
    @XmlElement(name = "INDEPARTMENTNAME",required = false)
    private String InDepartmentName;
    /**
     *入院时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "INDATETIME",required = false)
    private String inDateTime;
    /**
     *住院信息ID
     */
    @XmlElement(name = "INPATIENTID",required = true)
    private String inpatientId;
    /**
     *无效标志：0-有效,1-无效
     */
    @XmlElement(name = "INVALIDFLAG",required = true)
    private String invalidFlag;
    /**
     *住院状态：0-入院登记,1-在院,2-转科,3-批准出院,4-出院返回,5-确认出院
     */
    @XmlElement(name = "IPFLAG",required = false)
    private String ipFlag;
    /**
     *住院号
     */
    @XmlElement(name = "IPSEQNO",required = true)
    private String ipSeqNoText;
  /*  *//**
     *住院流水号
     *//*
    @XmlElement(name = "IPSEQNO",required = true)
    private String ipSeqNo;*/
    /**
     *住院次数
     */
    @XmlElement(name = "IPTIMES",required = false)
    private String ipTimes;
    /**
     *床位号
     */
    @XmlElement(name = "SICKBEDNO",required = false)
    private String sickBedNo;

    /**
     *床位
     */
    @XmlElement(name = "SICK_BED_ID",required = false)
    private String sickBedId;
    /**
     *入院时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "OUTDATE",required = false)
    private String outDate;

    /**
    *性别：0-未知,1-男,2-女
    */
    @XmlElement(name = "SEXFLAG",required = true)
    private String sexFlag;
    /**
    *年龄
    */
    @XmlElement(name = "AGE",required = true)
    private String age;
    /**
     *出院类型：0-出院,1-治愈,2-好转,3-未愈,4-死亡,5-其它,6-转科
     */
    @XmlElement(name = "OUTTYPEFLAG",required = false)
    private String outTypeFlag;
















}
