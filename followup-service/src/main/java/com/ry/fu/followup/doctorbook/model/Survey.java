package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 问卷调查model
 * Created by jackson on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "fu_survey_name")
public class Survey extends BaseModel {
    @Id
    private Long id;
    @Id
    private Long suVerId;
    private String title;
    private String description;
    private Integer sort;

    @Transient
    private List<SurveyQuestion> surveyQuestions;
}
