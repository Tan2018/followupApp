package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 取号支付
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-31 15:28
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PayMentRespone implements Serializable {
    private static final long serialVersionUID = 4913664676309170704L;

    @XmlElement(name="RECORD")
    private PayMentRecord resultInfo;

}
