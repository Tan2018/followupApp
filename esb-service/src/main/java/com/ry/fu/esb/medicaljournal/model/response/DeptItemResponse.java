package com.ry.fu.esb.medicaljournal.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeptItemResponse implements Serializable{
    private static final long serialVersionUID = 2756678993015237881L;

    @XmlElement(name = "DEPTID")
    private Integer deptId;

    @XmlElement(name = "DEPTNAME")
    private String deptName;

    @XmlElement(name = "PARENTID")
    private Integer parentId;

    @XmlElement(name = "DESC")
    private String desc;

    @JsonProperty(value = "DistrictDeptId")
    @XmlElement(name = "DISTRICTDEPTID")
    private Integer districtDeptId;

    @XmlElement(name = "DISTRICTDEPTNAME")
    private String districtDeptName;

    @XmlElement(name = "SECONDPARENTID")
    private Integer secondParentId;

    @XmlElement(name = "SECONDPARENTDEPTNAME")
    private String secondParentDeptName;

    private List<DeptItemResponse> depts = new ArrayList<DeptItemResponse>();

    public void addDepts(DeptItemResponse subDept) {
        this.depts.add(subDept);
    }
}
