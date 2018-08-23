package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author ：Boven
 * @Description ：诊断信息列表
 * @create ： 2018-01-15 17:10
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class HospitalizationRecordResponseRecodeDiagnoseList {

    /**
    *诊断模式：1初步诊断，2最终诊断
    */
    @XmlElement(name = "MODE",required = false)
    private String mode;

    /**
     * 如果是初步诊断：1入院诊断2西医诊断3中医诊断10并发症（含术后和麻醉）11院内感染12中毒和损伤的外部原因13其他诊断14病理诊断
     * 如果是最终诊断：1补充诊断2出院诊断3西医诊断4修正诊断5中医诊断8出院主要诊断9出院次要诊断10并发症（含术后和麻醉）
     * 11院内感染12中毒和损伤的外部原因13其他诊断14病理诊断：
     */
    @XmlElement(name = "TYPE",required = false)
    private String type;
     /**
    *诊断的疾病编码
    */
    @XmlElement(name = "DISEASE_CODE",required = false)
    private String diseaseCode;
    /**
    *诊断的疾病名称
    */
    @XmlElement(name = "DISEASE_NAME",required = false)
    private String diseaseName;
    /**
    *诊断描述
    */
    @XmlElement(name = "DESCRIPTION",required = false)
    private String description;
    /**
    *诊断日期
    */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "DIAGNOSE_TIME",required = false)
    private String diagnoseTime;
    /**
     *经治医师
     */
    @XmlElement(name = "ATTENDING_NAME",required = false)
    private String attendingName;


}
