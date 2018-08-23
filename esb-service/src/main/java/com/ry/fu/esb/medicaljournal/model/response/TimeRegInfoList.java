package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
* @Author:Boven
* @Description:科室号源响应实体
* @Date: Created in 11:06 2018/2/28
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class TimeRegInfoList implements Serializable {

    private static final long serialVersionUID = -1100228606096596538L;

    @XmlElement(name = "REGDATE")
    private String regDate;

    @XmlElement(name = "TIMEREGINFO")
    private List<TimeRegInfoItem> timeRegInfoItemList;
}
