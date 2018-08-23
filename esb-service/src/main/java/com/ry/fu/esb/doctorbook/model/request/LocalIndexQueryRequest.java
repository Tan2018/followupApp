package com.ry.fu.esb.doctorbook.model.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:本地主索引查询
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class LocalIndexQueryRequest implements Serializable {


    private static final long serialVersionUID = -71786234970581123L;
    /**
     *患者id
     */
    @XmlElement(required = true, name = "PATIENT_ID")
    private Long patientId;
    /**
     *患者姓名
     */
    @XmlElement(required = true, name = "PATIENT_NAME")
    private String patientName;
    /**
     *住院号
     */
    @XmlElement(required = true, name = "IP_SEQ_NO")
    private String ipSeqNoText;
    /**
     *门诊卡号
     */
    @XmlElement(required = true, name = "PATIENT_CARD_NO")
    private String patientCardNo;






}
