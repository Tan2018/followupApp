package com.ry.fu.esb.jpush.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientInformationsRequest implements Serializable {

    private static final long serialVersionUID = -2801035972989011689L;

    @XmlElement(required = true, name = "DOCTORCODE")
    private String personId;

    @XmlElement(required = true, name = "WHETHERDEALWITH")
    private String whetherDealWith;

    @XmlElement(required = true, name = "PAGENUM")
    private Integer pageNum;

    @XmlElement(required = true, name = "PAGESIZE")
    private Integer pageSize;
}
