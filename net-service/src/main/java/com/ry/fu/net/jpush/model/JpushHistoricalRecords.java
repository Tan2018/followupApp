package com.ry.fu.net.jpush.model;

import com.ry.fu.net.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Borehain
 * @version V1.0.0
 * @date 2018/3/30 13:22
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_HISTORICAL_RECORDS")
public class JpushHistoricalRecords extends BaseModel{
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private Long id;
    /**
     * 接收人
     */
    @Column(name = "RECIPIENT")
    private String recipient;
    /**
     * 发送平台
     */
    @Column(name = "ACCEPTANCE_PLATFORM")
    private String acceptancePlatform;
    /**
     * 发送标题
     */
    @Column(name = "TITLE")
    private String title;
    /**
     * 发送内容
     */
    @Column(name = "CONTENT")
    private String content;
    /**
     * 发送时间
     */
    @Column(name = "SENDING_TIME")
    private Date sendingTime;
    /**
     * 扩展字段
     */
    @Column(name = "EXTRAS")
    private String extras;
}
