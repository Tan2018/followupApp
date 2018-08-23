package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class FollowUpDetailQuery implements Serializable {

    /**
     * 病人主索引
     */
    @XmlElement(name = "PATIENTID",required = true)
    private String patientId;
    /**
     *患者姓名
     */

    @XmlElement(name = "PATIENT_NAME",required = true)
    private String patientName;

    /**
     * 出生日期
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "BIRTHDAY",required = true)
    private String birthday;

    /**
     *住院科室ID(入住科室)
     */
    @XmlElement(name = "DEPARTMENTID",required = true)
    private String departmentId;

    /**
     *住院科室编码(入住科室)
     */
    @XmlElement(name = "DEPARTMENTNO",required = true)
    private String departmentNo;

    /**
     *住院科室名称(入住科室)
     */
    @XmlElement(name = "DEPARTMENTNAME",required = true)
    private String departmentName;

    /**
     *病情标志：
     0-其它,1-危,2-急,3-一般
     */
    @XmlElement(name = "ILLNESSSTATEFLAG",required = true)
    private String illnessstatelag;
    /**
     *入院科室ID
     */
    @XmlElement(name = "INDEPARTMENTID")
    private String indepartmentId;
    /**
     *入院科室编码
     */
    @XmlElement(name = "INDEPARTMENTNO")
    private String indepartmentNo;
    /**
     *入院科室名称
     */
    @XmlElement(name = "INDEPARTMENTNAME")
    private String indepartmentName;
    /**
     *入院时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "INDATETIME",required = true)
    private String indateTime;
    /**
     *住院信息ID
     */
    @XmlElement(name = "INPATIENTID",required = true)
    private String inpatientId;
    /**
     *无效标志：
     0-有效,1-无效
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "INVALIDFLAG",required = true)
    private String invalidFlag;
    /**
     *住院状态：
     0-入院登记,1-在院,2-转科,3-批准出院,4-出院返回,5-确认出院
     */
    @XmlElement(name = "IPFLAG",required = true)
    private String ipFlag;
    /**
     *住院流水号
     */
    @XmlElement(name = "IPSEQNO",required = true)
    private String ipseqNo; /**
     *住院号
     */
    @XmlElement(name = "IPSEQNOTEXT",required = true)
    private String ipseqNoText;
    /**
     *住院次数
     */
    @XmlElement(name = "IPTIMES",required = true)
    private String ipTimes;

    /**
     *护理级别：
     0-一般护理,1-一级护理,2-二级护理,3-三级护理,4-特级护理
     */
    @XmlElement(name = "NURSEFLAG",required = true)
    private String nurseFlag;

    /**
     *出院日期
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "OUTDATE",required = true)
    private String outdDate;

    /**
     *出院类型：
     0-出院,1-治愈,2-好转,3-未愈,4-死亡,5-其它,6-转科
     */
    @XmlElement(name = "OUTTYPEFLAG",required = true)
    private String outTypeFlag;

    /**
     *性别：
     0-未知,1-男,2-女
     */
    @XmlElement(name = "SEXFLAG",required = true)
    private String sexFlag;

    /**
     *床位ID
     */
    @XmlElement(name = "SICKBEDID",required = true)
    private String sickbedId;

    /**
     *床位号
     */
    @XmlElement(name = "SICKBEDNO",required = true)
    private String sickbedNo;


}
