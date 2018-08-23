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
public class PrimaryIndexQueryPatientListRequest implements Serializable {


    private static final long serialVersionUID = -5432016439223779332L;
    /**
     *主索引ID
     */
    @XmlElement(required = true, name = "MPI_ID")
    private String mpiId;
    /**
     *身份证件号码
     */
    @XmlElement(required = true, name = "IDENTITY_CARD_NO")
    private String identityCardNo;
    /**
     *患者id
     */
    @XmlElement(required = true, name = "PATIENT_ID")
    private String patientId;
    /**
     *就诊卡号
     */
    @XmlElement(required = true, name = "PATIENT_CARD_NO")
    private String patientCardNo;
    /**
     *患者姓名
     */
    @XmlElement(required = true, name = "PATIENT_NAME")
    private String patientName;
    /**
     *住院号
     */
    @XmlElement(required = true, name = "IPSEQNO")
    private String ipSeqNoText;
    /**
     *页数
     */
    @XmlElement(required = true, name = "PAGENUM")
    private String pageNum;
    /**
     *每页数量
     */
    @XmlElement(required = true, name = "PAGESIZE")
    private String pageSize="50";



    private String queryValue;
    private String requestFlag;







}
