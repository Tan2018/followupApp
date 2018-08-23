package com.ry.fu.esb.medicaljournal.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板功能类型类
 *
 * @author ：Borehain
 * @Description ：模板表
 * @create ： 2017-04-10
 **/
public class JPushTemplateTypeInfo implements Serializable{
    /**
     * id
     */
    private Long id;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 使用类型
     */
    private char useType;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 推送类型
     */
    private char pushType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public char getUseType() {
        return useType;
    }

    public void setUseType(char useType) {
        this.useType = useType;
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

    public char getPushType() {
        return pushType;
    }

    public void setPushType(char pushType) {
        this.pushType = pushType;
    }

    @Override
    public String toString() {
        return "TemplateTypeInfo{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", useType=" + useType +
                ", createId=" + createId +
                ", createTime=" + createTime +
                ", pushType=" + pushType +
                '}';
    }
}
