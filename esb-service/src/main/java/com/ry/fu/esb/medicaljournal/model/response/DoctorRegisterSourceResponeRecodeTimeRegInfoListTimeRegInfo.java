package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * 医生号源分时
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-02-28 16:16
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorRegisterSourceResponeRecodeTimeRegInfoListTimeRegInfo implements Serializable{

    private static final long serialVersionUID = -4589791432780690978L;
    /**
     *时段ID
     */
    @XmlElement(required = true,name = "TIMEID")
    private String timeId;
    /**
     *时段名称，如：上午、下午、晚上
     */
    @XmlElement(required = true,name = "TIMENAME")
    private String timeName;
    /**
     *出诊状态 1-出诊 2-停诊
     */
    @XmlElement(required = true,name = "STATUSTYPE")
    private String statusType;

    /**
     *该时段可预约号源数（不包含当前排班中“加号标志”为“1-加号”的号源信息）
     */
    @XmlElement(required = true,name = "REGTOTALCOUNT")
    private String regTotalCount;
    /**
     *该时段剩余号源数（不包含当前排班中“加号标志”为“1-加号”的号源信息）
     */
    @XmlElement(required = true,name = "REGLEAVECOUNT")
    private String regLeaveCount;
    /**
     *挂号费(单位“分”)
     */
    @XmlElement(required = true,name = "REGFEE")
    private String regFee;
    /**
     *诊疗费(单位“分”)
     */
    @XmlElement(required = true,name = "TREATFEE")
    private String treatFee;

    @XmlElement(required = true,name = "REGTYPE")
    private String regType;
    /**
     *是否有分时 0-  否 1-  是
     */
    @XmlElement(required = true,name = "ISTIMEREG")
    private String isTimeReg;
}
