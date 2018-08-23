package com.ry.fu.esb.jpush.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26 10:34
 * @description 解除审核表，主要用来保存解除审核记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_RELIEVE_TO_EXAMINE")
public class RelieveToExamine extends BaseModel {
    /**
     * 解除审核表id
     */
    @Id
    @Column(name = "ID")
    private Long id;
    /**
     * 检验申请单id
     */
    @Column(name = "EXAMINE_REQUEST_ID")
    private Long examineRequestId;
    /**
     * 状态描述
     */
    @Column(name = "STATRE_MARK")
    private String statreMark;
    /**
     * 操作时间
     */
    @Column(name = "OPTM")
    private Date optm;
    /**
     *
     */
    @Transient
    private List<RelieveToExamineItemCode> relieveToExamineItemCodes;
}
