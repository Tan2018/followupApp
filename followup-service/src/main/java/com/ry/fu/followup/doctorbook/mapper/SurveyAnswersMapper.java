package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jackson on 2018/4/27.
 */
@Mapper
public interface SurveyAnswersMapper extends BaseMapper<Answer> {
    Integer insertBatch(@Param("answers") List<Answer> answers);
}
