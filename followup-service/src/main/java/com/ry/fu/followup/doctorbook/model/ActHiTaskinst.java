package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Jam on 2017/12/8.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "act_hi_taskinst")
public class ActHiTaskinst extends BaseModel {
    //定义常量delete_reason_的处理值。
    public static final String WAIT = "等待审批";
    public static final String COMPLETED = "已审批";
    public static final String DELETED = "已删除";
    public static final String REJECT = "驳回";

    @Id
    private String id_;
    private String name_;
    private String owner_;
    private String form_key_;
    private String proc_def_id;
    private String assignee_;
    private Integer duration_;
    private Integer priority_;
    private String execution_id_;
    private String proc_inst_id_;
    private String parent_task_id_;
    private Date claim_time_;
    private Date end_time_;
    private Date due_date_;
    private Date start_time_;
    private String description_;
    private String task_def_key_;
    private String delete_reason_;
}
