package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * 用户待缴费记录支付
 * @author ：Joker
 * @Description ：
 * @create ： 2018-4-12
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class PayTheFeesRespone implements Serializable {


    private static final long serialVersionUID = 4064242083895123764L;

    @XmlElement(name="RECORD")
    private List<PayTheFeesResponeRecord> record;
}
