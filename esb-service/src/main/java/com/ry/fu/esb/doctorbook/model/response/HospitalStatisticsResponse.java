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
* @Author:Jane
* @Description:医生手册住院--住院患者信息
*
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class HospitalStatisticsResponse implements Serializable{


    private static final long serialVersionUID = -2548982538328908332L;

    private  String statisticsNum;
    @XmlElement(name="RECORD")
    private List<HospitalStatisticsRecord> hospitalStatisticsRecord;


}
