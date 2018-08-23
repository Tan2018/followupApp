package com.ry.fu.esb.medicalpatron.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 查找患者信息请求实体详情类
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/17 9:49
 * @description
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class PatientDataRequest implements Serializable{

    private static final long serialVersionUID = 4405823088922226690L;

    /**
     * 科室ID
     */
    @XmlElement(required = true,name = "DEPTID")
    private String deptId;

    /**
     * 患者姓名
     */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;

    /**
     * 诊疗卡号
     */
    @XmlElement(name = "PATIENTCARDNO")
    private String patientCardNo;

    /**
     * 住院号
     */
    @XmlElement(name = "IPSEQNOTEXT")
    private String ipSeqNOText;
}
