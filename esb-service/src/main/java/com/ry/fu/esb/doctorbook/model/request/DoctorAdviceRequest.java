package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *@Author: telly
 *@Description: 住院患者医嘱查询（界面查询）
 *@Date: Create in 15:56 2018/1/25
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorAdviceRequest implements Serializable {
    private static final long serialVersionUID = 8512346024521720641L;
    /**
     * 住院信息ID
     */
    @XmlElement(name = "INPATIENTID")
    private String inpatientId="";
    /**
     * 住院次数
     */
    @XmlElement(name = "IPTIMES")
    private String ipTimes="";
    /**
     * 住院号
     */
    @XmlElement(name = "IPSEQNOTEXT")
    private String ipSeqNoText="";
    /**
     * 开嘱开始日期
     */
    @XmlElement(name = "STARTDATE")
    private String startDate="";
    /**
     * 开嘱结束日期
     */
    @XmlElement(name = "ENDDATE")
    private String endDate="";
    /**
     * 医嘱查询类型（0全部，1长嘱，2临嘱，3出院）
     */
    @XmlElement(name = "DATYPE")
    private String daType="";
    /**
     * 医嘱过滤标识（当DATYPE=1时，FILTERFLAG=1为查询已停止医嘱，当DATYPE=2或3时，FILTERFLAG=2为查询往日医嘱；FILTERFLAG为空或其时，不作条件筛选）
     */
    @XmlElement(name = "FILTERFLAG")
    private String filterFlag;
    /**
     * 长嘱停嘱过滤
     *
     */
    private String stopFlag;

}
