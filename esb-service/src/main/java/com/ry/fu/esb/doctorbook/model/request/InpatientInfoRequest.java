package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
* @Author:Jane
* @Description:医生手册住院--住院患者信息
*
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientInfoRequest implements Serializable{

    private static final long serialVersionUID = 3093704355212954177L;

    /**
    *科室ID
    */
    @XmlElement(name="DEPARTMENTID",required = true)
    private String departmentId;
    /**
    *医生工号（建议以此查询）
    */
    @XmlElement(name="DOCTORCODE",required = false)
    private String doctorCode;
    /**
    *患者类型（1表示本科室患者，2表示本组患者，3表示我的住院患者,4其他科室
    */
    @XmlElement(name="PATIENTTYPE",required = true)
    private String patientType;



    /**
     *组别ID
     */
    private String groupId;

    /**
     *医生Id
     */
    private String doctorId;

    /**
     *附属医生Id
     */
    private String subShiftDoctorCode;


    /**
     * 查询类型（0.交班，1.接班）
     */
    private String queryType;

   /* *//**
     * 起始数
     *//*
    private Integer startRow;
    *//**
     * 第几页
     *//*
    private Integer pageNum=1;

    *//**
     * 每页多少条数据
     *//*
    private Integer pageSize=20;
*/

}
