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
public class HandoverStatisticsResponse implements Serializable {
    private static final long serialVersionUID = 2385628485858091663L;

    @XmlElement(name="RECORD")
    private List<HandoverStatisticsInfo> handoverStatisticsInfoList;

    @XmlElement(name="TOTAL")
    private Integer total;
}
