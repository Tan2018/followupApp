package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/19 15:09
 * @description
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorInfoByPageRequest implements Serializable{
    private static final long serialVersionUID = -4958262899055574572L;

    /**
     * 查询值
     */
    @XmlElement(name = "CONTENT")
    private String content;

    /**
     * 每页多少条数据
     */
    @XmlElement(name = "PAGESIZE")
    private String pageSize;

    /**
     * 第几页
     */
    @XmlElement(name = "PAGENUM")
    private String pageNum;
}
