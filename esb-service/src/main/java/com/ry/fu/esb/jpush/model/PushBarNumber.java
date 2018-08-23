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
 * @description 危急值统计表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PUSH_BAR_NUMBER")
public class PushBarNumber extends BaseModel{

    /**
     * 统计id
     */
    @Id
    @Column(name = "ID")
    private Long id;
    /**
     * 用户别名
     */
    @Column(name="ALIAS")
    private String alias;
    /**
     * 推送通知类型：1：危急值通知 2：交接班通知 3：随访项目通知 4：会诊通知
     */
    @Column(name = "NOTICETYPE")
    private Integer noticeType;
}
