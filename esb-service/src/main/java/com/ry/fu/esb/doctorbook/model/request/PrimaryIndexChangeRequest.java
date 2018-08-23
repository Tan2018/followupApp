package com.ry.fu.esb.doctorbook.model.request;


import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:主索引注册
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class PrimaryIndexChangeRequest implements Serializable {


    private static final long serialVersionUID = 2889253299930158099L;


    /**
     *开始时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(required = true, name = "START_TIME")
    private String startTime;
    /**
    *结束时间
    */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(required = true, name = "END_TIME")
    private String endTime;






}
