package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Table(name = "M_GUIDE_DIAGNOSIS")
public class Department implements Serializable {

    private static final long serialVersionUID = 8997133010351930917L;
//    @Id
//    @Column(name = "id")
    private Long id;

    /**
     * 科室ID
     */
    private String deptId;

}
