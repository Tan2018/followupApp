package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;
import java.util.List;

/**
 * Created by jackson on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "fu_push_history")
public class PushHistory extends BaseModel {
    @Id
    private Long id;
    private String alias;
    private String title;
    private String content;
    private Date pushTime;
    private String extras;
    private Integer pushType;

}
