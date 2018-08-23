package com.ry.fu.esb.doctorbook.model.response;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
* @Author:Boven
* @Description:主索引查询
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PrimaryIndexQueryPatientRecord implements Serializable {


    private static final long serialVersionUID = -2441722190990477726L;
    /**
    *主索引ID
    */
    @XmlElement(required = true, name = "MPI_ID")
    private String mpiId;
    private String count;
    private String patientFlag;
    /**
     *患者id
     */
    @XmlElement(required = true, name = "PATIENT_ID")
    private String patientId;
    /**
     *就诊卡号
     */
    @XmlElement(required = true, name = "PATIENT_CARD_NO")
    private String patientCardNo;
    private String ipSeqNoText;
    private List<String> patientIdList;
    private List<String> ipSeqNoTextList;
    private List<String> patientCardNoList;
    private List< HashMap<String,String>> mapList;
    /**
     *性别：0-未知,1-男,2-女
     */
    @XmlElement(required = true, name = "SEX")
    private String sexFlag;


    /**
     *患者姓名
     */
    @XmlElement(required = true, name = "PATIENT_NAME")
    private String patientName;


    /**
     *电话
     */
    @XmlElement(required = true, name = "PHONE")
    private String phone;
    /**
     *出生日期
     */
    @XmlElement(required = true, name = "BIRTHDAY")
    private String birthday;
    /**
     *年龄
     */
    private String age;
    /**
     *婚姻状况：0-未知,1-未婚,2-已婚,3-离婚,4-丧偶,100-HIS30使用
     */
    @XmlElement(required = true, name = "MARRIAGE_FLAG")
    private String marriageFlag;
    /**
     *职业
     */
    @XmlElement(required = true, name = "PROFESSION_NAME")
    private String professionName;
    /**
     *证件号
     */
    @XmlElement(required = true, name = "IDENTITY_CARD_NO")
    private String IdCardNo;
   /*  应前端要求，注释暂时没用字段 *//**
     *数据来源（IP/OP）"1：OP 2：IP"
     *//*
    @XmlElement(required = true, name = "SOURCE_TYPE")
    private String sourceType;*/
   /**
     *证件类型：其他证件/身份证/港澳台身份证/护照
     *//*
    @XmlElement(required = true, name = "ID_TYPE")
    private String idType;
    *//**
     *医疗证号
     *//*
    @XmlElement(required = true, name = "MEDICAL_CERTIFICATE_NO")
    private String medicalCertificateNo;
    *//**
     *医保卡号
     *//*
    @XmlElement(required = true, name = "MEDICARE_CARD_NO")
    private String medicareCardNo;

    *//**
     *市民卡号
     *//*
    @XmlElement(required = true, name = "CITIZEN_CARD_NO")
    private String citizenCardNo;*/
    /**
     *联系人
     */
    @XmlElement(required = true, name = "CONTACT_PERSON")
    private String contactPerson;

   /**
     *家庭住址
     */
    @XmlElement(required = true, name = "ADDRESS")
    private String address;

    /**
     *移动电话
     */
    @XmlElement(required = true, name = "CELL_PHONE")
    private String cellPhone;
     /**
      * *籍贯
     */
    @XmlElement(required = true, name = "NATIVE_PLACE")
    private String nativePlace;
   /**
     *单位电话
     *//*
    @XmlElement(required = true, name = "COMPANY_PHONE")
    private String companyPhone;
    *//**
     *国籍
     *//*
    @XmlElement(required = true, name = "NATIONALITY_NAME")
    private String nationalName;
    *//**
     *籍贯
     *//*
    @XmlElement(required = true, name = "NATIVE_PLACE")
    private String nativePlace;
    /**
     *民族
     */
    @XmlElement(required = true, name = "RACE_NAME")
    private String raceName;
    @XmlElement(name="PATIENTLIST")
    private List<PrimaryIndexQueryPatientListRecordList> patientList;





}
