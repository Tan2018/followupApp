package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 挂号请求(实现锁定号源)
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class RegisterLockedInfoRequest implements Serializable {
    private static final long serialVersionUID = -3804978379162543699L;

    @XmlElement(name = "ORDERTYPE")
    private String orderType;

    @XmlElement(name = "LOCKID")
    private String lockId;

    @XmlElement(name = "ORDERID")
    private String orderId;

    @XmlElement(name = "USERHISPATIENTID")
    private String userHisPatientId;

    @XmlElement(name = "USERIDCARD")
    private String userIdCard;

    @XmlElement(name = "USERJKK")
    private String userJkk;

    @XmlElement(name = "USERSMK")
    private String userSmk;

    @XmlElement(name = "USERYBK")
    private String userYbk;

    @XmlElement(name = "USERPARENTIDCARD")
    private String userParentIdCard;

    @XmlElement(name = "USERNHCARD")
    private String userNhCard;

    @XmlElement(name = "USERNAME")
    private String userName;

    @XmlElement(name = "USERGENDER")
    private String userGender;

    @XmlElement(name = "USERMOBILE")
    private String userMobile;

    @XmlElement(name = "USERBIRTHDAY")
    private String userBirthday;

    @XmlElement(name = "AGENTID")
    private String agentId;

    @XmlElement(name = "ORDERTIME")
    private String orderTime;
}
