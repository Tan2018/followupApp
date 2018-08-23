package com.ry.fu.esb.jpush.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26 10:34
 * @description 患者检验项目表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_INSPECTION_PROJECT")
public class CrisisProject extends BaseModel{
    /**
     * 项目id
     */
    @Id
    @Column(name = "ID")
    private Long id;
    /**
     * 危急值患者表id(外键)
     */
    @Column(name = "CRITICAL_ID")
    private Long criticalId;
    /**
     * 检验申请单id
     */
    @Column(name = "EXAMINE_REQUEST_ID")
    private Long examineRequestId;
    /**
     * 项目id
     */
    @Column(name = "ITEMID")
    private Long itemId;
    /**
     * 项目编号
     */
    @Column(name = "ITEMCODE")
    private String itemCode;
    /**
     * 项目名称
     */
    @Column(name = "ITEMNAME")
    private String itemName;
    /**
     * 项目结果
     */
    @Column(name = "RESULT")
    private String result;
    /**
     * 单位
     */
    @Column(name = "COMPANY")
    private String company;
    /**
     * 提示
     */
    @Column(name = "PROMPT")
    private String prompt;
    /**
     * 危急值参考范围
     */
    @Column(name = "RANGE")
    private String range;
    /**
     * 是否解除审核
     */
    @Column(name = "ISRELIEVE")
    private String isRelieve;
}
