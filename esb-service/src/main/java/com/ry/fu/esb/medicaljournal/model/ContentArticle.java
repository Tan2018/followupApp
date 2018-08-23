package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/12 17:35
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_ARTICLE")
public class ContentArticle extends BaseModel{

    /**
     * 标题
     */
    @Column(name = "TITLE")
    private String title;

    /**
     * 内容
     */
    @Column(name = "CONTENT")
    private String content;
}
