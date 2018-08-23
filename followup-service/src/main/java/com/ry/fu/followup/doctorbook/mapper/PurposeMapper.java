package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Purpose;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Jam on 2017/12/7.
 *
 */
@Mapper
public interface PurposeMapper extends BaseMapper<Purpose> {



    // 根据ID查询随访目的
    @Select("select PURPOSE_TEXT as purposeText from FU_PURPOSE where ID = #{purposeId}")
    Purpose queryPurposeById(@Param("purposeId") Long purposeId);

    @Select("select PURPOSE_TEXT from FU_GROUP_PURPOSE fgp, FU_PURPOSE fp where fgp.purpose_id=fp.id and fgp.group_id=#{groupId}")
    List<Purpose> queryPurposeByGroupId(@Param("groupId") Long groupId);

}
