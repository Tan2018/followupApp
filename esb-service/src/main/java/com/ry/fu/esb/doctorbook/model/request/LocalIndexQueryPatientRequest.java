package com.ry.fu.esb.doctorbook.model.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:本地主索引查询
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class LocalIndexQueryPatientRequest implements Serializable {


    private static final long serialVersionUID = 8911680375115313990L;
    /**
     *患者id
     */
    @XmlElement(required = true, name = "PATIENT_ID")
    private Long patientId;
    /**
     *返回类型0.患者ID、1.全部患者信息
     */
    @XmlElement(required = true, name = "RESULT_TYPE")
    private Long resultType;







}
