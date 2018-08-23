package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by jackson on 2018/4/9.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_QUESTIONNAIRE")
public class Questionnaire extends BaseModel {
    @Id
    private Long id;

    private Long groupRecordId; //病案入组ID
    private Long satisfation;//随访满意度
    private Integer status; //状态
    private Long completeFlow;  //完成流程个数
    private Long  flowTotal;   //流程总数
    private Integer connected; //是否已联系
    private String name;   //问卷名称
    private Date createTime;
    private Date startTime;
}
