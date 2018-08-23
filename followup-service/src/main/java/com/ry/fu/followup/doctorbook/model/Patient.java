package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by Jam on 2017/12/8.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_PATIENT")
public class Patient extends BaseModel {

    @Id
    private Long id;

    private String name;

    private Long height;
    private String bloodType;
    private String phone;
    private String email;
    private Integer sex;
    private Long weight;
    private Integer marriage;
    private String idCard;
    private String healthCard;
    private String concatAddr;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;
    private String hospitalNo;
    private String birthAddr;
    private Date birthday;
    private long cdrPatientId;
    @Transient
    private long groupRecordId;//项目入组id
    @Transient
    private Integer isEntrust;

    private Date nextFollowupTime; //下次开始随访时间
    private Date maxFollowupTime; //下次开始随访时间

    @Transient
    private Long age;
    @Transient
    private String status;
}
