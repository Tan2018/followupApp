package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
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
public class TestDetailsResponse implements Serializable{

    private static final long serialVersionUID = 967004244190356303L;
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
     *
     */

    private ArrayList<String> listArray;
    private ArrayList<String> typeArray;
    private ArrayList<String> checkPointArray;
    private ArrayList<String> pdfArray;

    @XmlElementWrapper(name="REPORTLIST")
    @XmlElement(name="REPORT")
    private List<TestDetailsResponseReportList> patientDetailsResponseReportList;

}
