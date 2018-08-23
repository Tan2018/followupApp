package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/31 17:06
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientRecordPagerRequest implements Serializable{
    private static final long serialVersionUID = -3480951071557264226L;

    /**
     * 业务类型 1-交接班,2-查房
     */
    private String type;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 科室ID
     */
    private String deptId;

    /**
     * 医生组ID
     */
    private String groupId;

    /**
     * 第几页
     */
    private Integer pageNumber;

    /**
     * 每页多少条数据
     */
    private Integer pageSize;
}
