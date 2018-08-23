package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 20:21 2018/1/11
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectReportResponseRecordResultGerm implements Serializable{
    private static final long serialVersionUID = 6122179906453637098L;
    /**
     * 细菌英文名称
     */
    @XmlElement(name = "ENNAME")
    private String enname;
    /**
     * 细菌数量
     */
    @XmlElement(name = "BACT_QNY")
    private String bactQny;
    /**
     * 细菌备注
     */
    @XmlElement(name = "BACT_MEMO")
    private String bactNemo;
    /**
     * 是否多重耐药
     */
    @XmlElement(name = "MDR_FLAG")
    private String mdrFlag;
    /**
     * 是否泛耐药
     */
    @XmlElement(name = "XDR_FLAG")
    private String xdrFlag;
    /**
     * 是否全耐药
     */
    @XmlElement(name = "PDR_FLAG")
    private String pdrFlag;
    /**
     * 是否是特殊细菌
     */
    @XmlElement(name = "IS_SPECIFIC")
    private String isSpecific;
    /**
     * 细菌排序字段
     */
    @XmlElement(name = "BACT_DSP_ORD")
    private String bactDspOrd;

    @XmlElementWrapper(name="ANITBIOTISLIST")
    @XmlElement(name="ANITBIOTIS")
    private List<InspectReportResponseRecordResultGermAnitbiotis> inspectReportResponseRecordResultGermAnitbiotis;
}
