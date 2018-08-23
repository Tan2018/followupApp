package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * @Author: telly
 * @Description:
 * @String: Create in 20:00 2018/1/11
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectionReportResponseRecodeResult {
    /**
     * 检验编码
     */
    @XmlElement(name = "LIS_CODE")
    private String lisCode;
    /**
     * 检验名称
     */
    @XmlElement(name = "LIS_NAME")
    private String lisName;
    /**
     * 检验结果
     */
    @XmlElement(name = "LIS_VALUE")
    private String lisValue;
    /**
     * 检验辅助结果
     */
    @XmlElement(name = "ARSLT1")
    private String arslt1;
    /**
     * 异常标志
     */
    @XmlElement(name = "NFLAG_DSPR")
    private String nflagDspr;
    /**
     * 危急值标志
     */
    @XmlElement(name = "PFLAG_DSPR")
    private String pflagDspr;
    /**
     * 审核人名称
     */
    @XmlElement(name = "LIS_REPORTER")
    private String lisReporter;
    /**
     * 采样日期
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "SAMPLING_DATE")
    private String samplingDate;
    /**
     * 送检日期
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "SUBMIT_DATE")
    private String submitDate;
    /**
     * 检验报告时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "LIS_RESULT_DATE")
    private String lisResultDate;
    /**
     * 结果值的单位
     */
    @XmlElement(name = "LIS_UOM")
    private String lisUom;
    /**
     * 结果参考范围(含上下限)
     */
    @XmlElement(name = "LIS_RANGE")
    private String lisRange;
    /**
     * 危急值参考范围
     */
    @XmlElement(name = "RRP")
    private String rrp;
    /**
     * 检验样本编码
     */
    @XmlElement(name = "LIS_SAMPLE_CODE")
    private String lisSampleCode;
    /**
     * 检验样本名称
     */
    @XmlElement(name = "LIS_SAMPLE_NAME")
    private String lisSampleName;
    /**
     * 套餐排序ID(默认降序)
     */
    @XmlElement(name = "EIS_ORD")
    private String eisOrd;
    /**
     * 项目排序ID(默认升序)
     */
    @XmlElement(name = "EI_ORD")
    private String eiOrd;

    @XmlElementWrapper(name="GERMLIST")
    @XmlElement(name="GERM")
    private List<InspectionReportResponseRecodeResultGerm> inspectionReportResponseResultGerm;

}
