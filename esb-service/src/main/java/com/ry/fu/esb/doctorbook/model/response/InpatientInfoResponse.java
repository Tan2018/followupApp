package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
/**
* @Author:Boven
* @Description:	医生住院患者信息
* @Date: Created in 9:33 2018/1/22
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientInfoResponse implements Serializable{
    private static final long serialVersionUID = 877065100650032289L;

    private String patientCount;

    @XmlElement(name="RECORD")
    private ArrayList<InpatientInfoResponseRecord> inpatientInfoDetailResponse;
    


}
