package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterItemResponse implements Serializable{
    private static final long serialVersionUID = 2756678993015237881L;

    @XmlElement(name = "RESULTCODE")
    private String resultCode;

    @XmlElement(name = "RESULTDESC")
    private String resultDesc;

    @XmlElement(name = "ORDERIDHIS")
    private String orderidHis;

    //返回给前端用的外部订单ID
    private String orderId;

    @XmlElement(name = "USERFLAG")
    private String userFlag;

    @XmlElement(name = "WAITTIME")
    private String waitTime;

    @XmlElement(name = "DIAGNOSEROOMNAME")
    private String diagnoseRoomName;
}
