package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.doctorbook.model.DepartmentsStatisticsInfo;
import com.ry.fu.esb.doctorbook.model.HandoverStatisticsInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentsStatisticsRecord implements Serializable{
    private static final long serialVersionUID = -8494183424921790758L;

    private List<DepartmentsStatisticsInfo> list;

    private String depName;



}
