package com.ry.fu.followup.doctorbook.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;


import javax.persistence.Table;
import java.util.Date;

/**
 * pdf模板表
 * Created by jackson on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "tp_template")
public class Template {

    @Id
    private Long templateVerId;
    private Long templateId;
    private Long typeId;
    private String name;
    private String content;
    private Long orgId;
    private Long userId;
    private Long createUser;
    private Long lastupdateUser;
    private String status;
    private Long synVersion;
    private Date validDate;




}
