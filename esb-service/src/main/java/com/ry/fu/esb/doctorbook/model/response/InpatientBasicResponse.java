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
 * @author ：Boven
 * @Description ：患者基本信息
 * @create ： 2018-02-01 17:53
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class InpatientBasicResponse implements Serializable {
    private static final long serialVersionUID = 5766806001850643954L;

    @XmlElement(name="RECORD")
    private List<InpatientBasicResponseRecode> inpatientBasicResponse;

}
