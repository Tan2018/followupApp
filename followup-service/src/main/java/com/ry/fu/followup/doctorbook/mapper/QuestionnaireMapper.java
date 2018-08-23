package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Questionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jackson on 2018/4/9.
 */
@Mapper
public interface QuestionnaireMapper extends BaseMapper<Questionnaire> {
    List<Questionnaire> queryQuestionnaireByPatientAndPrjId(@Param("patientId") Long patientId, @Param("projectId") Long projectId);

}
