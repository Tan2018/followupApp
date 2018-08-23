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
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PrimaryIndexQueryResponse implements Serializable {

    @XmlElement(name="RECORD")
    private  PrimaryIndexQueryRecord queryRecord;





}
