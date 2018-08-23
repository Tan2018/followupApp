package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.doctorbook.model.DepartmentsStatisticsInfo;
import com.ry.fu.esb.doctorbook.model.DepartmentsStatisticsInfoLog;
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
public class DepartmentsStatisticsLogReponse implements Serializable{
    private static final long serialVersionUID = 5908826601484678896L;

    @XmlElement(name="RECORD")
    private List<DepartmentsStatisticsInfoLog> handoverStatisticsInfoList;

    @XmlElement(name="TOTAL")
    private Integer total;
}
