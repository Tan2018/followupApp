package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/23 10:34
 * @description 推送通知列表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PUSH_LIST")
public class PushList extends BaseModel{

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PUSH_TIME")
    private Date pushTime;

    @Column(name = "PUSH_TYPE")
    private String pushType;

    @Column(name = "EXTRAS")
    private String extras;

    @Column(name = "PATIENT_ID")
    private String patientId;

}
