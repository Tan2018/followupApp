package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_CS_DATA")
public class HandoverStatisticsInfo extends BaseModel {

    private static final long serialVersionUID = -6892401280001292973L;

    /**
     * 住院号
     */
    @Column(name = "BED_NO")
    private Integer bedNo;

    /**
     * 患者姓名
     */
    @Column(name = "PATIENT_NAME")
    private String patientName;

    /**
     * 交班医生ID
     */
    @Column(name = "SHIFT_DOCTOR")
    private Integer shiftDoctor;

    /**
     * 接班医生名称
     */
    @Column(name = "CH_NAME")
    private String chName;

    /**
     * 接班医生ID
     */
    @Column(name = "TAKE_DOCTOR")
    private Integer takeDoctor;

    /**
     * 接班医生名称
     */
    @Column(name = "JH_NAME")
    private String jhName;

    /**
     * 交班时间
     */
    @Column(name = "SHIFT_TIME")
    private String shiftTime;

    /**
     * 接班时间
     */
    @Column(name = "TAKE_TIME")
    private String takeTime;

    /**
     * 科室名称
     */
    @Column(name = "ORG_NAME")
    private String orgName;
}
