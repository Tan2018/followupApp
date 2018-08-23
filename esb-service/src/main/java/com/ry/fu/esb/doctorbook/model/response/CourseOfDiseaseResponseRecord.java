package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 15:21 2018/1/23
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseOfDiseaseResponseRecord implements Serializable {

    private static final long serialVersionUID = -1100228606096596538L;
    /**
    *姓名
    */
    @XmlElement(name = "PATIENT_NAME")
    private String patientName;
    /**
    *性别(0表示男，1表示女)
    */
    @XmlElement(name = "PATIENT_SEX")
    private String patientSex;
    /**
    *床号
    */
    @XmlElement(name = "BEDNO")
    private String bedNo;
    /**
    *科室
    */
    @XmlElement(name = "DEPARTMENT")
    private String department;
    /**
    *住院号
    */
    @XmlElement(name = "IPSEQNOTEXT")
    private String ipSeqnoText;
    /**
    *病程 PDF路径
    */
    @XmlElement(name = "REPORT_URL")
    private String reportUrl;
    /**
    *病程记录时间
    */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "DURATION_TIME")
    private String durationTime;
    /**
     *病程时间数组
     */
    private ArrayList<String> aryDate;
}
