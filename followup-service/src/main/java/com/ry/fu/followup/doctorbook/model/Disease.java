package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Jam on 2017/12/7.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_DISEASE")
public class Disease extends BaseModel {

    @Id
    private Long id;

    private String name;

    private Long orgId;

    private String remark;

    private String icd;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;
}
