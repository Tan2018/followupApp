package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author ：Boven
 * @Description ：单次挂号检查报告
 * @create ： 2018-03-23 15:29
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
public class RegisterExaminRequest implements Serializable {


    private static final long serialVersionUID = 2985215843417591009L;
    /**
    *挂号id
     */
    @XmlElement(name = "REGISTRATIONID")
    private String registrId;

}
