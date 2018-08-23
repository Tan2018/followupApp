package com.ry.fu.esb.instantmessaging.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 单聊历史记录消息请求
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class PersonalHistoryInquiriesRequest implements Serializable{
    /**
     * 发送者accid
     */
    @XmlElement(name = "FROM")
    private String from;
    /**
     * 接收者accid
     */
    @XmlElement(name = "TO")
    private String to;
    /**
     * 开始时间ms(毫秒)
     */
    @XmlElement(name = "BEGINTIME")
    private String beginTime;
    /**
     * 结束时间ms(毫秒)
     */
    @XmlElement(name = "ENDTIME")
    private String endTime;
    /**
     * 本次查询的消息条数上限(最多100条),小于等于0，或者大于100，会提示参数错误
     */
    @XmlElement(name = "LIMIT")
    private int limit;
    /**
     * 1按时间正序排列，2按时间降序排列。其它返回参数414错误.默认是按降序排列
     */
    @XmlElement(name = "REVERSE")
    private int reverse;
    /**
     * 查询指定的多个消息类型
     * 示例： 0,1,2,3
     * 类型支持： 1:图片，2:语音，3:视频，4:地理位置，5:通知，6:文件，10:提示
     */
    @XmlElement(name = "TYPE")
    private String type;
}
