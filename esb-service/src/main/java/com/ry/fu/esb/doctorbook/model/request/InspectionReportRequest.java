package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author: telly
 * @Description: 检验报告查询（第三方查询）
 * @Date: Create in 16:22 2018/1/11
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectionReportRequest implements Serializable{
    /**
     *
     */
    private static final Long serialVersionUID = -476521955107313845L;
    /**
     * 诊疗卡号
     */
    @XmlElement(name = "PATIENTCARDNO",required = false)
    private String patientcardNo = "";
    /**
     * 患者ID
     */
    @XmlElement(name = "PATIENTID",required = false)
    private String patientId = "";
    /**
     * 住院号
     */
    @XmlElement(name = "IPSEQNOTEXT",required = false)
    private String ipSeqnoText = "";
    /**
     * 住院次数
     */
    @XmlElement(name = "IPTIMES",required = false)
    private String ipTimes = "";
    /**
     * 住院信息ID
     */
    @XmlElement(name = "INPATIENTID",required = false)
    private String inpatientId = "";
    /**
     * 报告结果开始时间
     */
    @XmlElement(name = "RPT_BEGIN",required = false)
    private String rptBegin =new DateTime().minusMonths(6).toString().substring(0,10);
    /**
     * 报告结果结束时间
     */
    @XmlElement(name = "RPT_END",required = false)
    private String rptEnd= new DateTime().toString().substring(0,10);

    // 默认是半年内（0或空）   1.半年前
    private String pageNum;
    /**
     * 检验单号
     */
    @XmlElement(name = "EXAMINEREQUESTID",required = false)
    private String examineRequestId = "";
}
