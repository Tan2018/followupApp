package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.jpush.model.CrisisProject;
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
public class InspectionProjectResponse implements Serializable {
    /**
     * 检验项目
     */
    @XmlElement(name = "ITEMSET")
    private String itemSet;
    /**
     * 申请医生
     */
    @XmlElement(name = "REQUESTDOCTOR")
    private String requestDoctor;
    /**
     * 申请科室
     */
    @XmlElement(name = "REQUESTDEPARTMENT")
    private String requestDepartment;
    /**
     * 申请时间
     */
    @XmlElement(name = "REQUESTTIME")
    private String requestTime;
    /**
     * 检查报告号
     */
    @XmlElement(name = "LISTNO")
    private String listNo;
    /**
     * 检验项目明细
     */
    @XmlElement(name="RECORD")
    private List<CrisisProject> crisisProjectList;
}
