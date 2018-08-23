package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 获取指定医生列表
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-12 11:11
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class DesignatedDoctorsRequest implements Serializable {

    private static final long serialVersionUID = 7428498872968545635L;

    /**
    *科室id
    */
    @XmlElement(name = "DEPARTMENTID")
    private  String departmentId;
}
