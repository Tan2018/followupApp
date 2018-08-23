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
 * @author ：Boven
 * @Description ：住院患者病案首页信息
 * @create ： 2018-01-15 16:33
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientCaseResponseRecord implements Serializable{


    private static final long serialVersionUID = 1131767324628555396L;
    /**
     *患者主索引
     */
    @XmlElement(name = "PATIENTID",required = true)
    private String patientId;
    /**
    *住院信息ID
    */
    @XmlElement(name = "INPATIENTID",required = true)
    private String inpatientId;
    /**
     *患者姓名
     */
    @XmlElement(name = "PATIENTNAME",required = true)
    private String patientName;
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
//    /**
//    *入院方式1：急诊2：门诊3：其他医疗机构转入9：其他
//     */
//    @XmlElement(name = "IP_MANNER_FLAG")
//    private String payMethod;
//    /**
//    *诊疗卡号
//    */
//    @XmlElement(name = "HEALTH_CARD_NO")
//    private String healthCardNo;

    /**
     *性别（1男，2女）
     */
    @XmlElement(name = "SEXFLAG",required = true)
    private String sex;
    /**
    *年龄
    */
    @XmlElement(name = "AGE",required = true)
    private String age;
    /**
    *国籍
    */
    @XmlElement(name = "NATIONALITY")
    private String nationAlity;
    /**
    *民族
    */
    @XmlElement(name = "NATION")
    private String nation;
    /**
     *新生儿出生体重
     */
    @XmlElement(name = "BABYBRONWEIHT",required = true)
    private String birthWeight;
    /**
     *新生儿入院体重
     */
    @XmlElement(name = "BABYINWEIGHT",required = true)
    private String checkinWeight;

    /**
     *出生地
     */
    @XmlElement(name = "BIRTHPLACE",required = true)
    private String birthPlace;
    /**
     *出生日期
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "BIRTHDAY")
    private String birthday;
    /**
     *籍贯
     */
    @XmlElement(name = "NATIVEPLACE")
    private String nativePlace;
    /**
    *身份证号
    */
    @XmlElement(name = "IDENTITYCARD")
    private String idEntitycard;
    /**
    *职业名称
    */
    @XmlElement(name = "PROFESSION")
    private String profession;
    /**
    *婚姻(9-其他,1-未婚,2-已婚,3-离婚,4-丧偶 9 其他)
    */
    @XmlElement(name = "MARITALSTATUS")
    private String marriage;
    /**
    * 现住址
    */
    @XmlElement(name = "CURRADDR")
    private String currAddr;
    /**
    *联系电话（手机）
    */
    @XmlElement(name = "PHONE")
    private String phone;

//    /**
//    *联系电话（手机）
//    */
//    @XmlElement(name = "HOME_PHONE")
//    private String homePhone;
    /**
        *现住址邮编
    */
    @XmlElement(name = "CURRPOSTCODE")
    private String currPostCode;
    /**
    *户口地址
    */
    @XmlElement(name = "RESIDENCEADDR")
    private String residenceAddr;

    /**
     *户口地址邮编
     */
    @XmlElement(name = "RESIDENCEPOSTCODE")
    private String residencePostCode;

    /**
     * 工作单位地址
     */
    @XmlElement(name = "WORKADDR",required = true)
    private String workAddr;

    /**
     * 工作单位电话
     */
    @XmlElement(name = "WORKPHONE",required = true)
    private String workPhone;

    /**
     * 工作单位地址邮编
     */
    @XmlElement(name = "WORKPOSTCODE",required = true)
    private String workPostCode;
    /**
    *联系人住址
    */
    @XmlElement(name = "CONTACTADDR")
    private String contactAddr;
    /**
    *联系人
    */
    @XmlElement(name = "CONTACTPERSON")
    private String contactPerson;
    /**
    *联系人电话
    */
    @XmlElement(name = "CONTACTPHONE")
    private String contactPhone;

    /**
    *联系人关系
    */
    @XmlElement(name = "CONTACTRELATION")
    private String contactRelationshipFlag;

    /**
     *入院途径:1.急诊 2.门诊 3.其他医疗机构转入 9.其他
     */
    @XmlElement(name = "INPATIENTWAY")
    private String inpatientWay;

    /**
     * 入院科别
     */
    @XmlElement(name = "INPATIENTDEPT")
    private String inpatientDept;

    /**
     *入院日期
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "INPATIENTDATE")
    private String inpatientDate;

    /**
     * 出院科别
     */
    @XmlElement(name = "OUTPATIENTDEPT")
    private  String outPatientDept;

    /**
     *出院时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "OUTPATIENTDATE")
    private String outpatientDate;

    /**
     * 病房
     */
    @XmlElement(name = "SOCKROOM")
    private  String sockRoom;

    /**
     * 门（急）诊诊断
     */
    @XmlElement(name = "DISEASENAME")
    private  String diseaseName;

    /**
     * 门（急）诊诊断医生
     */
    @XmlElement(name = "DISEASEDOCTOR")
    private  String diseaseDoctor;

    /**
     * 住院类型（0-入院登记,1-在院,2-转科,3-批准出院,4-出院返回,5-确认出院）
     */
    @XmlElement(name = "IPFLAG")
    private  String ipFlag;

    /**
    *床位号
    */
    @XmlElement(name = "SICK_BED_NO")
    private String sickBedNo;
    /**
    *床位
    */
    @XmlElement(name = "SICK_BED_ID")
    private String sickBedId;
    /**
    *入院病情0 其他1 危2 急3 一般
     */
    @XmlElement(name = "DISEASE_STATE_FLAG")
    private String diseaseStateFlag;

    /**
    *入院科室编码
    */
    @XmlElement(name = "ADMISSION_DEP_NO")
    private String admissionDepNo;
    /**
    *入院科室名称
    */
    @XmlElement(name = "ADMINSSION_DEP_NAME")
    private String admissionDepName;
    /**
    *住院科室编码
    */
    @XmlElement(name = "IP_DEP_NO")
    private String ipDepNo;
    /**
    *住院科室名称
    */
    @XmlElement(name = "IP_DEP_NAME")
    private String ipDepName;

    /**
     *住院天数
     */
    @XmlElement(name = "INPATIENTTIMES")
    private String inpatientTimes;
    /**
     *出院类型0-出院,1-治愈,2-好转,3-未愈,4-死亡,5-其它,6-转科
     */
    @XmlElement(name = "OUT_TYPE_FLAG")
    private String outTypeFlag;
    /**
     *最后一次手术时间
     */
    @XmlElement(name = "LASTOPERATIONTIME")
    private String lastPerationTime;
    /**
     *手术次数
     */
    @XmlElement(name = "OPERATIONTIMES")
    private String operationTimes;

}
