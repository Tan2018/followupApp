package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.GroupPurpose;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Jam on 2017/12/8.
 *
 */
@Mapper
public interface GroupPurposeMapper extends BaseMapper<GroupPurpose> {

    // 根据group_id查询随访目的id
    @Select("select PURPOSE_ID from FU_GROUP_PURPOSE where GROUP_ID = #{groupId}")
    List<GroupPurpose> queryPurposeIdByGroupId(@Param("groupId") Long groupId);

}
