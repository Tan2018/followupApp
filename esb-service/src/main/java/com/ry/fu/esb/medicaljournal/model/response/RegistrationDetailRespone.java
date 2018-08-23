package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 挂号详情
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-04-11 17:49
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class RegistrationDetailRespone implements Serializable {
    private static final long serialVersionUID = 2070592024955269024L;

    @XmlElement(name="RECORD")
    private RegistrationDetailRecord record;

}
