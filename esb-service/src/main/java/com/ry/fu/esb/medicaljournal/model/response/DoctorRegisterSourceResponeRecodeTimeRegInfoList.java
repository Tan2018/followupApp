package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

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
public class DoctorRegisterSourceResponeRecodeTimeRegInfoList implements Serializable{

    private static final long serialVersionUID = 8281307925538563576L;
    /**
     *出诊日期，格式：YYYY-MM-DD
     */
    @XmlElement(required = true,name = "REGDATE")
    private String regDate;
    /**
     *出诊日期对应的星期
     */
    @XmlElement(required = true,name = "REGWEEKDAY")
    private String regWeekDay;


    @XmlElement(name = "TIMEREGINFO")
    private List <DoctorRegisterSourceResponeRecodeTimeRegInfoListTimeRegInfo> timeRegInfoList;

}
