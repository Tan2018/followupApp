package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Seaton
 * @version V1.0.0
 * @date 2018/5/31 11:43
 * @description 科室交接班记录表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentShiftedStatisticsVO extends BaseModel {


    private static final long serialVersionUID = -9158844016340931005L;

    /**
     * 交班科室ID
     */
    private Long shiftDeptId;

    /**
     * 交班科室名称
     */
    private String shiftDeptName;

    /**
     * 科室下医生的交班信息
     */
    private List<DoctorShiftDuty> doctorsInfo;


//    /**
//     * 图片路径
//     */
//    private List<String> picPath;
//
//    /**
//     * 语音路径
//     */
//    private List<String> voicePath;


}
