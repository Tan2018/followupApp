package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.DateAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-05-16 16:58
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientQueryRecord implements Serializable {

    private static final long serialVersionUID = -685126681712035046L;
    /**
     *诊疗卡号
     */
    @XmlElement(name = "PATIENTCARDNO")
    private Long patientCardNo;
    /**
     *患者ID
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;
    /**
     *患者IdList
     */
    private List<String> patientIdList;

    /**
     *患者姓名
     */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;

    /**
     *住院号
     */
    @XmlElement(name="IPSEQNOTEXT",required = false)
    private String ipSeqNoText;

    /**
     *出生日期
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "BIRTHDATE")
    private String birthday;
    /**
     *性别（1男，2女，3未知）
     */
    @XmlElement(name = "GENDER")
    private String sexFlag;
    /**
     *身份证号码
     */
    @XmlElement(name = "IDCARDNO")
    private String IdCardNo;
    /**
     *婚姻状况 0-未知,1-未婚,2-已婚,3-离婚,4-丧偶
     */
    @XmlElement(name = "MARRIAGEFLAG")
    private String marriageFlag;
    /**
     *电话
     */
    @XmlElement(name = "CELLPHONE")
    private String cellPhone;
    /**
     *电话
     */
    @XmlElement(name = "PHONE")
    private String phone;
    /**
     *医疗证号
     */
    @XmlElement(name = "MEDICALCERTIFICATENO")
    private String medicalCertificateNo;
    /**
     *医保卡号
     */
    @XmlElement(name = "MEDICARECARDNO")
    private String medicareCardNo;
    /**
     *医保个人编号
     */
    @XmlElement(name = "MEDICAREPERSONNELNO")
    private String medicarePersonnelNo;
    /**
     *医保类型
     */
    @XmlElement(name = "MEDICARETYPE")
    private String medicareType;
    /**
     *邮政编号
     */
    @XmlElement(name = "POSTALCODE")
    private String postalCode;
    /**
     *联系人
     */
    @XmlElement(name = "CONTACTPERSION")
    private String contactPersion;
    /**
     *电子邮箱
     */
    @XmlElement(name = "EMAIL")
    private String email;
    /**
     *家庭住址
     */
    @XmlElement(name = "ADDRESS")
    private String address;
    /**
     *地区（0-本区,1-本市,2-本省,3-外省,4-港澳台,5-外国）
     */
    @XmlElement(name = "AREAFLAG")
    private String areaFlag;
    /**
     *血型：1-A,2-B,3-AB,4-O,5-其它,6-未查
     */
    @XmlElement(name = "BLOODTYPEFLAG")
    private Integer bloodTypeFlag;
    /**
     *单位地址
     */
    @XmlElement(name = "COMPANYADDRESS")
    private String companyAddress;
    /**
     *单位传真
     */
    @XmlElement(name = "COMPANYFAX")
    private String companyFax;
    /**
     *单位名称
     */
    @XmlElement(name = "COMPANYNAME")
    private String companyName;
    /**
     *单位电话
     */
    @XmlElement(name = "COMPANYPHONE")
    private String companyPhone;
    /**
     *国籍
     */
    @XmlElement(name = "NATIONALITY")
    private String nationality;
    /**
     *籍贯
     */
    @XmlElement(name = "NATIVEPLACE")
    private String nativePlace;
    /**
     *职业
     */
    @XmlElement(name = "PROFESSION")
    private String profession;
    /**
     *民族
     */
    @XmlElement(name = "RACE")
    private String race;
    /**
     *RH血型：1-阴性,2-阳性,3-未查
     */
    @XmlElement(name = "RHFLAG")
    private Integer RHFlag;
    /**
     *联系人电话
     */
    @XmlElement(name = "CONTACTPHONE")
    private String contactPhone;
    /**
     *现住址
     */
    @XmlElement(name = "CURRENTADDRESS")
    private String currentAddress;
    /**
     *门诊患者 ipFlag为空
     *住院患者：0-入院登记,1-在院,2-转科,3-批准出院,4-出院返回,5-确认出院
     */
    @XmlElement(name = "IPFLAG")
    private String patientFlag;
    /**
     *年龄
     */
    private String age;
    /**
     *住院的床号
     */
    private String bed;
    /**
     *住院多少天
     */
    private String hospitalDay;
    /**
     *手术后多少天
     */
    private String postoperativeDay;


}
