package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/27 9:47
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class ConsultationDeptRequest  implements Serializable {

    /**
     * 会诊科室id
     */
    @XmlElement(required = false, name = "DEPARTMENT_ID")
    private String departmentId;

    /**
     * 会诊科室编码
     */
    @XmlElement(required = false, name = "DEPARTMENT_CODE")
    private String departmentCode;

    /**
     * 会诊科室名称
     */
    @XmlElement(required = false,name = "DEPARTMENT_NAME")
    private String departmentName;
}
