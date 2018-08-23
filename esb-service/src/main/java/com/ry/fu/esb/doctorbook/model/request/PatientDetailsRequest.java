package com.ry.fu.esb.doctorbook.model.request;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 9:12 2018/1/15
 */

import lombok.Data;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author: telly
 * @Description: 检查结果查询（影像结果和其他结果）
 * @Date: Create in 16:22 2018/1/11
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class PatientDetailsRequest implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -5488559874438387691L;
    /**
     * 患者ID
     */
    @XmlElement(name = "PATIENTID",required = false)
    private String patientId = "";
    /**
     * 诊疗卡号
     */
    @XmlElement(name = "PATIENTCARDNO",required = false)
    private String patientCardNo = "";
    /**
     * 住院号
     */
    @XmlElement(name = "IPSEQNOTEXT",required = false)
    private String ipSeqnoText = "";
    /**
     * 检查单号
     */
    private String examId = "";
    /**
     * 住院次数
     */
    @XmlElement(name = "IPTIMES",required = false)
    private String ipTimes = "";
    /**
     * 检查类型，默认全部: 1MR 2X光 3CT 4超声 5内镜 6核医学 7心电图 8病理资料 9ALO 10ALO-随医拍 99其他（多个类型，逗号分隔）
     */
    @XmlElement(name = "FUNC_TYPE",required = false)
    private String funcType ;
    // 检查类型 1影像结果 2其他检查结果
    protected String requestType;
    // 默认是半年内（0或空）   1.半年前
    protected String pageNum;
    /**
     * 起始日期（不填默认当天）
     */
    @XmlElement(name = "STARTDATE",required = false)
    private String startDate = new DateTime().minusMonths(6).toString().substring(0,10);
    /**
     * 结束日期（不填默认当天）
     */
    @XmlElement(name = "ENDDATE",required = false)
    private String endDate = new DateTime().toString().substring(0,10);
}
