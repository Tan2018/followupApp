package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_DE_DOCTOR")
public class DeDoctorInfo extends BaseModel {
    private static final long serialVersionUID = 6507139286847825084L;

    @Column(name = "ID")
    private Long id;

    @Column(name = "DOCTOR_ID")
    private Long doctorId;

    @Column(name = "DE_ID")
    private Long deId;
}
