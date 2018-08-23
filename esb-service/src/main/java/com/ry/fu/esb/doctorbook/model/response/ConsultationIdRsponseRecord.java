package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/9 16:45
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ConsultationIdRsponseRecord {

    /**
     *序列ID
     */
    @XmlElement(name = "SEQ_ID")
    private String seqId;
}
