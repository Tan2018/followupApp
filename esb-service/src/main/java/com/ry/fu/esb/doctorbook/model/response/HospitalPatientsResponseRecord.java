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
import java.util.ArrayList;

/**
* @Author:Boven
* @Description:	医生住院患者信息
* @Date: Created in 9:33 2018/1/22
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class HospitalPatientsResponseRecord implements Serializable{
    private static final long serialVersionUID = 877065100650032289L;


    /**
     *诊疗卡号
     */
    private Long patientCardNo;
    /**
    *患者ID
    */
    @XmlElement(name = "PATIENT_ID",required = true)
    private String patientId;
    /**
    *患者姓名
    */
    @XmlElement(name = "PATIENT_NAME",required = true)
    private String patientName;

    /**
     *民族
     */
    @XmlElement(name = "NATION",required = true)
    private String nation;

    /**
     * 国籍
     */
    @XmlElement(name = "COUNTRY",required = true)
    private String country;

    /**
     *住院号
     */
    @XmlElement(name = "IP_SEQNO_TEXT",required = true)
    private String ipSeqNoText;
    /**
     *住院ID
     */
    @XmlElement(name = "IN_PATIENT_ID",required = true)
    private String inpatientId;
    /**
     *医生姓名
     */
    @XmlElement(name = "DOCTORNAME",required = true)
    private String doctorName;

    /**
    *性别：1-男,2-女
    */
    @XmlElement(name = "SEX_FLAG",required = true)
    private String sexFlag;
    /**
    *年龄
    */
    @XmlElement(name = "AGE",required = true)
    private String age;

    /**
    *身份证件
    */
    @XmlElement(name = "IDENTITY_CARD_NO",required = true)
    private String IDNo;
    /**
    *出生日期
    */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "BIRTHDAY",required = true)
    private String birthday;

    /**
     * 出生地
     */
    @XmlElement(name = "BIRTHPLACE",required = true)
    private String birthPlace;

    /**
     * 籍贯
     */
    @XmlElement(name = "NATIVEPLACE",required = true)
    private String nativePlace;

    /**
     *婚姻状况1 未婚 2 已婚3 丧偶4 离婚9 其他

     */
    @XmlElement(name = "MARRIAGE_FLAG",required = false)
    private String marriageFlag;


    /**
    *联系电话（手机）
    */
    @XmlElement(name = "CELL_PHONE",required = false)
    private String cellPhone;
    /**
    *家庭住址
    */
    @XmlElement(name = "HOME_ADDRESS",required = false)
    private String homeAddress;
    /**
    *住址电话
    */
    @XmlElement(name = "HOME_PHONE",required = false)
    private String homePhone;
    /**
    *职业名称
    */
    @XmlElement(name = "PROFESSION_NAME",required = false)
    private String professionName;
    /**
    *联系人住址
    */
    @XmlElement(name = "CONTACT_ADDRESS",required = false)
    private String contactAddress;
    /**
    *联系人
    */
    @XmlElement(name = "CONTACT_PERSON",required = false)
    private String contactPerson;
    /**
    *联系人电话
    */
    @XmlElement(name = "CONTACT_PHONE",required = false)
    private String contactPhone;
    /**
    *联系人关系1-夫妻,2-父子,3-父女,4-母子,5-母女,6-兄弟,7-姐妹,8-亲戚,9-朋友,10-秘书,11-其他,12-婆媳,13-爷孙,14-婆孙,15-翁婿,16-兄妹,17-姐弟
    */
    @XmlElement(name = "CONTACT_RELATIONSHIP_FLAG",required = false)
    private String contactRelationshipFlag;


    /**
     *住院次数
     */
    @XmlElement(name = "IP_TIMES",required = false)
    private String ipTimes;
    /**
     *床位号
     */
    @XmlElement(name = "SICK_BED_NO",required = false)
    private String sickBedNo;
    private String bed;
    /**
     *床位
     */
    @XmlElement(name = "SICK_BED_ID",required = false)
    private String sickBedId;
    /**
     *入院病情0 其他1 危2 急3 一般
     */
    @XmlElement(name = "DISEASE_STATE_FLAG",required = false)
    private String diseaseStateFlag;
    /**
     *入院方式1：急诊2：门诊3：其他医疗机构转入9：其他
     */
    @XmlElement(name = "IP_MANNER_FLAG",required = false)
    private String ipMannerFlag;

    /**
     *入院时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "ADMISSION_TIME",required = false)
    private String admissionTime;
    /**
     *入院科室编码
     */
    @XmlElement(name = "ADMISSION_DEP_NO",required = false)
    private String admissionDepNo;
    /**
     *入院科室名称
     */
    @XmlElement(name = "ADMISSION_DEP_NAME",required = false)
    private String admissionDepName;
    /**
     *住院科室编码
     */
    @XmlElement(name = "IP_DEP_NO",required = false)
    private String ipDepNo;
    /**
     *住院科室名称
     */
    @XmlElement(name = "IP_DEP_NAME",required = false)
    private String ipDepName;
    /**
     *治疗科室编码
     */
    @XmlElement(name = "CURE_DEP_NO",required = false)
    private String cureDepNo;
    /**
     *治疗科室名称
     */
    @XmlElement(name = "CURE_DEP_NAME",required = false)
    private String cureDepName;
    /**
     *门诊医姓名
     */
    @XmlElement(name = "OP_DOCTOR_NAME",required = false)
    private String opDoctorName;
    /**
     *出院日期
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "OUT_DATE",required = false)
    private String outDate;
    /**
     *出院类型0-出院,1-治愈,2-好转,3-未愈,4-死亡,5-其它,6-转科
     */
    @XmlElement(name = "OUT_TYPE_FLAG",required = false)
    private String outTypeFlag;

    /**
     *最后一次手术时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "LASTOPERATIONTIME",required = false)
    private String lastOperationTime;
    /**
    *术后次数
    */
    @XmlElement(name = "OPERATIONTIMES",required = false)
    private  String operationTimes;
    /**
    *状态显示
    */
    private  String postoperativeDay;
    /**
     *入院天数
     */
    private  int inpatientDayStatus;

    /**
     *入院诊断
     */
    private   String StrDiagnosis;
    private ArrayList<String> preliminaryDiagnosis;



}
