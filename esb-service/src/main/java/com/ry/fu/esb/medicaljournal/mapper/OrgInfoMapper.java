package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.OrgInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 医生信息，缓存到FUM数据库
 */
@Mapper
public interface OrgInfoMapper extends BaseMapper<OrgInfo> {

    @Select("SELECT id FROM M_DOCTOR where doctor_id =  #{doctorId}")
    public  List<Long> findByEsbOrgId(@Param("doctorId") Long doctorId);

    @Insert("INSERT INTO M_ORG(id,parent_id,org_id,org_name,create_time)values(#{id},#{parentId},#{orgId},#{orgName},#{createTime})")
    void insertOrg(@Param("id") Long id,@Param("parentId") Integer parentId, @Param("orgId") Integer orgId, @Param("createTime") Date createTime, @Param("orgName") String orgName);

    @Select("SELECT HEAD_IMG FROM  M_DOCTOR where DOCTOR_ID=#{doctorId}")
    public  List<String> img(@Param("doctorId") Long doctorId);

    @Select("select id from M_ORG where orgId = #{orgId}")
    List<Long> selectByOrgId(@Param("orgId") Integer orgId);

}
