package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：医生手册影像请求
 * @create ： 2018-03-12 10:40
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class GetImageRequest implements Serializable{

    private static final long serialVersionUID = -3658081603059810804L;
    /**
    *申请单id
    */
    @XmlElement(name = "FUNCTIONREQUESTFORPACSID")
    private String funcId;

    /**
    *患者id
    */
    @XmlElement(name = "PATIENTID")
    private String patientId;
    /**
    * 版本号：默认空，1、分组格式
    */
    @XmlElement(name = "VERNO")
    private String verNo;

}
