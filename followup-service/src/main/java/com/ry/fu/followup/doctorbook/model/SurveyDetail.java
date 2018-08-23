package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by jackson on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "fu_survey_detail")
public class SurveyDetail extends BaseModel {
    @Id
    private Long id;
    private Long questionId;
    private String choiceText;
    private Integer sort;
    private Integer isDefault;


}
