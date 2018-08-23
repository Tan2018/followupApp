package com.ry.fu.esb.medicaljournal.model;

import java.util.Date;

/**
 * 群发对象类
 * @author Borehain
 */
public class MassObjectInfo {
    /**
     * id
     */
    private Long id;
    /**
     * 模板id(外键)
     */
    private Long templateId;
    /**
     * 特定号码
     */
    private String givenNumber;
    /**
     * 发送时间
     */
    private Date pushTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getGivenNumber() {
        return givenNumber;
    }

    public void setGivenNumber(String givenNumber) {
        this.givenNumber = givenNumber;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }
}
