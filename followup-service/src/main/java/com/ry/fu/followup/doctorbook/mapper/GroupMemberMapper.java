package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.GroupMember;
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
public interface GroupMemberMapper extends BaseMapper <GroupMember>{

    @Select("SELECT GROUP_ID FROM FU_GROUP_MEMBER WHERE USER_ID = #{userId}")
    List<Map<String,Object>> queryGroupIdByDoctorId(@Param("userId") Long userId);

    @Select("select user_id as userId, mem_type as memType from FU_GROUP_MEMBER where group_id = #{groupId}")
    List<Map<String,Object>> queryMemberByGroupId(@Param("groupId") Long groupId);

    @Select("select t.id as id ,t.group_id as groupId,t.user_id as userId,t.mem_type as memType,t.account_name as accountName,t.account_mobile as accountMobile,t.account_email as accountEmail" +
            " from FU_GROUP_MEMBER t where t.group_id = #{groupId} and t.user_id = #{userId}")
    GroupMember queryMemberByGroupIdAndUserId(@Param("groupId") Long groupId ,@Param("userId") Long userId);

}
