package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Map;

/**
 * Created by Jam on 2017/12/1.
 * @description Group组表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_GROUP")
public class Group extends BaseModel {

    @Id
    private Long id;
    @Transient
    private String project;

    private String descript;

    private Long diseaseId;

    private Long recordNo;

    private Integer review;

    private Integer status;

    private String express;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

    private String groupId;

    private Integer groupType;

    private Date endTime;

    private Date startTime;

    private String checkOpinion;

    private Integer manageType;

    private String processInstanceId;

    private String projectPur;

    private Date projectApprovalTime;//立项时间
    private Date plannedExecTime;//计划执行时间
    private String sponsor;//申办方
    private Integer internationalProject;//国际项目(0是，1否)
    private Integer multiCenter;//多中心(0是，1否)
    private Integer leadProject;//是否牵头项目(0是，1否)
    private Date approvertime;//审核时间
    private String pdfPath;//pdf路径

    @Transient
    private String taskId;  //任务id
    @Transient
    private String currentLevel;

    @Transient
    private Map<String, Object> info;
}
