package com.ry.fu.esb.medicaljournal.model.request;

import com.ry.fu.esb.common.adapter.DateAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorRegisterSourceRequestExtend implements Serializable{

    private static final long serialVersionUID = -3238861445331550275L;
    /**
     * 平台科室ID，科室代码，如果科室代码为“-2”则相当于查询参与外部预约挂号的所有门诊专科的科室信息
     */
    @XmlElement(name = "DEPTID")
    private List<String> deptIdList;
    /**
     * 平台医生ID，如果为空则获取某科室下所有医生的信息
     */
    @XmlElement(name = "DOCTORID")
    private String doctorId;
    /**
     *号源开始日期，
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "STARTDATE")
    private String startDate;
    /**
     *号源结束日期，
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "ENDDATE")
    private String endDate;
    /**
     * 查询排班范围标志
     * 0-只返回预约排班信息  1-返回预约排班信息和当前排班信息
     */
    @XmlElement(name = "TYPE")
    private String type ;

}
