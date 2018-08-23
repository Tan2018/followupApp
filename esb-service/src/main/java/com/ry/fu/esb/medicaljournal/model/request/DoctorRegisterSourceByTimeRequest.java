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

/**
 * @author ：Boven
 * @create ： 2018-02-28 14:58
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorRegisterSourceByTimeRequest implements Serializable{
    private static final long serialVersionUID = -3238861445331550275L;

    /**
     * 平台医生ID，如果为空则获取某科室下所有医生的信息
     */
    @XmlElement(required = false,name = "DOCTORID")
    private String doctorId;
    /**
     *出诊日期，格式：YYYY-MM-DD
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name = "REGDATE")
    private String regDate;

    /**
     *时段ID
     */
    @XmlElement(name = "DEPTIDANDTIMEID")
    private List<DeptIdAndTimeId> deptIdAndTimeIdList;

    /**
     * 查询排班范围标志
     * 0-只返回预约排班信息  1-返回预约排班信息和当前排班信息
     */
    @XmlElement(required = false,name = "TYPE")
    private String type;

}
