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
 * @Description ：科室号源请求
 * @create ： 2018-02-27 16:15
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptRegInfoRequest implements Serializable {

    private static final long serialVersionUID = -5068230710641526479L;
    /**
     *平台科室代码
     */
    @XmlElement(required = true, name = "DEPTID")
    private List<String> deptIdList;

    @XmlElement(required = true, name = "DOCTORID")
    private String doctorId;
    /**
     *号源开始日期
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(required = true, name = "STARTDATE")
    private String startDate;
    /**
     *号源结束日期
     */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(required = true, name = "ENDDATE")
    private String endDate;
    /**
     *查询排班范围标志 0-只返回预约排班信息 1-返回预约排班信息和当前排班信息
     */
    @XmlElement(name = "TYPE")
    private String type="1";

}
