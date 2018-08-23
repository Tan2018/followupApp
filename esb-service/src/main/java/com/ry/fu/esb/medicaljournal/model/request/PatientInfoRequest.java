package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class PatientInfoRequest implements Serializable{

    private static final long serialVersionUID = -5091535062812392363L;

    /**
     * 用户Id
     */
    private  String accountId;

    @XmlElement(name = "USERCARDTYPE")
    private String userCardType = "1";

    /**
     * 用户证件号码
     */
    @XmlElement(name = "USERCARDID")
    private String userCardId;

    /**
     * 患者姓名
     */
    @XmlElement(name = "USERNAME")
    private String userName;

    /**
     * 患者身份证号码
     */
    @XmlElement(name = "USERIDCARD")
    private String userIdCard;

    /**
     * 患者id
     */
    private String esbPatientId;

    private String relationship;

}
