package com.ry.fu.esb.jpush.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class NoteList implements Serializable{

    /**
     * 员工id/科室id
     */
    @XmlElement(required = false, name = "PERSONID")
    private String personId;
    /**
     * 员工工号/科室编码
     */
    @XmlElement(required = false, name = "PERSONNO")
    private String personNo;
    /**
     * 员工姓名/科室名称
     */
    @XmlElement(required = false, name = "PERSONNAME")
    private String personName;
    /**
     * 1、开单医生；2、科室主任值班医生；3、总值班院领导
     */
    @XmlElement(required = false, name = "NOTELEVEL")
    private Long noTelevel;
    /**
     * 1、员工；2、科室
     */
    @XmlElement(required = false, name = "NOTETYPE")
    private Long noTeType;
    /**
     * 超时时间
     */
    @XmlElement(required = false, name = "NOTETIMEOUT")
    private Long noTetimeOut;
}
