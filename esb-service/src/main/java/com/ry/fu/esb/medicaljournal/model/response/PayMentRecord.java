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
public class PayMentRecord implements Serializable{


    private static final long serialVersionUID = 8455896243522145781L;
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
    *就诊顺序号或者时间
    */
    @XmlElement(name = "INFOSEQ")
    private String infoSeq;
    /**
    *取号最大号数
    */
    @XmlElement(name = "QUEUESEQNO")
    private String queueSeqNo;
    /**
    *患者序号
    */
    private String number;
    /**
    *患者排队人数
    */
    private String queueNumber;
    /**
    *流水号
    */
    private String serialNumber;
    //挂号id
    @XmlElement(name = "REGISTERID")
    private String orderIdHis;


}
