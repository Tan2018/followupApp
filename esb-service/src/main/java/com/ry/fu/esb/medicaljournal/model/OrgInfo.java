package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 12:12
 * @description 门诊挂号科室，注意，不是医院的组织机构的科室，主要是挂号用
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_ORG")
public class OrgInfo extends BaseModel {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 院区id
     */
    @Column(name = "AREA_ID")
    private Long areaId;

    /**
     * 父ID
     */
    @Column(name = "PARENT_ID")
    private Long parentId;

    /**
     * ESB门诊科室
     */
    @Column(name = "ORG_ID")
    private Long orgId;

    /**
     * 科室名称
     */
    @Column(name = "ORG_NAME")
    private String orgName;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 科室地址
     */
    @Column(name = "OFFICE_ADDRESS")
    private String officeAddress;

    /**
     * 科室图片
     */
    @Column(name = "OFFICE_IMAGE")
    private String officeImage;

    /**
     * 科室电话
     */
    @Column(name = "OFFICE_TELL")
    private String officeTell;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

}
