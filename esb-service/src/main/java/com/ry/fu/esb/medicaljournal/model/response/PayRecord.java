package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-31 15:31
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PayRecord implements Serializable{


    private static final long serialVersionUID = -6746903637950554508L;
    /**
    *处理结果代码：0-成功
    */
    @XmlElement(name = "RESULTCODE")
    private String resultCode;
    /**
    *处理结果描述
    */
    @XmlElement(name = "RESULTDESC")
    private String resultDesc;
    /**
    *HIS缴费订单号
    */
    @XmlElement(name = "ORDERIDHIS")
    private String orderIdHIS;

/**
    *HIS缴费订单号
    */
    @XmlElement(name = "ORDERDESC")
    private String orderDesc;


}
