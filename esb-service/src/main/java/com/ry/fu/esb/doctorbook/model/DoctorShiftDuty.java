package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author Seaton
 * @version V1.0.0
 * @date 2018/5/31 11:43
 * @description 交接班医生信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorShiftDuty extends BaseModel {


    private static final long serialVersionUID = -1066533985654421737L;

    /**
     * 交班医生ID
      */
    private Long shiftDoctorId;

    /**
     * 交班医生姓名
     */
    private String shiftDoctorName;


    /**
     * 交班时间
     */
    private Date shiftTime;


    /**
     * 交接状态 0-未交班，1-已交班,2-已接班
     */
    @Column(name = "STATUS")
    private String status;


//    /**
//     * 接班科室ID
//     */
//    private Long succeedDeptId;
//
//    /**
//     * 接班科室
//     */
//    private String succeedDeptName;


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
