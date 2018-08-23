package com.ry.fu.esb.medicaljournal.model.request;

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
 * @Author: Boven
 * @Description: 检查结果查询（影像结果和其他结果）
 * @Date: Create in 16:22 2018/1/11
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class TestDetailsRequest implements Serializable{
    private static final long serialVersionUID = -1052446992868518231L;
    /**
     *
     */

    /**
     * 患者ID
     */
    @XmlElement(name = "PATIENTID",required = false)
    private String patientId ;
    /**
     * 诊疗卡号
     */
    @XmlElement(name = "PATIENTCARDNO",required = false)
    private String patientCardNo ;
    /**
     * 住院号
     */
    @XmlElement(name = "IPSEQNOTEXT",required = false)
    private String ipSeqNoText ;
    /**
     * 住院次数
     */
    @XmlElement(name = "IPTIMES",required = false)
    private String ipTimes ;
    /**
     * 检查类型，默认全部: 1MR 2X光 3CT 4超声 5内镜 6核医学 7心电图 8病理资料 9ALO 10ALO-随医拍 99其他（多个类型，逗号分隔）
     */
    @XmlElement(name = "FUNC_TYPE",required = false)
    private String funcType = "1,2,3,4,5,6,7,8,9,10,99";
    /**
     * 起始日期（不填默认当天）
     */
    @XmlElement(name = "STARTDATE",required = false)
    private String startDate= new DateTime().minusMonths(6).toString().substring(0,10) ;
    /**
     * 结束日期（不填默认当天）
     */
    @XmlElement(name = "ENDDATE",required = false)
    private String endDate=new DateTime().toString().substring(0,10);
}
