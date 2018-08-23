package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Seaton
 * @version V1.0.0
 * @Date 2018/3/19 10:48
 * @description 与ESB交互使用的对象，根据科室名称 或者 简介查询返回的字段
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorInfoByName extends BaseModel {

    /**
     * 平台医生ID
     */
    @XmlElement( name = "DOCTORID")
    private String doctorId;
    /**
     * 平台医生名称
     */
    @XmlElement( name = "DOCTORNAME")
    private String doctorName;

    /**
     * 平台科室ID
     */
    @XmlElement( name = "ORGID")
    private String deptId;
    /**
     * 平台科室名称
     */
    @XmlElement( name = "ORGNAME")
    private String deptName;

    /**
     * 上级医生ID
     */
    @XmlElement(name = "PDOCTORID")
    private String pDoctorId;

    /**
     * 上级医生Code
     */
    @XmlElement(name = "PDOCTORCODE")
    private String pDoctorCode;

    /**
     * 上级医生名字
     */
    @XmlElement(name = "PDOCTORNAME")
    private String pDoctorName;



}
