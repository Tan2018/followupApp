package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;


/**
*挂号记录
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_REGISTRATION")
public class RegisteredRecord extends BaseModel {



    /**
     * 院区
     */
    @Column(name = "HOSPITAL_NAME")
    private String hospitalName;


    /**
     *科室ID
     */
    @Column(name = "DEPT_ID")
    private String deptId;

    /**
     *订单所属科室
     */
    private String deptName;

    /**
     *医生ID
     */
    private String doctorId;

    /**
     * 医生名字
     */
    private String doctorName;


    /**
     * accountId
     */
    private Long accountId;

    /**
     *就诊对象（患者）ID
     */
    @Column(name = "PATIENT_ID")
    private String patientId;

    /**
     * 就诊人
     */
    private String patientName;

    /**
     * esbPatientId
     */
    private Long esbPatientId;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 诊疗卡号
     */
    private String healthCardNo;

    /**
     *挂号订单创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     *候诊时间
     */
    @Column(name = "WAITTIME")
    private Date waitTime;
    /**
     *候诊科室
     */
    @Column(name = "DIAGNOSEROOM")
    private String diagnoseRoom;

    /**
     *就诊时段
     */
    @Column(name = "VISIT_TIME")
    private String visitTime;
    /**
     *就诊日期
     */
    @Column(name = "VISIT_DATE")
    private Date visitDate;

    /**
     *挂号费
     */
    private Long totalFee;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     *His订单ID( 挂号His流水号)
     */
    @Column(name = "HIS_ORDER_ID")
    private String hisOrderId;

    /**
     *订单状态
     */
    private String orderStatus;

    /**
     *医院流水号
     */
    private String serialNumber;

    /**
     *排队序号
     */
    private String lineUpNumber;

    /**
     *HIS挂号ID
     */
   /* @Column(name = "HIS_REG_NO")
    private String hisRegNo;*/

    /**
     *挂号单CODE
     */
   /* @Column(name = "OUTPATIENT_CODE")
    private String outPatientCode;*/

    /**
     *挂号类型：0-不加号，1-加号
     */
    @Column(name = "REG_TYPE")
    private String regType;
    /**
     *挂号来源：0-微信、1-安卓、2-IOS
     */
    @Column(name = "APP_ID")
    private String appId;

    private String infoSeq;

    public String getOrderStatus() {
        //对前端显示超时取消,后台保持可支付状态
        if(Constants.ORDER_NO_PAY_STATUS.equals(orderStatus) && DateUtils.addMinutes(createTime,Constants.ORDER_OVER_TIME) .before (new Date())){
            return Constants.ORDER_EXPIRED_STATUS;
        }
        return orderStatus;
    }


/**
     *
     */
    /*@Column(name = "QR_CODE")
    private String qrCode;*/



//    /**
//     *上、下午ID
//     */
//    @XmlElement(name = "TIMEID")
//    private String timeId;
//    /**
//     *就诊时段 上、下午
//     */
//    @XmlElement(name = "TIMENAME")
//    private String timeName;
//    /**
//     *就诊时间段ID，具体到几点
//     */
//    @XmlElement(name = "TIMESEGMENTLISTID")
//    private String timeSegmentListId;
//
//    /**
//     *就诊时间段，具体到几点
//     */
//    @XmlElement(name = "TIMESEGMENTLISTNAME")
//    private String timeSegmentListName;
//    /**
//     *患者姓名
//     */
//    @XmlElement(name = "PATIENTNAME")
//    private String patientName;
//    /**
//     *就诊科室
//     */
//    @XmlElement(name = "LOCATION")
//    private String location;
//    /**
//     *挂号费
//     */
//    @XmlElement(name = "COST")
//    private double cost;
//    /**
//     *接诊标志：0. 没有接诊 1.已接诊
//     */
//    @XmlElement(name = "DIAGNOSEFLAG")
//    private String diagnoseFlag;
//    /**
//     *挂号状态标志：0. 挂号 1.退号 2.废话 3.重打
//     */
//    @XmlElement(name = "STATUSFLAG")
//    private String statusFlag;
//    /**
//     *退款金额：
//     */
//    @XmlElement(name = "RETURNFEE")
//    private String returnFee;
//    /**
//     *付款时间
//     */
//    @XmlElement(name = "PAYTIME")
//    private String payTime;
//
//    @XmlElement(name = "DEPARTMENTID")
//    private String departmentId;
//
//    //院区编码
//    @XmlElement(name = "DISTRICTID")
//    private String districtId;
//
//    //院区名称
//    @XmlElement(name = "DISTRICTNAME")
//    private String districtName;
//    //是否取号，1-已报道，0-未报道
//    @XmlElement(name = "ISCHECKINFLAG")
//    private String isCheckInFlag;
//
//    private String treatNum;
//
//    //0-已挂号，1-已取消，2-已支付，3-已取号，4-已退费
//    private String orderESBStatus;
//    //：0-未支付、1-已支付、2-已过期、3-已取消、5-已退款
//    @XmlElement(name = "ORDERFLAG")
//    private String orderFlag;

}
