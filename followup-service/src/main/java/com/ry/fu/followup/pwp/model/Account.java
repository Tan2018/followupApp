package com.ry.fu.followup.pwp.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/27 16:33
 * @description Account用户表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "PWP_ACCOUNT")
public class Account extends BaseModel {

    @Id
    @Column(name = "account_id")
    private Long accountId;

    private Date accountInvDate;

    private String accountEmail;

    private String accountStatus;

    private String accountNum;

    private String accountName;

    private String accountPostalcode;

    private String accountRemark;

    private Long accountSuperior;

    private String accountPassword;

    private String accountCode;

    private String accountType;

    private Long orgId;

    private String accountIdentityid;

    private Date accountCreattime;

    private Date accountMendtime;

    private String accountPhone;

    private String accountCreator;

    private String accountSex;

    private String accountMender;

    private String accountAddress;

    private Long accountDeleted;

    private String accountMobile;

    private Long accountPosition;

    @Transient
    private String orgName;
}
