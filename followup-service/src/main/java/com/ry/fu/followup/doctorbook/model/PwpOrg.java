package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * Created by samhuang on 2018/4/3.
 * @description 科室表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "PWP_ORG")
public class PwpOrg extends BaseModel {

    private Long orgId;
    private String orgName;
    private Long orgPid;
    private String orgShortName;
}
