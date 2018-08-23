package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Author: telly
 * @Description: 检验报告查询（第三方查询）
 * @Date: Create in 16:36 2018/1/11
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectResponse implements Serializable{

    private static final long serialVersionUID = 3042062747492899522L;
    /**
     *
     */
    @XmlElement(name="RECORD")
    private ArrayList<InspectReportResponseRecord> inspectionReportResponseRecord;



}
