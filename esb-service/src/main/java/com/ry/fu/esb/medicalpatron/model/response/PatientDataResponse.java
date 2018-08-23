package com.ry.fu.esb.medicalpatron.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * 根据条件查找患者信息响应实体类
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/17 9:50
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
public class PatientDataResponse implements Serializable{
    private static final long serialVersionUID = 3747965817366691063L;

    @XmlElement(name = "RECORD")
    private List<PatientDataResponseRecord> records;
}
