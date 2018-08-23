package com.ry.fu.esb.jpush.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26 10:34
 * @description 解除审核项目表，主要用来保存解除审核的项目信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_RELIEVE_TO_EXAMINE_ITEM_CODE")
public class RelieveToExamineItemCode extends BaseModel{

    /**
     * 检验申请单id
     */
    @Column(name = "EXAMINE_REQUEST_ID")
    private Long examineRequestID;
    /**
     * 检验编码
     */
    @Column(name = "LIS_CODE")
    private String lisCode;
}
