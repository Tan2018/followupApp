package com.ry.fu.followup.doctorbook.model;

import com.ry.fu.followup.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

/**
 * Created by jackson on 2018/5/2.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "fu_survey_answers")
public class Answer extends BaseModel {
    @Id
    private Long choiceId;
    @Id
    private Long flowProgramInstanceId;
    private String answerText;

}
