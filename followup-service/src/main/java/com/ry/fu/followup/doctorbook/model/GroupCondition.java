package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Created by Jam on 2017/12/7.
 *
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_GROUP_CONDITION")
public class GroupCondition extends BaseModel {

    @Id
    private Long id;

    private Long groupId;

    private String minValue;

    private String maxValue;

    private String expression;

}
