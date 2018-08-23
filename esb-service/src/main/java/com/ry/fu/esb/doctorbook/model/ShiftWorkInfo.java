package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/30 11:43
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_CS_DATA")
public class ShiftWorkInfo extends BaseModel {
    private static final long serialVersionUID = -5335766245638038692L;
   /* *//**
     * id
     *//*
    @Id
    @Column(name = "ID")
    private Long id;

    *//**
     * 患者id
     *//*
    @Column(name = "PATIENT_ID")
    private Long patientId;

    *//**
     * 交班状态 0-已交班，1-已接班
     *//*
    @Column(name = "ST_STATE")
    private String shiftState;

    *//**
     * 交班医生ID
     *//*
    @Column(name = "TAKE_DOCTOR")
    private Long takeDoctor;

    *//**
     * 交班医生
     *//*
    @Column(name = "SHIFT_DOCTOR")
    private Long shiftDoctor;

    *//**
     * 交班时间
     *//*
    @Column(name = "SHIFT_TIME")
    private Date shiftTime;

    *//**
     * 接班时间
     *//*
    @Column(name = "TAKE_TIME")
    private Date takeTime;*/

    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 交班科室ID
     */
    @Column(name = "SHIFT_DEPT_ID")
    private Long shiftDeptId;

    /**
     * 交班科室名称
     */
    @Column(name = "SHIFT_DEPT_NAME")
    private String shiftDeptName;


    /**
     * 交班组别
     */
//    @Column(name = "SHIFT_GROUP")
//    private Long shiftGroup;

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
     * HOSPITAL_PATIENT表主键
     */
    @Column(name = "HOSPITAL_PATIENT_ID")
    private Long hospitalPatientId;

    /**
     * 交接状态 0-未接班，1-已接班
     */
    @Column(name = "ST_STATE")
    private String shiftState;

    /**
     * 可见范围:1-本科室可见,2-本组可见,3-其他科室可见
     */
    @Column(name = "SHIFT_VISIBLE")
    private String shiftVisible;

    /**
     * 接班科室ID
     */
    @Column(name = "TAKE_DEPT_ID")
    private Long takeDeptId;

    /**
     * 接班科室名称
     */
    @Column(name = "TAKE_DEPT_NAME")
    private String takeDeptName;


    /**
     * 接班组别
     */
    /*@Column(name = "SUCCEED_GROUP")
    private Long succeedGroup;*/

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
     * 是否再次交班
     */
    @Column(name = "SHIFT_AGAIN")
    private String shiftAgain;

    /**
     * 交接类型:交班类型:1-单个患者交班,2-整个科室交班
     */
    @Column(name = "SHIFT_FLAG")
    private String shiftFlag;

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
     *组别ID
     */
    @Column(name = "SHIFT_GROUP_ID")
    private String shiftGroupId;
}
