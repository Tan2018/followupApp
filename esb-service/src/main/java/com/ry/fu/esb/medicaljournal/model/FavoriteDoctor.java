package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_FAVORITES")
public class FavoriteDoctor implements Serializable {

    private static final long serialVersionUID = 1628850772445861695L;
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 移动账号ID
     */
    private  Long accountId;

    /**
     * 医生唯一识别号
     */
    private  Long doctorId;


    /**
     * 收藏时间
     */
    private Date createTime;
}
