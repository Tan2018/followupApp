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
* @Description:标记已受理
* @Date: Created in 19:44 2018/3/6
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class MarkAdmissibleRequest implements Serializable{


    private static final long serialVersionUID = -1039236855897490707L;
    /**
     *医生id
     */
    @XmlElement(name="DOCTORID")
    private String doctorId;
    /**
     *患者ID
     */
    @XmlElement(name="PATIENTID")
    private String patientId;
    /**
     * 是否已受理 0.	否1.	是
     */
    @XmlElement(name="isAccept")
    private String isAccept;


}
