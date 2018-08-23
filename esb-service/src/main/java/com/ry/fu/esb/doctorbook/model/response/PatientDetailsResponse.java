package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: telly
 * @Description: 检查结果查询（影像结果和其他结果）
 * @Date: Create in 9:23 2018/1/15
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientDetailsResponse implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -1031817382528568197L;
    /**
     * 总报告数
     */
    @XmlElement(name = "TOTAL_REPORT")
    private String totalReport;

    /**
     * 总报告数
     */

    @XmlElement(name = "REPORTCONUT")
    private Map<String,Integer> reportConut;
    /**
     *报告标记 0.没有数据 1.有数据
     */

    @XmlElementWrapper(name="REPORTLIST")
    @XmlElement(name="REPORT")
    private List<PatientDetailsResponseReportList> patientDetailsResponseReportList;

}
