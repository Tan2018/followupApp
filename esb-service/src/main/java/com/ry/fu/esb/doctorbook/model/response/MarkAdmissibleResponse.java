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
* @Author:Boven
* @Description:标记已受理
* @Date: Created in 20:01 2018/3/6
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class MarkAdmissibleResponse implements Serializable{


    private static final long serialVersionUID = -4956530258001545795L;
    @XmlElement(name="RECORD")
    private List<MarkAdmissibleResponseRecord> markAdmissibleResponseRecord;
}
