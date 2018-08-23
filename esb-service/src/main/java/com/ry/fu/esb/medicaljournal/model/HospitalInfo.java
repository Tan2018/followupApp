package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author jane
 * @Date 2018/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_HOSPITAL_INFO")
public class HospitalInfo extends BaseModel {
    private static final long serialVersionUID = 2540104745775798765L;
    @Id
    @Column(name = "id")
    private Long id;

    private Integer deptId;

    /**
     * 院区
     */
    private String name;
    /**
     * 地址
     */
    private String location;
    /**
     * 电子地图
     */
    private String uid;


    /**
     * 医院院区
     */
    @Column(name = "HOSPITALAREA")
    private String hospitalArea;

    /**
     * 医院简介
     */
    @Column(name = "DESCRIPTION")
    private String description;


    /**
     * 楼层分布
     */
    @Column(name = "BUILDING")
    private String building;

    /**
     * 交通指引
     */
    @Column(name = "TRANSPORT_GUIDE")
    private String transportGuide;
}
