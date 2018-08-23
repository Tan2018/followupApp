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
public class TemplateTypeResponse implements Serializable {
    private static final long serialVersionUID = -1956155222821661766L;

    /**
     * 分类id
     */
    @XmlElement(name = "ID")
    private String id;

    /**
     * 分类名称
     */
    @XmlElement(name = "NAME")
    private String name;

    /**
     * 创建时间
     */
    @XmlElement(name = "CREATE_TIME")
    private String createTime;


}
