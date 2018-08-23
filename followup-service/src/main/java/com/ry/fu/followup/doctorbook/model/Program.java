package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Clob;
import java.util.Date;

/**
 * Created by Jam on 2017/12/11.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_PROGRAM")
public class Program extends BaseModel {

    @Id
    private Long id;

    private Long verId;
    private Long diseaseId;
    private Integer type;
    private String name;
    private Long templateTypeId;
    private Long sort;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;
    private Long fieldNum;
    private Long templateVerId;
    private Integer status;
    private Integer isbaseform;
    private String pdfPath;
    @Transient
    private Report report;

    private Integer flowStatus;

}
