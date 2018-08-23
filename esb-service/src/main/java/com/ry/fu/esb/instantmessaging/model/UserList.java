package com.ry.fu.esb.instantmessaging.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯消息内容保存
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_NETEASE_CLOUD_LETTER_USER_LIST")
public class UserList extends BaseModel {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private Long id;
    /**
     * 网易云信用户账号
     */
    @Column(name = "USERACCID")
    private String userAccid;
    /**
     * 网易云信用户密码
     */
    @Column(name = "USERTOKEN")
    private String userToken;
    /**
     * 网易云信用户名称
     */
    @Column(name = "USERNAME")
    private String userName;
    /**
     * 网易云信用户创建时间
     */
    @Column(name = "CREATETIME")
    private Date createTime;
    /**
     * 网易云信用户修改时间
     */
    @Column(name = "UPDATETIME")
    private Date updateTime;
    /**
     * 标记环境
     */
    @Column(name = "SYMBOL")
    private String symbol;
}
