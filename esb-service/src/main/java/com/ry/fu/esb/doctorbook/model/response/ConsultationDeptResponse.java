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
 * @Author:Tom
 * @Description:
 * @create: 2018/6/27 9:29
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class ConsultationDeptResponse  implements Serializable {
    private static final long serialVersionUID = 7799749417870547021L;

    @XmlElement(name="RECORD")
    private List<ConsultationDeptResponseRecord> consultationDeptResponseRecord;
}
