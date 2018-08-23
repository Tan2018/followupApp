package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:	医生住院患者信息
* @Date: Created in 9:33 2018/1/22
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class HospitalStatisticsRecord implements Serializable{

    private static final long serialVersionUID = 8250597945505496058L;
    /**
    *科室ID
    */
    @XmlElement(name = "DEPARTMENTID",required = true)
    private String departmentId;
    /**
     *科室名称
     */
    @XmlElement(name = "DEPARTMENTNAME",required = false)
    private String departmentName;
    /**
     * 统计数据分类 1、门诊人次2、住院人次3、手术台次
     */
    @XmlElement(name = "DATATYPE",required = false)
    private String dataType ;

    private String dataTypeDesc ;

    private String isToday ;
    private String statisticsIndex ;

    /**
     * 日期
     */
    @XmlElement(name = "DATADATE",required = false)
    private String dataDate = "";

    /**
     *数量
     */
    @XmlElement(name = "DATANUM",required = false)
    private String dataNum;

















}
