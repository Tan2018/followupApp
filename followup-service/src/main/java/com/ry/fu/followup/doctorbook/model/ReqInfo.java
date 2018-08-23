package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * Created by Jam on 2017/11/30.
 *
 */
@Data
public class ReqInfo extends BaseModel {

    // 医生ID
    private Long doctorId;

    private Long transferDoctorId;
    // 每一页包含的信息条数
    private Long pageSize;
    // 页码
    private Long pageNumber;
    // 项目属性
    private Long status;
    // 项目ID
    private Long projectId;
    // 病人ID
    private Long patientId;
    // 流程实例ID
    private Long flowInstanceId;
    // 病人姓名
    private String patientName;
    // 计划ID
    private Long planId;
    //流程项目实例ID
    private Long flowProgramInstanceId;
    //答题列表
    private List<Answer> answers;
    //住院号
    private String hospNo;

    private String taskId;

    private String currentLevel;

    private String comment;  //项目审核意见

    //private Long[] patientIds;
    private String patientIds;

    private long groupRecordId;//病案入组id

    //查询请求类型，1-模糊搜索、2-定位搜索
    private String requestFlag;

}
