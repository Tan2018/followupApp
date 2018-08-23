package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.GroupConditionexPression;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Jam on 2017/12/7.
 *
 */
@Mapper
public interface GroupConditionexPressionMapper extends BaseMapper<GroupConditionexPression> {

    @Select("select condition_Stub as conditionStub from fu_group_condition_expression where group_id=#{id}")
    String queryConditionStub(@Param("id") Long id);
    //Map<String,Object>queryConditionStub(@Param("id") Long id);


}
