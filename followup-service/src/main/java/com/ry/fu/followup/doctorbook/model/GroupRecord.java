package com.ry.fu.followup.doctorbook.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Table;
import java.util.Date;

/**
 * create by Leon_zhang on 2018-07-17
 */
@Table(name = "FU_GROUP_RECORD")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupRecord {
    private Long id;//id
    private Long groupId;//入组id
    private String recordId;//病案id
    private String status;//入组状态
    private Date studyId;//随访id
    private Date inGroupTime;//入组时间
    private Date createTime;//创建时间
    private Long createUser;//创建人
    private String groupName;//组名
    private Long groupRespId;//组负责人id
    private String groupRespName;//组负责人名字
    private String remarks;//
    private Date quitday;//
    private String endReson;//
    private String otherReason;
    private Long transferDoctor;//转接医生

}
