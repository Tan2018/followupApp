package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:	医生住院患者信息诊断信息
* @Date: Created in 9:33 2018/1/22
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientInfoDiagnose implements Serializable{


    private static final long serialVersionUID = -5812641800542240159L;
    /**
    *诊断模式：1初步诊断，2最终诊断
    */
    @XmlElement(name = "MODE",required = true)
    private String mode;
    /**
    *诊断类型：
     如果是初步诊断：
     1入院诊断 2西医诊断 3中医诊断 10并发症（含术后和麻醉）11院内感染 12中毒和损伤的外部原因 13其他诊断 14病理诊断
     如果是最终诊断：1补充诊断 2出院诊断 3西医诊断 4修正诊断 5中医诊断 出院主要诊断 9出院次要诊断 10并发症（含术后和麻醉）11院内感染 12中毒和损伤的外部原因 13其他诊断 14病理诊断
     */
    @XmlElement(name = "TYPE",required = true)
    private String diagnoseType;
    /**
     *诊断的疾病编码
     */
    @XmlElement(name = "DISEASE_CODE",required = true)
    private String diseaseCode;
    /**
     *诊断的疾病名称
     */
    @XmlElement(name = "DISEASE_NAME",required = true)
    private String diseaseName;
    /**
     *诊断描述
     */
    @XmlElement(name = "DESCRIPTION",required = true)
    private String description;

    /**
    *性别：诊断日期
    */
    @XmlElement(name = "DIAGNOSE_TIME",required = true)
    private String diagnoseTime;
    /**
    *经治医师
    */
    @XmlElement(name = "ATTENDING_NAME",required = true)
    private String attendingName;



}
