package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 *@Author: telly
 *@Description: 长期医嘱集合
 *@Date: Create in 15:31 2018/1/25
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = false)
public class DoctorAdviceResponseRecodeLongdaList implements Serializable {

    private static final long serialVersionUID = -977161771131282931L;
    private Integer value;
    /**
     * 序号
     */
    @XmlElement(name = "NUM")
    private String num;
    /**
     * 医嘱状态：0-录入未核实,1-录入已核实,2-停嘱未核实,3-停嘱已核实,4-取消,5-部分重整医嘱未核实,6-全部重整未核实,7-改用法停医嘱
     */
    @XmlElement(name = "STATUS")
    private String status;
    /**
     * 长期医嘱
     */
    @XmlElement(name = "DANAME")
    private String daName;
    /**
     * 每次量
     */
    @XmlElement(name = "DOSAGEPERTIME")
    private String dosageperTime;
    /**
     * 频率
     */
    @XmlElement(name = "TAKINGMEDICINETIME")
    private String takingmedicineTime;
    /**
     * 用药途径
     */
    @XmlElement(name = "MEDICINEUSINGMETHOD")
    private String medicineusingMethod;
    /**
     * 首次次数
     */
    @XmlElement(name = "FIRSTDAYTIMES")
    private String firstdayTimes;
    /**
     * 末次次数
     */
    @XmlElement(name = "LASTDAYTIMES")
    private String lastdayTimes;
    /**
     * 起始时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "DASTARTDATETIME")
    private String dastartDateTime;


    private String dastartDate;

    private String dastartTime;
    /**
     * 停止时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "DASTOPDATETIME")
    private String dastopDateTime;

    private String dastopDate;

    private String dastopTime;
    /**
     * 开嘱医生
     */

    @XmlElement(name = "DASTARTDOCTOR")
    private String dastartDoctor;
    /**
     * 停嘱医生
     */
    @XmlElement(name = "DASTOPDOCTOR")
    private String dastopDoctor;
    /**
     * 备注
     */
    @XmlElement(name = "REMARK")
    private String remark;


}
