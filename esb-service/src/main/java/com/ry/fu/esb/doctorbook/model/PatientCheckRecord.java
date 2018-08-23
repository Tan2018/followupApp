package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/30 11:28
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PT_RECORD")
public class PatientCheckRecord extends BaseModel {
    private static final long serialVersionUID = -3481168323946459654L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEPT_ID")
    private String deptId;

    @Column(name = "GROUP_ID")
    private String groupId;

    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ST_WORD")
    private String shiftWord;

    @Column(name = "ST_VISIBLE")
    private String shiftVisible;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "DOCTOR_ID")
    private Long doctorId;

    @Column(name = "DOCTOR_NAME")
    private String doctorName;

    /**
     * 附属医生的ID
     */
    @Column(name = "SUB_DOCTOR_ID")
    private Long subDoctorId;

    /**
     * 附属医生的名称
     */
    @Column(name = "SUB_DOCTOR_NAME")
    private String subDoctorName;


    @Transient
    private List<Long> appointDoctors;

    @Transient
    private List<PatientRecordFile> patientRecordFiles;


}
