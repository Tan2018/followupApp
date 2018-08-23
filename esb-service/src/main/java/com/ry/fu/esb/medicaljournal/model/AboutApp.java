package com.ry.fu.esb.medicaljournal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ry.fu.esb.common.response.BaseModel;
import com.ry.fu.esb.pwp.model.PwpUpload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/27 10:49
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_ABOUT_APP")
public class AboutApp extends BaseModel{
    /**
     * ID
     */
    @Id
   // @Column(name = "id")
    private Long id;

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

    @Transient
    private String imgUrl;

    @Transient
    private PwpUpload pwpUpload;





}
