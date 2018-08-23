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
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Boven
 * @Description ：患者入院记录信息响应参数
 * @create ： 2018-01-15 16:54
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class HospitalizationRecordResponseRecode implements Serializable{

    /**
    *患者姓名
    */
    @XmlElement(name = "PATIENT_NAME",required = true)
    private String patientName;
    /**
    *性别（1男，2女）
    */
    @XmlElement(name = "PATIENT_SEX",required = true)
    private String sex;
    /**
    *民族
    */
    @XmlElement(name = "NATION",required = true)
    private String nation;
    /**
    *年龄
    */
    @XmlElement(name = "AGE",required = true)
    private String age;
    /**
    *婚否（0表示未婚，1表示已婚）
    */
    @XmlElement(name = "ISMARRIAGE",required = true)
    private String isMarriage;
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
    *职业
    */
    @XmlElement(name = "PROFESSION",required = true)
    private String profession;
    /**
    *出生地
    */
    @XmlElement(name = "BIRTHPLACE",required = true)
    private String birthPlace;
    /**
    *入院日期
    */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "BEHOSPITAL_TIME",required = true)
    private String behospitalTime;
    /**
    *病史陈述者
    */
    @XmlElement(name = "ILLNESSER",required = true)
    private String illnesser;
    /**
    *主诉
    */
    @XmlElement(name = "COMPLAIN",required = true)
    private String complain;
    /**
    *现病史
    */
    @XmlElement(name = "NOWILLNESS",required = true)
    private String nowIllness;
    /**
    *入院记录PDF地址
    */
    @XmlElement(name = "REPORT_URL")
    private String reportUrl;
    private ArrayList<String> preliminaryDiagnosis;
    private String strDiagnosis;

    /**
     *诊断时间数组
     */
    private ArrayList<String> aryDate;
    @XmlElementWrapper(name = "DIAGNOSELIST")
    @XmlElement(name="DIAGNOSE")
    private List<HospitalizationRecordResponseRecodeDiagnoseList> hospitalizationRecordResponseDiagnoseList;



}
