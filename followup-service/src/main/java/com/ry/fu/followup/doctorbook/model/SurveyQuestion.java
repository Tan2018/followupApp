package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by jackson on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "fu_survey_question")
public class SurveyQuestion extends BaseModel {
    @Id
    private Long id;
    private String question;
    private Integer questionType;
    private Integer isAnswer;
    private Long surveyId;
    private Long suVerId;
    private Integer sort;

    @Transient
    private List<SurveyDetail> surveyDetails;
}
