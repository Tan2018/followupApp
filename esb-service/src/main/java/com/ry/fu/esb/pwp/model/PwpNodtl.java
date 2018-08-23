package com.ry.fu.esb.pwp.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author walker
 * @description pwp_nodtl序列表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "PWP_NODTL")
public class PwpNodtl extends BaseModel {

    private static final long serialVersionUID = -730477908765962075L;

    private String noid;

    private String prefix;

    private Integer noday;

    private Long nextid;

    private String postfix;

    private Integer noincrement;

    private Integer noyear;

    private Integer nomonth;

}
