package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Jam on 2017/12/25.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "FU_FLOW_INSTANCE")
public class FlowInstance extends BaseModel {

    private Long id;
    private Long quesId;
    private String name;
    private Integer startFollowType;
    private Integer unit;
    private Date startFollowTime;
    private String describe;

}
