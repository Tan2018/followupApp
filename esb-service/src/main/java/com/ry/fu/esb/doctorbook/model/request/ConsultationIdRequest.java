package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/9 16:44
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class ConsultationIdRequest {

    /**
     * Id类型
     */
    @XmlElement(name = "SEQ_TYPE")
    private String seqType;
}
