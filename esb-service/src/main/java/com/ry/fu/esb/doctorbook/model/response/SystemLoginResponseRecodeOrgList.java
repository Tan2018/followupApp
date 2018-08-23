package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：随访系统登录响应实体
 * @create ： 2018-02-05 17:10
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemLoginResponseRecodeOrgList implements Serializable{
    private static final long serialVersionUID = -1085196051434821776L;
    /**
     *科室ID
     */
    @XmlElement(name = "ORGID")
    private String orgId;
    /**
     *科室编码
     */
    @XmlElement(name = "ORGCODE")
    private String orgCode;
    /**
     *组ID
     */
    @XmlElement(name = "GROUPID")
    private String groupId;

    /**
     *科室名称
     */
    @XmlElement(name = "ORGNAME")
    private String orgName;
    /**
     *是否为默认科室（1默认）
     */
    @XmlElement(name = "ISDEFAULT")
    private String isDefault="1";




}
