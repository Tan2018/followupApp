package com.ry.fu.esb.jpush.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26 10:34
 * @description 危急值处理表，主要用来保存危急值处理记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_CRITICAL_PROCESSING_LIST")
public class CriticalValueProcessing extends BaseModel{
    /**
     * id
     */
    @Id
    @Column(name = "ID")
    private Long id;
    /**
     * 检验标本号
     */
    @Column(name = "LISLABLENO")
    private Long lisLableNo;
    /**
     * 检验申请单ID
     */
    @Column(name = "EXAMINEREQUESTID")
    private Long examineRequestId;
    /**
     * 患者ID
     */
    @Column(name = "PATIENTID")
    private String patientId;
    /**
     * 患者姓名
     */
    @Column(name = "PATIENTNAME")
    private String patientName;
    /**
     * 处理途径：1、电子病历；2、移动平台；3、LIS系统
     */
    @Column(name = "DISPOSEWAY")
    private Long disposeWAY;
    /**
     * 处理人工号
     */
    @Column(name = "DISPOSEEMPLOYEENO")
    private String disposeEmployeeNo;
    /**
     * 处理人姓名
     */
    @Column(name = "DISPOSEEMPLOYEENAME")
    private String disposeEmployeeName;
    /**
     * 处理日期时间
     */
    @Column(name = "DISPOSEDATETIME")
    private Date disposeDatetime;
    /**
     * 处理类型编码：默认99
     */
    @Column(name = "DISPOSETYPECODE")
    private String disposeTypeCode;
    /**
     * 处理类型描述
     */
    @Column(name = "DISPOSETYPENAME")
    private String disposeTypeName;
    /**
     * 处理备注
     */
    @Column(name = "DISPOSEDESCRIBE")
    private String disposeDescribe;
    /**
     * 项目id
     */
    @Column(name = "ITEMID")
    private Long itemId;
    /**
     * 项目编码
     */
    @Column(name = "ITEMCODE")
    private String itemCode;
    /**
     * 项目名称
     */
    @Column(name = "ITEMNAME")
    private String itemName;
    /**
     * 处理时长(分钟)
     */
    @Column(name = "DISPOSEMINUTES")
    private Long disposeMinutes;
}
