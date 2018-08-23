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
 * @Description ：单次挂号检验报告
 * @create ： 2018-03-23 15:29
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
public class RegisteInspectRequest implements Serializable {


    private static final long serialVersionUID = 2700207222892113619L;
    /**
    *挂号id
     */
    @XmlElement(name = "REGISTRATIONID")
    private String registrId;
    /**
    *患者类型为I时：住院号 患者类型为T/V时：体检登记号

     */
    @XmlElement(name = "IPSEQNOTEXT")
    private String ipSeqNoText;

}
