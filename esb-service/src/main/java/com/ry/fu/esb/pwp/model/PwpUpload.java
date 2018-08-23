package com.ry.fu.esb.pwp.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/2 10:34
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "PWP_UPLOAD")
public class PwpUpload extends BaseModel{

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "file_Name")
    private String fileName;
}
