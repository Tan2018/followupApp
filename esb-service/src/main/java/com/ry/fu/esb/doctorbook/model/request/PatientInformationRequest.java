package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/31 10:11
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientInformationRequest implements Serializable {
    private static final long serialVersionUID = 2705138470183495682L;
    /**
     *科室ID
     */
    private String departmentId;

    /**
     *组别ID
     */
    private String groupId;

    /**
     *医生工号（建议以此查询）
     */
    private String doctorCode;

    /**
     *医生Id
     */
    private String doctorId;

    /**
     *附属医生Id
     */
    private String subShiftDoctorCode;

    /**
     *患者类型（1表示本科室患者，2表示本组患者，3表示我的住院患者,4表示其他科室住院患者）。默认本科室
     */
    private String patientType;

    /**
     * 查询类型（0.交班，1.接班）
     */
    private String queryType;
}
