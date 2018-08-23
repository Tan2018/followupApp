package com.ry.fu.esb.pwp.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author walker
 * @description Account用户表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_ACCOUNT")
public class Account extends BaseModel {

    private static final long serialVersionUID = -730477908765962075L;

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

}
