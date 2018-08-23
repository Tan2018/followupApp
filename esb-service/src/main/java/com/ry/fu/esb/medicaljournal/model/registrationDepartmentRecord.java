package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joker
 * @version V1.0.0
 * @description 代缴费科室
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class registrationDepartmentRecord implements Serializable {


    private static final long serialVersionUID = 5051752816302463467L;


    /**
     * 医生名字
     */
    private String doctorName;

    /**
     * 科室名字
     */
    private String deptName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 就诊日期
     */
    private String visitTime;


    /**
     * 就诊时段
     */
    private String visitDate;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 就诊卡登记号
     */
    private String infoSeq;


}
