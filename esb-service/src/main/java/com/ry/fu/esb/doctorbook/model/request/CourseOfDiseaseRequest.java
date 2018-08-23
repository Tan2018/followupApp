package com.ry.fu.esb.doctorbook.model.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
* @Author:Jane
* @Description:病程记录
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseOfDiseaseRequest implements Serializable {

    private static final long serialVersionUID = -3482692491543966557L;
    /**
    *患者id
    */
    @XmlElement(required = true, name = "PATIENTID")
    private String patientId;
    /**
    *住院号
    */
    @XmlElement(required = false, name = "IPSEQNOTEXT")
    private String ipseqnoText;
    /**
    *住院次数
    */
    @XmlElement(required = true, name = "IPTIMES")
    private String ipTimes;

}
