package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：指定医生
 * @create ： 2018-03-12 11:32
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DesignatedDoctorsResponeRecord implements Serializable {


    private static final long serialVersionUID = 4220168171942711622L;
    /**
    *医生id
    */
    @XmlElement(name = "DOCTORID")
    private String doctorId;
    /**
    *医生姓名
    */
    @XmlElement(name = "DOCTORNAME")
    private String doctorName;


}
