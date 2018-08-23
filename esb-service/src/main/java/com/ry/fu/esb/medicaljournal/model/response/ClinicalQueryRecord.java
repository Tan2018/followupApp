package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-23 15:48
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ClinicalQueryRecord implements Serializable {

    private static final long serialVersionUID = 3631021303498452755L;

    /**
    *平台科室ID
    */
    @XmlElement(name = "DEPTID")
    private String deptId;
    /**
     *平台科室名称
     */
    @XmlElement(name = "DEPTNAME")
    private String deptName;

    @XmlElement(name = "PARENTID")
    private String parentId;

    @XmlElement(name = "PARENTNAME")
    private String parentName;
    /**
     *医生id
     */
    @XmlElement(name = "DOCTORID")
    private String doctorId;
    /**
     *医生姓名
     */
    @XmlElement(name = "DOCTORNAME")
    private String doctorName;
    /**
     *挂号类型
     */
    @XmlElement(name = "REGTYPE")
    private String regType;
    /**
     *时段名称
     */
    @XmlElement(name = "TIMENAME")
    private String timeName;
}
