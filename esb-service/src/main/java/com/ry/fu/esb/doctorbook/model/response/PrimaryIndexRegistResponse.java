package com.ry.fu.esb.doctorbook.model.response;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:主索引注册
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class PrimaryIndexRegistResponse implements Serializable {


    private static final long serialVersionUID = 2183624599464831495L;
    @XmlElement(name="RECORD")
    private  PrimaryIndexRegistRecord registRecord;





}
