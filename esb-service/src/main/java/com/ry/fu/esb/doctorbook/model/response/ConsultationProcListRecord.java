package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * @Author jane
 * @Date 2018/7/9
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ConsultationProcListRecord implements Serializable{
    private static final long serialVersionUID = 1117695810469176992L;

    /***
     * 会诊ID
     */
    @XmlElement(name="CONSULTATION_ID")
    private String consultationId;

    @XmlElement(name="PROCLIST")
    private List<ProcListResponse> proclist;

}
