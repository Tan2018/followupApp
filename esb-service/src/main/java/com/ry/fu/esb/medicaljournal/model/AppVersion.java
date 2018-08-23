package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/25 20:14
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_APP_VERSION")
public class AppVersion extends BaseModel {

    /**
     * ID
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 版本号
     */
    @Column(name = "VER_NO")
    private String verNo;

    /**
     * IOS下载地址
     */
    @Column(name = "IOS_URL")
    private String iosUrl;


    /**
     * 安卓下载地址
     */
    @Column(name = "ANDROID_URL")
    private String androidUrl;

    /**
     * 版本介绍
     */
    @Column(name = "DESCR")
    private String descr;

    /**
     * 版本编码
     */
    @Column(name = "VERCODE")
    private Integer verCode;

    /**
     * 版本编码
     */
    @Column(name = "HTML_VER")
    private String htmlVer;

    /**
     * 版本编码
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * 版本编码
     */
    @Column(name = "ipad_url")
    private String ipadUrl;

    /**
     * 客户端类型
     */
    @Column(name = "USE_TYPE")
    private String useType;

    /**
     * 跟新标识
     */
    @Column(name = "RENEW_FLAG")
    private String renewFlag;

    /**
     * 跟新标识
     */
    @Column(name = "STRONG_FLAG")
    private String strongFlag;

    private Long packageSize;
}
