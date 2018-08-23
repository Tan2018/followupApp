package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Seaton
 * @version V1.0.0
 * @date 2018/5/31 11:43
 * @description 交接班记录表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientStatisticsVO extends BaseModel {


    private static final long serialVersionUID = -581943868116724236L;



    /**
     * 交班医生ID
     */
    private Long shiftDoctorId;

    /**
     * 医生姓名
     */
    private String shiftDoctorName;

    /**
     * 患者id
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 床位号
     */
    private String sickBedNo;

    /**
     * 交班时间
     */
    private Date shiftTime;


    /**
     * 交接状态 0-未接班，1-已接班
     */
    private String status;


    /**
     * 接班科室ID
     */
    private Long succeedDeptId;

    /**
     * 接班科室
     */
    private String succeedDeptName;


    /**
     * 接班医生ID
     */
    private Long succeedDoctorId;

    /**
     * 接班医生姓名
     */
    private String succeedDoctorName;


    /**
     * 接班时间
     */
    private Date succeedTime;

    /**
     * 附属交班医生的ID
     */
    private Long subShiftDoctorId;

    /**
     * 附属交班医生的名称
     */
    private String subShiftDoctorName;

    /**
     * 接班医生上级的ID
     */
    private Long supTakeDoctorId;

    /**
     * 接班医生上级的姓名
     */
    private String supTakeDoctorName;
}
