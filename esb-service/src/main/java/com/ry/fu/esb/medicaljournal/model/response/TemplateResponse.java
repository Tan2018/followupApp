package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author jane
 * @Date 2018/7/19
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateResponse implements Serializable {

    private static final long serialVersionUID = 2360756062644206722L;
    /**
     * 模板id
     */
    @XmlElement(name = "ID")
    private String id;

    /**
     * 分类id
     */
    @XmlElement(name = "TYPE_ID")
    private String typeId;

    /**
     * 分类名称
     */
    @XmlElement(name="NAME")
    private String name;

    /**
     * 模板标题
     */
    @XmlElement(name = "TITLE")
    private String title;

    /**
     * 模板内容
     */
    @XmlElement(name = "CONTENT")
    private String content;

    /**
     * 创建时间
     */
    @XmlElement(name = "CREATE_TIME")
    private String createTime;

    /**
     * 状态 （1,'启用',2,'禁用')）
     */
    @XmlElement(name = "STATUS")
    private String status;

}
