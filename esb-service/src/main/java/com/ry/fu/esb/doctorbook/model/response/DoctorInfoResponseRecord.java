package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/31 13:46
 * @description
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorInfoResponseRecord implements Serializable {
    private static final long serialVersionUID = -754913145647601996L;

    @XmlElement(name = "DOCTORID")
    private String doctorId;

    @XmlElement(name = "DOCTORNAME")
    private String doctorName;

    @XmlElement(name = "PDOCTORID")
    private String pDoctorId;

    @XmlElement(name = "PDOCTORNAME")
    private String pDoctorName;

    /**
     * 平台人员编码(医院工号)
     */
    @XmlElement(name = "IP_STAFF_CODE")
    private String hipStaffCode;

    /**
     * 医疗服务人员ID
     */
    @XmlElement(name = "ACCOUNT_ID")
    private String accountId;

    /**
     * 手机号码
     */
    @XmlElement(name = "MOBILE")
    private String mobile;
    /**
     * 联系电话
     */
    @XmlElement(name = "TELEPHONE")
    private String telephone;
}
