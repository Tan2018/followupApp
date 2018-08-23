package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Created by samhuang on 2018/4/9.
 * @description 条件表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_GROUP_CONDITION_EXPRESSION")
public class GroupConditionexPression extends BaseModel {

    @Id
    Long id;

    String conditionStub;//条件说明

}
