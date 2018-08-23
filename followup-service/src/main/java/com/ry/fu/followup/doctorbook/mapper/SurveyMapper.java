package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Survey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by jackson on 2018/4/27.
 */
@Mapper
public interface SurveyMapper extends BaseMapper<Survey> {
    Survey querySurvey(@Param("flowProgramInstanceId") Long flowProgramInstanceId);
    List<Map<String,Object>> querySurveylist(@Param("groupId") Long groupId,@Param("patientId") Long patientId);

}
