package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.doctorbook.model.DepartmentShiftedStatisticsVO;
import com.ry.fu.esb.doctorbook.model.PatientStatisticsVO;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author Seaton
 * @version V1.0.0
 * @Date 2018/3/25 21:01
 * @description 交接班统计
 */
@Mapper
public interface ShiftDutyStatisticsMapper {

    /**
     * 统计患者
     * @param start
     * @param end
     * @param deptId
     * @param date
     * @return
     */
    List<PatientStatisticsVO> selectPatientStatisticsByDeptId(@Param("start") int start, @Param("end") int end, @Param("deptId")String deptId, @Param("date")String date);
    List<PatientStatisticsVO> selectPatientStatisticsByDeptId(@Param("deptId")String deptId, @Param("date")String date);
    List<DepartmentShiftedStatisticsVO> selectDepartmentStatistics(@Param("date")String date);

    @Select("select org_id ,org_name from pwp_org where org_enabled = 1 and org_deleted = 0 and org_pid = " +
            "(select org_id from pwp_org where org_pid is null) order by org_id")
    @Results({
            @Result(property="orgName",column="org_name"),
            @Result(property="orgId",column="org_id")
    })
    List<HashMap<String,String>> findHospital();


    @Select("select org_name,org_id from pwp_org where org_enabled = 1 and org_deleted = 0 and org_pid = #{orgPid} order by org_id ")
    @Results({
            @Result(property="orgName",column="org_name"),
            @Result(property="orgId",column="org_id")
    })
    List<HashMap<String,String>> findOrgList(Integer orgPid);

    @Select("select ORG_ID,org_name from pwp_org  where ORG_ID=(select ORG_PID from pwp_org  where ORG_ID=#{deptId}) ")
    @Results({
            @Result(property="orgName",column="org_name"),
            @Result(property="orgId",column="org_id")
    })
    HashMap<String,String> findSuperiorOrg(String deptId);
}
