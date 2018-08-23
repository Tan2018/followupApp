package com.ry.fu.esb.jpush.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class SendCriticalValuesHandlerRequest implements Serializable{

    private static final long serialVersionUID = 5124750108145730626L;
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
    @XmlElement(required = false, name = "PATIENTNAME")
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
     * 处理日期时间
     */
    @XmlElement(required = true, name = "DISPOSEDATETIME")
    private String disposeDatetime;
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
    @XmlElement(required = false, name = "DISPOSEDESCRIBE")
    private String disposeDescribe;
    /**
     *
     */
    @XmlElementWrapper(name="DISPOSERESULTS")
    @XmlElement(name="ITEM")
    private List<Disposeresults> disposeresults;
}