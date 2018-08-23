package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * 挂号记录Record
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterdResponseRecord implements Serializable {

    private static final long serialVersionUID = -1100228606096596538L;
    /**
    *订单的创建时间
    */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(required = true,name = "ORDERCREATEDATE")
    private String orderCreateDate;
    /**
     *订单ID
     */
    @XmlElement(required = true,name = "ORDERID")
    private String orderId;
    /**
     *医生姓名
     */
    @XmlElement(required = true,name = "DOCTORNAME")
    private String doctorName;
    /**
     *订单所属科室
     */
    @XmlElement(required = true,name = "DEPARTMENT")
    private String department;
    /**
     *就诊日期
     */
    @XmlElement(required = true,name = "REGISTERDATE")
    private String registerDate;
    /**
     *上、下午ID
     */
    @XmlElement(name = "TIMEID")
    private String timeId;
    /**
     *就诊时段 上、下午
     */
    @XmlElement(name = "TIMENAME")
    private String timeName;
    /**
     *就诊时间段ID，具体到几点
     */
    @XmlElement(name = "TIMESEGMENTLISTID")
    private String timeSegmentListId;

    /**
     *就诊时间段，具体到几点
     */
    @XmlElement(name = "TIMESEGMENTLISTNAME")
    private String timeSegmentListName;
    /**
     *患者姓名
     */
    @XmlElement(name = "PATIENTNAME")
    private String patientName;
    /**
     *就诊地点
     */
    @XmlElement(name = "LOCATION")
    private String location;
    /**
     *挂号费
     */
    @XmlElement(name = "COST")
    private double cost;
    /**
     *接诊标志：0. 没有接诊 1.已接诊
     */
    @XmlElement(name = "DIAGNOSEFLAG")
    private String diagnoseFlag;
    /**
     *挂号状态标志：0. 挂号 1.退号 2.废号 3.重打
     */
    @XmlElement(name = "STATUSFLAG")
    private String statusFlag;
    /**
     *退款金额：
     */
    @XmlElement(name = "RETURNFEE")
    private String returnFee;
    /**
     *付款时间
     */
    @XmlElement(name = "PAYTIME")
    private String payTime;

    @XmlElement(name = "DEPARTMENTID")
    private String departmentId;

    //院区编码
    @XmlElement(name = "DISTRICTID")
    private String districtId;

    //院区名称
    @XmlElement(name = "DISTRICTNAME")
    private String districtName;
    //是否取号，1-已报道，0-未报道
    @XmlElement(name = "ISCHECKINFLAG")
    private String isCheckInFlag;

    private String treatNum;
    //挂号id
    @XmlElement(name = "REGISTERID")
    private String orderIdHis;
    //0-已挂号，1-已取消，2-已支付，3-已取号，4-已退费
    private String orderESBStatus;
    //：0-未支付、1-已支付、2-已过期、3-已取消、5-已退款
    @XmlElement(name = "ORDERFLAG")
    private String orderFlag;
}
