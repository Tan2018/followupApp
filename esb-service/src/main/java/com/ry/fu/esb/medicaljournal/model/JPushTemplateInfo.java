package com.ry.fu.esb.medicaljournal.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板类
 *
 * @author ：Borehain
 * @Description ：模板表
 * @create ： 2017-04-10
 **/
public class JPushTemplateInfo implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 使用类型
     */
    private char useType;
    /**
     * url_scheme
     */
    private String url_scheme;
    /**
     * 模板类型id
     */
    private Long TemplateTypeId;
    /**
     * 编号
     */
    private String number;
    /**
     * 标题
     */
    private String title;
    /**
     *  通知类型
     */
    private Integer mode;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态
     */
    private char state;
    /**
     * 推送对象
     */
    private char pushObject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getUseType() {
        return useType;
    }

    public void setUseType(char useType) {
        this.useType = useType;
    }

    public String getUrl_scheme() {
        return url_scheme;
    }

    public void setUrl_scheme(String url_scheme) {
        this.url_scheme = url_scheme;
    }

    public Long getTemplateTypeId() {
        return TemplateTypeId;
    }

    public void setTemplateTypeId(Long templateTypeId) {
        TemplateTypeId = templateTypeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public char getPushObject() {
        return pushObject;
    }

    public void setPushObject(char pushObject) {
        this.pushObject = pushObject;
    }

}