package com.ry.fu.esb.instantmessaging.model.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 网易云信
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
public class UinfosResponse implements Serializable{

    /**
     * 医生名字
     */
    @XmlElement(name="CH_NAME")
    private String ch_name;
    /**
     * 医生职称
     */
    @XmlElement(name="PROFESS_NAME")
    private String profess_name;

    /**
     * 医生职称
     */
    @XmlElement(name="HEAD_IMG")
    private String head_img;

    /**
     * 医生职称
     */
    @XmlElement(name="DOCTOR_ID")
    private String doctor_id;
}
