package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
* @Author:Jane
* @Description:病种信息
* @Date: Created in 11:19 2018/1/24
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class CourseOfDiseaseResponse implements Serializable{

    private static final long serialVersionUID = 7799749417870547021L;

    @XmlElement(name="RECORD")
    private List<CourseOfDiseaseResponseRecord> courseOfDiseaseResponseRecode;
}
