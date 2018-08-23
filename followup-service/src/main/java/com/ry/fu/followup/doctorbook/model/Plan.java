package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by jackson on 2018/4/8.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_PLAN")
public class Plan extends BaseModel {
    @Id
    private Long id;

    private Integer busType; //所属类型：0-病种、1-组
    private Long flowTotal;  //流程总数
    private Long  busId;   //业务ID
    private Integer isDefault; //是否默认
    private String planName;   //计划名称
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;
}

