package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientInfoRecord implements Serializable{
    private static final long serialVersionUID = -2533045238822872983L;

    @XmlElement(name="USERHISPATIENTID")
    private String userHisPatientId;

    @XmlElement(name="USERADDRESS")
    private String userAddress;

    @XmlElement(name="USERPATIENTYPELISTNAME")
    private String userPatientTypeListName;

    @XmlElement(name="USERIDCARD")
    private String userIdCard;

    @XmlElement(name="USERJKK")
    private String userJkk;

    @XmlElement(name="USERSMK")
    private String userSmk;

    @XmlElement(name="USERYBK")
    private String userYbk;

    @XmlElement(name="USERPARENTIDCARD")
    private String userParentIdCard;

    @XmlElement(name="USERNAME")
    private String userName;

    @XmlElement(name="GENDER")
    private String userGender;

    @XmlElement(name="USERMOBILE")
    private String userMobile;

    @XmlElement(name="USERBIRTHDAY")
    private String userBirthDay;

    private String primaryIndex;
}
