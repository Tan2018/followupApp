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
@Table(name = "M_MESSAGE_CONTENT")
public class MessageContent extends BaseModel{
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private Long id;
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
    @Column(name = "MSGTYPE")
    private String msgType;
    /**
     * 发送客户端类型
     */
    @Column(name = "FROMCLIENTTYPE")
    private String fromClientType;
    /**
     * 服务端生成的消息id
     */
    @Column(name = "MSGIDSERVER")
    private String msgidServer;
    /**
     * 消息发送时间
     */
    @Column(name = "MSGTIMESTAMP")
    private Date msgTimeStamp;
    /**
     * 抄送消息类型
     */
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
    @Column(name = "CONVTYPE")
    private String convType;
    /**
     * 客户端生成的消息id
     */
    @Column(name = "MSGIDCLIENT")
    private String msgidClient;
    /**
     * 重发标记：0不是重发，1是重发
     */
    @Column(name = "RESENDFLAG")
    private String resendFlag;
    /**
     * 音频的大小
     */
    @Column(name = "ATTACHSIZE")
    private Integer attachSize;
    /**
     * 音频的格式
     */
    @Column(name = "ATTACHEXT")
    private String attachExt;
    /**
     * 音频的时长
     */
    @Column(name = "ATTACHDUR")
    private Integer attachDur;
    /**
     * 音频的url
     */
    @Column(name = "ATTACHURL")
    private String attachUrl;
    /**
     * 音频的md5值
     */
    @Column(name = "ATTACHMD5")
    private String attachMd5;
}
