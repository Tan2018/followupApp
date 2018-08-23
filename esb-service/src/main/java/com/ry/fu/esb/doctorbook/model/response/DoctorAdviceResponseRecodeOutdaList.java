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
 *@Description: 出院医嘱集合
 *@Date: Create in 15:31 2018/1/25
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(callSuper = false)
public class DoctorAdviceResponseRecodeOutdaList implements Serializable {

    private static final long serialVersionUID = 5874426702167555296L;
    /**
     * 序号
     */
    @XmlElement(name = "NUM")
    private String num;
    /**
     * 处方号
     */
    @XmlElement(name = "DAID")
    private String daId;

    private String daNum;
    /**
     * 物品名称
     */
    @XmlElement(name = "ITEMNAME")
    private String itemName;
    /**
     * 医嘱状态：0-录入未核实,1-录入已核实,2-停嘱未核实,3-停嘱已核实,4-取消,5-部分重整医嘱未核实,6-全部重整未核实,7-改用法停医嘱
     */
    @XmlElement(name = "STATUS")
    private String status;
    /**
     * 每次量
     */
    @XmlElement(name = "DOSAGEPERTIME")
    private String dosageperTime;
    /**
     * 用药途径
     */
    @XmlElement(name = "MEDICINEUSINGMETHOD")
    private String medicineusingMethod;
    /**
     * 用药时间
     */
    @XmlElement(name = "TAKINGMEDICINETIME")
    private String takingmedicineTime;
    /**
     * 嘱托
     */
    @XmlElement(name = "ENTRUST")
    private String entrust;
    /**
     * 开嘱时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "DADATETIME")
    private String dadateTime;
    /**
     * 医嘱类型
     */
    @XmlElement(name = "TYPE")
    private String type;

    @XmlElement(name = "DADATE")
    private String dadate;
    @XmlElement(name = "DATIME")
    private String daTime;

}
