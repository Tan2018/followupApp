package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/12/1.
 *
 */
@Mapper
public interface PushHistoryMapper extends BaseMapper<Group> {

    // 根据userId和status查询该用户申请groupId总数
    @Select("select count(*) as totalRow from fu_push_history b where b.alias= #{userId} and b.push_type=51")

    Integer getPushHistoryCountByDoctorId(@Param("userId") String userId);

    List<Map<String,Object>> queryPushHistoryCountByDoctorIdByPage(@Param("userId") String userId, @Param("start") Long start, @Param("end") Long end);

    //插入审核推送记录
    @Insert("insert into fu_push_history values(#{id},#{alias},#{title},#{content},to_date(to_char(systimestamp,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss'),#{extras},51)")//to_date(to_char(systimestamp,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')
    // id, alias title, notification_title,pushtime,extras,pushtype
    Integer insertPushHistory(@Param("id") Integer id,@Param("alias") String alias,@Param("title") String title,@Param("content")String notification_title,@Param("extras")String extras);

}
