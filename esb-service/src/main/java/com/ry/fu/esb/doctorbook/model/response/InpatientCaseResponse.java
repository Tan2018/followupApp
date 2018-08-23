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
 * @author ：Boven
 * @Description ：住院患者病案首页信息
 * @create ： 2018-01-15 16:33
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientCaseResponse implements Serializable{

    @XmlElement(name="RECORD")
    private List<InpatientCaseResponseRecord> inpatientCaseDetailResponse;




}
