package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/9 16:44
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class ConsultationIdRsponse {

    @XmlElement(name="RECORD")
    private List<ConsultationIdRsponseRecord> ConsultationIdRsponseRecord;
}
