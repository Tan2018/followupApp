package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 20:40 2018/1/11
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectionReportResponseRecodeResultGermAnitbiotis {
    /**
     * 抗生素中文名称（药敏）
     */
    @XmlElement(name = "NAME_CN")
    private String nameCn;
    /**
     * 抗生素英文名称（药敏）
     */
    @XmlElement(name = "NAME_EN")
    private String nameEn;
    /**
     * KB药敏检验结果
     */
    @XmlElement(name = "KB_RESULT")
    private String kbResult;
    /**
     * MIC药敏检验结果
     */
    @XmlElement(name = "MIC_RESULT")
    private String micResult;
    /**
     * 敏感度
     */
    @XmlElement(name = "SENS")
    private String sens;
    /**
     * 折点指南
     */
    @XmlElement(name = "BR_GLDN")
    private String brGldn;
    /**
     * KB值使用的折点
     */
    @XmlElement(name = "BR_KB")
    private String brKb;
    /**
     * MIC值使用的折点
     */
    @XmlElement(name = "BR_MIC")
    private String brMic;
    /**
     * 检验方法
     */
    @XmlElement(name = "EM_DS")
    private String emDs;
    /**
     * 纸片含量
     */
    @XmlElement(name = "POTENCY")
    private String potency;
    /**
     * 抗生素排序字段
     */
    @XmlElement(name = "ANTI_DSP_ORD")
    private String antiDspOrd;


}
