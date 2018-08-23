package com.ry.fu.followup.doctorbook.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.util.Date;
/**
 * Created by Leon on 2018/07/10
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "fu_file")
public class Fufile {

    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long createUser;
    private Date createTime;
    private String uploadType;
    private Date updateTime;
    private Long updateUser;
    private Long groupId;
    private String fieldName;
}
