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
public class DoctorRegisterSourceByTimeResponeRecordTimeRegInfo implements Serializable{

    private static final long serialVersionUID = -7589142021466235487L;

    /**
     *号源开始日期，
     */
    @XmlElement(required = false,name = "STARTTIME")
    private String startTime;
    /**
     *号源结束日期，
     */
    @XmlElement(required = false,name = "ENDTIME")
    private String endTime;
    /**
     *可预约号源数（不包含当前排班中“加号标志”为“1-加号”的号源信息）
     */
    @XmlElement(required = true,name = "REGTOTALCOUNT")
    private String regTotalCount;
    /**
     *剩余号源数（不包含当前排班中“加号标志”为“1-加号”的号源信息）
     */
    @XmlElement(required = true,name = "REGLEAVECOUNT")
    private String regLeaveCount;

}
