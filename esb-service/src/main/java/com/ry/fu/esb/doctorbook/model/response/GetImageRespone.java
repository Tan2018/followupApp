package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 *
 * @author ：Boven
 * @Description ：提交信息
 * @create ： 2018-03-12 11:09
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class GetImageRespone implements Serializable{


    private static final long serialVersionUID = -4751243935561876258L;

    @XmlElement(name="RECORD")
    private GetImageRecord imageListRecord;

}
