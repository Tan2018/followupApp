package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/6 14:45
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PT_RECORD")
public class PtRecord extends BaseModel {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 患者Id
     */
    @Column(name = "PATIENT_ID")
    private String patientId;

    /**
     * 患者Id
     */
    @Column(name = "DOCTOR_ID")
    private String doctorId;

    /**
     * 患者Id
     */
    @Column(name = "SUB_DOCTOR_ID")
    private String subDoctorId;
}
