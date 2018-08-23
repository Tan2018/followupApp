package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author ：Boven
 * @Description ：影像响应实体
 * @create ： 2018-03-12 11:09
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class GetImageRecord implements Serializable{


    private static final long serialVersionUID = 7337543342704392431L;
    /**
    *申请单号
    */

    @XmlElement(name = "FUNCTIONREQUESTID")

    private String functionId;

    /**
    *患者Id
    */
    @XmlElement(name = "PATIENTID")
    private String patientid;
    /**
    *诊疗卡号
    */
    @XmlElement(name = "PATIENT_CARDNO")
    private String patientCardNo;
    /**
    *住院次数
    */
    @XmlElement(name = "IPTIMES")
    private String iptimes;
    /**
    *患者姓名
    */
    @XmlElement(name = "PATIENT_NAME")
    private String patientName;


    /**
    *图片路径
    */
    @XmlElement(name = "IMAGEPATH")
    private String imagepath;

    /**
    *
    */

    @XmlElementWrapper(name = "SERIELIS")
    @XmlElement(name = "SERIS")

    private List<GetImageRecordSeris> serieList;

}
