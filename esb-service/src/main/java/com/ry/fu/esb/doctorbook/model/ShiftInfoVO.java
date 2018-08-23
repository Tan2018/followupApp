package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/30 11:43
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ShiftInfoVO extends BaseModel {
    private static final long serialVersionUID = -5335766245638038692L;


    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 交班医生ID
     */
    @Column(name = "SHIFT_DOCTOR")
    private Long shiftDoctor;



    /**
     * 交班医生名称
     */
    @Column(name = "SHIFT_DOCTOR_NAME")
    private String shiftDoctorName;


    /**
     * 交班时间
     */
    @Column(name = "SHIFT_TIME")
    private Date shiftTime;

    /**
     * 患者ID
     */
    @Column(name = "PATIENT_ID")
    private Long patientId;


    /**
     * 交接状态 0-未接班，1-已接班
     */
    //@Column(name = "ST_STATE")
    //private String shiftState;




    /**
     * 接班医生ID
     */
    @Column(name = "TAKE_DOCTOR")
    private Long takeDoctor;



    /**
     * 接班医生姓名
     */
    @Column(name = "TAKE_DOCTOR_NAME")
    private String takeDoctorName;


    /**
     * 接班时间
     */
    @Column(name = "TAKE_TIME")
    private Date takeTime;

    /**
     * 交接文字
     */
    @Column(name = "SHIFT_WORDS")
    private String shiftWords;


    /**
     * 附属交班医生的ID
     */
    @Column(name = "SUB_SHIFT_DOCTOR_ID")
    private Long subShiftDoctorId;

    /**
     * 附属交班医生的名称
     */
    @Column(name = "SUB_SHIFT_DOCTOR_NAME")
    private String subShiftDoctorName;

    /**
     * 接班医生上级的ID
     */
    @Column(name = "SUP_TAKE_DOCTOR_ID")
    private Long supTakeDoctorId;

    /**
     * 接班医生上级的姓名
     */
    @Column(name = "SUP_TAKE_DOCTOR_NAME")
    private String supTakeDoctorName;

    /**
     *患者姓名
     */
    private String patientName;

    /**
     *责任医师
     */
    private String responsibleDoctor;

    /**
     *性别：0-未知,1-男,2-女
     */
    private String sexFlag;


    /**
     *护理级别：0-一般护理,1-一级护理,2-二级护理,3-三级护理,4-特级护理
     */
    //private String nurseFlag;

    /**
     *床位号
     */
    private String sickBedNo;

    /**
     * 年纪
     */
    private String age;

    /**
     *住院次数
     */
    //private String ipTimes;


    /**
     * 入院天数
     */
    private  String inpatientDayStatus;

    /**
     * 交班状态0-未交班，1-已交班
     */
    private String shiftStatus = "0";

    /**
     * 接班状态0-未接班，1-已接班
     */
    private String takeStatus = "0";

    /**
     *初步诊断
     */
    private String  strDiagnosis;

    /**
     * 是否为我的患者
     * 只为兼容之前的版本
     */
    private String flag="1";

    /**
     * 住院号
     */
    private String ipSeqNoText;

    /**
     * 是否再次交班
     */
    private String shiftAgain;

    public String getShiftStatus() {
        if("1".equals(shiftAgain)){
            return "0";
        }else {
            return "1";
        }
    }
}
