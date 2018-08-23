package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.GroupProgramField;
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
public interface GroupProgramFieldMapper extends BaseMapper<GroupProgramField> {

    @Select("select field_name as fieldName from FU_GROUP_PROGRAM_FIELD where group_id = #{groupId}")
    List<Map<String,Object>> queryFieldNameByGroupId(@Param("groupId") Long groupId);

}
