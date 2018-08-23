package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PART")
public class Part implements Serializable {

    private static final long serialVersionUID = -8203391195392464168L;
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 部位编码
     */
    private String code;


    /**
     * name
     */
    private String name;


    /**
     * 症状列表
     */
    private List<Symptoms> symptomsList;

    /**
     * 未选择时的图片
     */
    private String unSelectedUrl;

    /**
     * 选择时的图片
     */
    private String selectedUrl;

}
