package com.ry.fu.esb.doctorbook.model.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:主索引查询
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class PrimaryIndexQueryRequest implements Serializable {


    private static final long serialVersionUID = -71786234970581123L;
   /* *//**
    *主索引ID
    *//*
    @XmlElement(required = true, name = "MPI_ID")
    private Long mpiId;
    *//**
     *证件号
     *//*
    @XmlElement(name = "IDENTITY_CARD_NO")
    private String IdCardNo;
*/
    @XmlElement(name = "MPI_ID")
    private String mpiId;

    @XmlElement(name = "IDENTITY_CARD_NO")
    private String identityCardNo;





}
