package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.util.Date;
import java.util.Set;

/**
 * Created by Jam on 2017/12/19.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Remind extends BaseModel {

    private Long id;
    private String name;
    private String project;
    private String phones;
    private Date followTime;
    private Long projectId;
    private Integer connected;

    @Transient
    private Set phoneNumber;
    @Transient
    private String startFollowTime;
}
