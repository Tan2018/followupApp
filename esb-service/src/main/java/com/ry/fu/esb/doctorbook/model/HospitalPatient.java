package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Seaton
 * @version V1.0.0
 * @date 2018/5/31 11:43
 * @description 住院患者信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_HOSPITAL_PATIENT")
public class HospitalPatient extends BaseModel {


    private static final long serialVersionUID = 6759121776650847954L;
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 床号
     */
    @Column(name = "BED_NO")
    private String bedNo;


    /**
     * 年龄
     */
    @Column(name = "AGE")
    private String age;

    /**
     * 性别
     */
    @Column(name = "SEX")
    private Integer sex;

    /**
     * 住院号
     */
    @Column(name = "H_NO")
    private String hNo;


    /**
     * 患者id
     */
    @Column(name = "PATIENT_ID")
    private Long patientId;

    /**
     * 状态
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 住院天数
     */
    @Column(name = "IN_HOSPITAL_DAYS")
    private String inHospitalDays;


    /**
     * 入院诊断
     */
    @Column(name = "DIAGNOSIS")
    private String diagnosis;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 责任医生
     */
    @Column(name = "RESPONSIBLE_DOCTOR")
    private String responsibleDoctor;
}
