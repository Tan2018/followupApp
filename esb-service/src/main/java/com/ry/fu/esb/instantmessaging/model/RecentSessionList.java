package com.ry.fu.esb.instantmessaging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ry.fu.esb.common.adapter.TimeAdapater;
import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_RECENT_SESSION_LIST")
public class RecentSessionList extends BaseModel{
    /**
     *  消息内容主键
     */
    @JsonIgnore//设置不返回
    @Column(name = "MCID")
    private Long mcId;

    /**
     * 发送者账号
     */
    @Column(name = "FROMACCOUNT")
    private String fromAccount;
    /**
     * 发送者昵称
     */
    @Column(name = "FROMNICK")
    private String fromNick;
    /**
     * 获取接收者账号
     */
    @Column(name = "TID")
    private String tid;
    /**
     * 发送类型
     */
    @JsonIgnore//设置不返回
    @Column(name = "MSGTYPE")
    private String msgType;

    /**
     * 消息发送时间
     */
    @Column(name = "MSGTIMESTAMP")
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//把返回的date类型显示是毫秒的问题格式化
    private Date msgTimeStamp;

    /**
     * 抄送消息类型
     */
    @JsonIgnore//设置不返回
    @Column(name = "EVENTTYPE")
    private String eventType;

    /**
     * 消息内容
     */
    @Column(name = "BODY")
    private String body;

    /**
     * 会话具体类型
     */
    @JsonIgnore//设置不返回
    @Column(name = "CONVTYPE")
    private String convType;
    /**
     * 医生或患者名字
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)//设置不序列化，不会保存到数据库中
    private String name;
}
