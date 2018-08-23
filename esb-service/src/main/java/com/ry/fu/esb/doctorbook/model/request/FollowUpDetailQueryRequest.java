package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
/**
 * @author ：Leon
 * @Description ：随访详情查询封装类
 * @create ： 2018-06-01
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class FollowUpDetailQueryRequest implements Serializable {
    private static final long serializableId =  -3521450380712345671L;

    /**
     *诊疗卡号
     */
    @XmlElement(name="PATIENTCARDNO",required = true)
    private String patientCardNo;

    /**
     *住院号
     */
    @XmlElement(name="IPSEQNOTEXT",required = false)
    private String ipSeqNoText;

    /**
     * 排序标识,1:升序,2:降序
     */
    @XmlElement(name="SORT",required = false)
    private String sort;

    /**
     * 平台患者id(随访的cdrPatientId)
     */
    @XmlElement(name="PATIENTID",required = true)
    private String cdrPatientId;

    /**
     * 患者id(随访的cdrPatientId)
     */
    @XmlElement(name="FOLLOWPATIENTID",required = true)
    private Long patientId;
}
