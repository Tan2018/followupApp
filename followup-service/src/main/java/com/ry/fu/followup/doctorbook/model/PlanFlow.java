package com.ry.fu.followup.doctorbook.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_PLAN_FLOW")
public class PlanFlow {
    private Long followId;
    private Long planId;
    private Long verId;
    private String name;
    private String startFollowType;
    private Long time;
    private String unit;
    private Long reminderDate;
    private Long sort;
    private String status;
    private Long templateId;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;
    private Long minOffset;
    private Long maxOffset;
    private String describe;



}
