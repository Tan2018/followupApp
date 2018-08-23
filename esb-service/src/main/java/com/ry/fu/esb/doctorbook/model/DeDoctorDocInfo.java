package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_DE_DOCTOR")
public class DeDoctorDocInfo  extends BaseModel {
    private static final long serialVersionUID = 2725437190987066695L;

    @Column(name = "DE_ID")
    private Long deId;

    @Column(name = "CH_NAME")
    private String chName;

    @Column(name = "DOCTOR_ID")
    private Long doctorId;
}
