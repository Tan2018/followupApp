package com.ry.fu.followup.doctorbook.model;


import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Jam on 2018/3/30.
 * @description 随访报告表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "TP_TEMPLATE_REPORT")
public class Report extends BaseModel {

     @Id
     private Long id;
     private Long templateVer_id;
     private String reportStatus;
     private String content;//html
     private Long createUser;
     private Long lastupdateUser;
     private Date createTime;
     private Date lastupdateTime;
     private Long synVersion;


}
