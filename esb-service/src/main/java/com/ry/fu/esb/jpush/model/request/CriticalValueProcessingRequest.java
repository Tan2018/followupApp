package com.ry.fu.esb.jpush.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值请求参数
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class CriticalValueProcessingRequest implements Serializable {

    /**
     * 检验标本号
     */
    @XmlElement(required = true, name = "LISLABLENO")
    private Long lisLableNo;
    /**
     * 检验申请单ID
     */
    @XmlElement(required = false, name = "EXAMINEREQUESTID")
    private Long examineRequestId;
    /**
     * 患者ID
     */
    @XmlElement(required = false, name = "PATIENTID")
    private String patientId;
    /**
     * 患者姓名
     */
    @XmlElement(required = false, name = "patientName")
    private String patientName;
    /**
     * 处理途径：1、电子病历；2、移动平台；3、LIS系统
     */
    @XmlElement(required = true, name = "DISPOSEWAY")
    private Long disposeWAY;
    /**
     * 处理人工号
     */
    @XmlElement(required = true, name = "DISPOSEEMPLOYEENO")
    private String disposeEmployeeNo;
    /**
     * 处理人姓名
     */
    @XmlElement(required = true, name = "DISPOSEEMPLOYEENAME")
    private String disposeEmployeeName;
    /**
     * 处理类型编码：默认99
     */
    @XmlElement(required = true, name = "DISPOSETYPECODE")
    private String disposeTypeCode;
    /**
     * 处理类型描述
     */
    @XmlElement(required = true, name = "DISPOSETYPENAME")
    private String disposeTypeName;
    /**
     * 处理备注
     */
    @XmlElement(required = true, name = "DISPOSEDESCRIBE")
    private String disposeDescribe;
    /**
     * 项目id
     */
    @XmlElement(required = true, name = "ITEMID")
    private Long itemId;
    /**
     * 项目编码
     */
    @XmlElement(required = true, name = "ITEMCODE")
    private String itemCode;
    /**
     * 项目名称
     */
    @XmlElement(required = true, name = "ITEMNAME")
    private String itemName;

    /**
     * 危急值接收时间
     */
    @XmlElement(required = true, name = "CRISISTIME")
    private String crisisTime;
}
