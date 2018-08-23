package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/12/1.
 *
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    Group selectGroup(@Param("id") Long id, @Param("status") Long status, @Param("groupType") Long groupType);

    List<Group> selectGroups(@Param("list") List list, @Param("status") Long status, @Param("groupType") Long groupType);


    Integer selectDoctorGroupsCount(@Param("doctorId")Long doctorId, @Param("status") Long status);

    List<Group> selectDoctorGroups(@Param("doctorId")Long doctorId, @Param("status") Long status, @Param("start") Long start, @Param("end") Long end);

    @Select("select a.group_id as groupId from FU_GROUP_MEMBER a,FU_GROUP b where a.group_id = b.id and a.user_id= #{userId} and b.status = #{status}")
    List<Map<String,Object>> queryGroupIdByDoctorIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);

    // 根据userId和GROUP_TYPE组类型查询groupId总数
    @Select("select count(*) as totalRow from FU_GROUP_MEMBER a,FU_GROUP b where a.group_id = b.id and a.user_id= #{userId} and b.group_type = #{groupType}")
    Integer getGroupIdCountByDoctorIdAndGroupType(@Param("userId") Long userId, @Param("groupType") Long groupType);

    // 根据userId和GROUP_TYPE分页查询groupId
    List<Map<String,Object>> queryGroupIdByDoctorIdAndGroupTypeByPage(@Param("userId") Long userId, @Param("groupType") Long groupType, @Param("start") Long start, @Param("end") Long end);

    // 根据userId和status查询该用户相关groupId总数
    //@Select("select count(*) as totalRow from FU_GROUP_MEMBER a,FU_GROUP b where a.group_id = b.id <if test=\"userId!=00351\"> and a.user_id=#{userId} </if> and b.status=#{status}")
    Integer getGroupIdCountByDoctorIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);

    // 根据userId和status查询该用户申请groupId总数
    //@Select("select count(*) as totalRow from FU_GROUP b where b.create_user= #{userId} and b.status = #{status}")
    Integer getGroupIdCountByApplDoctorIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);


    // 根据userId和status分页查询groupId
    List<Map<String,Object>> queryGroupIdByDoctorIdAndStatusByPage(@Param("userId") Long userId, @Param("status") Long status, @Param("start") Long start, @Param("end") Long end);

    List<Map<String,Object>> queryApplGroupIdByDoctorIdAndStatusByPage(@Param("userId") Long userId, @Param("status") Long status, @Param("start") Long start, @Param("end") Long end);

    // 审核组
    @Update("update fu_group a set status = #{status} ,approvertime = to_date(to_char(systimestamp,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') where exists(select * from fu_group_member b where a.id = b.group_id and b.user_id = #{userId} and a.id = #{projectId})")
    Integer updateGroupStatus(@Param("userId") Long userId, @Param("status") Long status, @Param("projectId") Long projectId);

    // 设置已联系
    Integer updateConnected(@Param("groupId") Long groupId, @Param("patientId") Long patientId);

    //查询pdf路径是否存在
    @Select("select pdf_path from fu_group where id = #{id}")
    Group  selectpdfPathById(@Param("id")Long id);


    //根据医生id查询他项目下的通讯录列表品
    List<Map<String, Object>> queryDoctoryContactById(@Param("userId") Long userId);
    List<Map<String, Object>> countPatientsByProjectId(@Param("userId") Long userId);

    //设置pdf路径
    @Update("update fu_group set pdf_path = #{pdfPath,jdbcType=VARCHAR} where id = #{id}")
    Integer updatePdfpathById(@Param("pdfPath")String pdfPath,@Param("id") Long id);

    Integer queryCountTask(@Param("userId") Long userId, @Param("status") Long status);

    /**
     * 查询任务列表
     * @param userId
     * @param status
     * @param start
     * @param end
     * @return
     */
    List<Group> queryGroupTask(@Param("userId") Long userId, @Param("status") Long status, @Param("start") Long start, @Param("end") Long end);

    Integer queryCountPassTask(@Param("userId") Long userId, @Param("status") Long status);

    /**
     * 查询通过任务列表
     * @param userId
     * @param status
     * @param start
     * @param end
     * @return
     */
    List<Group> queryPassGroupTask(@Param("userId") Long userId, @Param("status") Long status, @Param("start") Long start, @Param("end") Long end);


    Integer queryCountNotPassTask(@Param("userId") Long userId, @Param("status") Long status);

    /**
     * 查询不通过任务列表
     * @param userId
     * @param status
     * @param start
     * @param end
     * @return
     */
    List<Group> queryNotPassGroupTask(@Param("userId") Long userId, @Param("status") Long status, @Param("start") Long start, @Param("end") Long end);

    /**
     * CreateBy Tasher
     * @param projectId
     * @return
     */
    @Select("select process_instance_id from fu_group where id=#{projectId}")
    Long getInstanceIdByProjectId(@Param("projectId") Long projectId);
}
