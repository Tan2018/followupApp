package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.GroupCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/12/7.
 *
 */
@Mapper
public interface GroupConditionMapper extends BaseMapper<GroupCondition> {

    @Select("select expression from FU_GROUP_CONDITION where group_id = #{groupId}")
    List<Map<String,Object>> queryExpressionByGroupId(@Param("groupId") Long groupId);

}
