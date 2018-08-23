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
 * @create: 2018/6/9 15:14
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_CS_DATA")
public class CsData extends BaseModel{

    private static final long serialVersionUID = -3223836131884891683L;
    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 患者id
     */
    @Column(name = "PATIENT_ID")
    private Long patientId;
}
