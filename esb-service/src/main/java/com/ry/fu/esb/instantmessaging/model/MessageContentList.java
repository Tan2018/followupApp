package com.ry.fu.esb.instantmessaging.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageContentList extends BaseModel{

    /**
     * 发送者账号
     */
    private String fromAccount;
    /**
     * 获取接收者账号
     */
    private String tid;
    /**
     * 消息内容
     */
    private String body;
    /**
     * 消息发送时间
     */
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//把返回的date类型显示是毫秒的问题格式化
    private Date msgTimeStamp;
    /**
     * 发送类型
     */
    private String msgType;
    /**
     * 客户端生成的消息id
     */
    private String msgIdClient;
    /**
     * 服务端生成的消息id
     */
    private String msgIdServer;
    /**
     * 音频的大小
     */
    private Integer attachSize;
    /**
     * 音频的格式
     */
    private String attachExt;
    /**
     * 音频的时长
     */
    private Integer attachDur;
    /**
     * 音频的url
     */
    private String attachUrl;
    /**
     * 音频的md5值
     */
    private String attachMd5;
}
