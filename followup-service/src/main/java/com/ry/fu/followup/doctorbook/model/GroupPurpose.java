package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Jam on 2017/12/8.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_GROUP_PURPOSE")
public class GroupPurpose extends BaseModel {

    @Id
    private Long id;

    private Long groupId;

    private Long purposeId;

}
