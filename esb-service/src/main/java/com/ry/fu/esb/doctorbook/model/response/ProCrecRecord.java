package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/5 10:31
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ProCrecRecord {

    /**
     * 操作人ID
     */
    @XmlElement(required = true, name = "DOCTOR_ID")
    private String doctorId;

    /**
     * 操作人名称
     */
    @XmlElement(required = true, name = "DOCTOR_NAME")
    private String doctorName;

    /**
     * 操作节点
     */
    @XmlElement(required = true, name = "CURRENT_NODE")
    private String currentNode;

    /**
     * 操作内容
     */
    @XmlElement(required = true, name = "OPERRATION_CONTENT")
    private String operrationContent;

    /**
     * 操作时间
     */
    @XmlElement(required = true, name = "CREATE_TIME")
    private String createTime;

    /**
     * 操作科室ID
     */
    @XmlElement(required = true, name = "DEPARTMENT_ID")
    private String departmentId;

    /**
     * 操作科室名称
     */
    @XmlElement(required = true, name = "DEPARTMENT_NAME")
    private String departmentName;

    /**
     * 审核意见
     */
    @XmlElement(required = true, name = "OPTIONS")
    private String options;

}
