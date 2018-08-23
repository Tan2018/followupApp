package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/26 15:58
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_QUES_FEEDBACK")
public class QuesFeedback extends BaseModel{

    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 反馈人
     */
    @Column(name = "ACCOUNT_ID")
    private String accountId;

    /**
     * 版本号
     */
    @Column(name = "VER_NO")
    private String verNo;

    /**
     * USER_TYPE
     */
    @Column(name = "QUES")
    private String ques;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_DATE")
    private Date createDate;

    /**
     * 使用类型
     */
    @Column(name = "USER_TYPE")
    private String userType;
}
