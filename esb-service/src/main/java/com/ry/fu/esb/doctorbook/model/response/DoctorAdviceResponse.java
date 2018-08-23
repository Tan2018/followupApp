package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 *@Author: telly
 *@Description: 住院患者医嘱查询（界面查询）
 *@Date: Create in 15:52 2018/1/25
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class DoctorAdviceResponse implements Serializable{

    private static final long serialVersionUID = 2020786799777726720L;


    private String newAcvice="0";

    private String count;
    @XmlElement(name="RECORD")
    private List<DoctorAdviceResponseRecode> doctorAdviceResponseRecode;

}
