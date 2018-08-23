package com.ry.fu.esb.medicalpatron.model;


import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 14:30
 * @description 随医拍业务类型相关信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Table(name = "M_ACCESS_BUS_TYPE")
public class BusType implements Serializable {
    private static final long serialVersionUID = -2593532749980864508L;
    /**
     * ID
     */
    private Long id;

    /**
     * 业务编码
     */
    private String busCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
