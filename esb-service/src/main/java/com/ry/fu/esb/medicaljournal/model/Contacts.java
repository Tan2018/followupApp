package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ：Boven
 * @Description ：就诊对象管理
 * @create ： 2018-03-29 14:25
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_CONTACTS")
public class Contacts implements Serializable{

    private static final long serialVersionUID = -3794317788090062990L;

    private Long id;
    /**
    *用户ID
    */
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /**
     * 病人ID
     */
    @Column(name = "PATIENT_ID")
    private Long patientId;
    /**
     *患者名字
     *//*
    private String patientName;
    *//**
     *性别
     *//*
    private String sex;
    *//**
     *身份证
     *//*
    private String IdCard;
    *//**
     *联系电话
     *//*
    private String phone;
    *//**
     *诊疗卡号
     *//*
    private String healthCard;*/
    /**
     *与父用户关系：0-本人、1-子女、2-朋友和其他
     */
    @Column(name = "RELATIONSHIP")
    private String relationship;
    /**
     *创建人
     */
    //private String createUser;
    /**
     *创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
    /**
     *类型：1-关联我的，2-我关联的
     */
   // private String type;
    /**
     *状态：1-绑定，2-已解除，3--等待通知，4-待审核
     *//*
    private String status;*/


}
