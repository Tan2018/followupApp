package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_DE_DATA")
public class DepDoctor extends BaseModel {

    @Column(name="ORG_ID")
    private String orgId;

    @Column(name="ORG_NAME")
    private String orgName;
}
