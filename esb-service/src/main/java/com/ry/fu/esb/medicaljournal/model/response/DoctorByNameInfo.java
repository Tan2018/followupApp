package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 10:48
 * @description 与ESB交互使用的对象，根据科室名称 或者 简介查询返回的字段
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorByNameInfo extends BaseModel {

    /**
     * 平台医生ID
     */
    @XmlElement(required = true, name = "DOCTORID")
    private String doctorId;
    /**
     * 平台医生名称
     */
    @XmlElement(required = true, name = "DOCTORNAME")
    private String doctorName;

    /**
     * 最早的号源日期，格式：YYYY-MM-DD
     */
    @XmlElement(required = true, name = "REGDATE")
    private String regDate;

    /**
     * 平台医生职称 -- 号源类型
     */
    @XmlElement(required = true, name = "REGISTRATIONTYPE")
    private String registrationType;
    /**
     * 平台医生职称 -- 号源类型名称
     */
    @XmlElement(required = true, name = "REGISTRATIONNAME")
    private String registrationName;

    /**
     * 平台科室ID
     */
    @XmlElement(required = true, name = "DEPTID")
    private String deptId;
    /**
     * 平台科室名称
     */
    @XmlElement(required = true, name = "DEPTNAME")
    private String deptName;

    /**
     * 剩余号源数
     */
    @XmlElement(required = true, name = "COUNT")
    private String count;

    /**
     * 时段 上午 下午
     */
    @XmlElement(required = true, name = "TIMENAME")
    private String timeName;

    /**
     * 医生简介
     */
    @XmlElement(required = true, name = "DESC")
    private String desc;

    /**
     * 性别
     */
    @XmlElement(required = true, name = "SEX")
    private String sex;

    /**
     * 用户名
     */
    @XmlElement(required = true, name = "LOGINNAME")
    private String loginName;

    /**
     * 头像
     */
    @XmlElement(required = true, name = "DOCIMG")
    private String docImg;

    /**
     * 挂号费
     */
    @XmlElement(required = true, name = "PRICE")
    private String price;

    /**
     * 职称
     */
    @XmlElement(required = true, name = "TITLE")
    private String title;


    /**
     * 停诊标记
     */
    @XmlElement(required = true, name = "STOPFLAG")
    private String stopFlag;


    /**
     * 医生职称编号
     */
    @XmlElement(required = true, name = "TECHPOSTNO")
    private String techPostNo;
}
