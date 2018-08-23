package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: telly
 * @Description: 检验报告查询（第三方查询）
 * @Date: Create in 16:36 2018/1/11
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectionReportResponse implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -3413367568359570168L;
    /**
     *
     */
    private Map<String,String> reportMap;
    private String reportCount;
    @XmlElement(name="RECORD")
    private List<InspectionReportResponseRecode> inspectionReportResponseRecode;
}
