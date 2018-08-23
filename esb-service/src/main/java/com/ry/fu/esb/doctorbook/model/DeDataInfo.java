package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author thankbin
 * @version V1.0.0
 * @date 2018/5/11 11:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_DE_DATA")
public class DeDataInfo extends BaseModel {

    private static final long serialVersionUID = 8135372464786183322L;

    /*
    * ID
    * */
    @Column(name = "ID")
    private Long id;

    /*
    * 科室id
    * */
    @Column(name = "ORG_ID")
    private Long orgId;

    /*
    * 交班医生id
    * */
    @Column(name = "SHIFT_DOCTOR")
    private Long shiftDoctor;

    /*
    * 接班医生id
    * */
    @Column(name = "TAKE_DOCTOR")
    private Long takeDoctor;

    /*
    * 交班状态
    * */
    @Column(name = "ST_STATE")
    private Integer stState;

    /*
   * 交班时间
   * */
    @Column(name = "SHIFT_TIME")
    private Date shiftTime;

    /*
    * 接班时间
    * */
    @Column(name = "TAKE_TIME")
    private Date takeTime;

    /*
    * 文字描述
    * */
    @Column(name = "DESCRIBE")
    private String describe;
}
