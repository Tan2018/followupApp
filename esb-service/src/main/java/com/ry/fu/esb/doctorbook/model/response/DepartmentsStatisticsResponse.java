package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.doctorbook.model.HandoverStatisticsInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class DepartmentsStatisticsResponse implements Serializable {
    private static final long serialVersionUID = -3301018014203053298L;

    @XmlElement(name="RECORD")
    private List<DepartmentsStatisticsRecord> handoverStatisticsInfoList;

    @XmlElement(name="TOTAL")
    private Integer total;
}
