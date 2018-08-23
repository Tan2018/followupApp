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
* @Author:Boven
* @Description:医嘱
* @Date: Created in 11:11 2018/1/24
*/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorAdviceResponseRecode implements Serializable {
    private static final long serialVersionUID = -849318659699109198L;


    /**
     * 患者主索引
     */
    @XmlElement(name = "PATIENTID")
    private String patientId;
    /**
     * 患者姓名
     */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;
    /**
     *住院信息ID
     */
    @XmlElement(name = "INPATIENTID")
    private String inpatientId;
    /**
     *住院次数
     */
    @XmlElement(name = "IPTIMES")
    private String ipTimes;
    /**
     *住院号
     */
    @XmlElement(name = "IPSEQNOTEXT")
    private String ipSeqnoText;
    private Integer count;
    /**
     * 长期医嘱
     */
    @XmlElementWrapper(name = "LONGDALIST")
    @XmlElement(name="LONGDA")
    private List<DoctorAdviceResponseRecodeLongdaList> doctorAdviceResponseLongdaList;
    /**
     * 出院医嘱
     */
    @XmlElementWrapper(name = "OUTDALIST")
    @XmlElement(name="OUTDA")
    private List<DoctorAdviceResponseRecodeOutdaList> doctorAdviceResponseOutdaList;
    /**
     * 临时医嘱
     */
    @XmlElementWrapper(name = "TEMPDALIST")
    @XmlElement(name="TEMPDA")
    private List<DoctorAdviceResponseRecodeTempdaList> doctorAdviceResponseTempdaList;

}
