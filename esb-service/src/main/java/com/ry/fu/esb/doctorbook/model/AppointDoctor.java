package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/30 13:22
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_AP_DOCTOR")
public class AppointDoctor extends BaseModel{
    private static final long serialVersionUID = -3223836131884891683L;
    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 交接班ID
     */
    @Column(name = "PC_ID")
    private Long patientRecordId;

    /**
     * 指定医生ID
     */
    @Column(name = "DOCTOR_ID")
    private Long doctorId;

    /**
     * 推送信息id
     */
    @Column(name = "hand_Inform_Id")
    private Long handInformId;

    @Transient
    private PtRecord ptRecord;
}
