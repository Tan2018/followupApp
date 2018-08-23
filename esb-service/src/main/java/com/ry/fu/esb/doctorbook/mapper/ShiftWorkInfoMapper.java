package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.ShiftInfoVO;
import com.ry.fu.esb.doctorbook.model.ShiftWorkInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/25 21:01
 * @description 交接班记录信息存储到FUM数据库
 */
@Mapper
public interface ShiftWorkInfoMapper extends BaseMapper<ShiftWorkInfo> {
    @Update("update m_cs_data set st_state = '1',take_time = #{takeTime},take_doctor = #{doctorId} where id in (select id from m_cs_data where shift_time in\n" +
            " (select  max(shift_time) from m_cs_data where st_state = '0' and patient_id = #{patientId}))")
    Integer update(@Param("patientId") Long patientId, @Param("doctorId") Long doctorId, @Param("takeTime") Date takeTime);
    //TODO:直接用自带方法根据状态查询,删掉这两条SQL
    @Select("select patient_id from m_cs_data where to_char(shift_time,'yyyymmdd') = to_char(sysdate,'yyyymmdd')")
    List<String> findAllShiftPatientIds();

    @Select("select patient_id from m_cs_data where to_char(take_time,'yyyymmdd') = to_char(sysdate,'yyyymmdd')")
    List<String> findAllTakePatientIds();

    @Select("select patient_id from m_cs_data where shift_time > (sysdate-1) and st_state = '1'" +
            "and shift_flag=1 and (take_doctor = #{doctorId} or sup_take_doctor_id = #{doctorId})")
    List<String> findTookPatientIds(@Param("doctorId") String doctorId );

    @Select("select patient_id from m_cs_data where  shift_time > (sysdate-1) and st_state = '0'" +
            " and shift_flag=1 and (take_doctor = #{doctorId} or sup_take_doctor_id = #{doctorId})")
    List<String> findNoTakePatientIds(@Param("doctorId") String doctorId );

    @Select("select org_id from pwp_org where org_enabled = 1 and org_deleted = 0 and org_pid = #{firstOrgId} order by org_id")
    List<String> findSecondOrgList(@Param("firstOrgId")Integer firstOrgId);

    @Update("update m_cs_data set shift_again = '1' where id in (select id from m_cs_data where take_time in\n" +
            " (select  max(take_time) from m_cs_data where st_state = '1' and patient_id = #{patientId} and take_doctor= #{doctorId}))")
    void updateLastShiftInfo(@Param("patientId") Long patientId,@Param("doctorId") Long doctorId);

    List<ShiftInfoVO> findOtherDeptShiftInfoList(@Param("deptId") String deptId,@Param("doctorId") String doctorId);

    List<ShiftInfoVO> findThisDeptTakeInfoList(@Param("deptId") String deptId,@Param("doctorId") String doctorId);

    List<ShiftInfoVO> findThisGroupTakeInfoList(@Param("groupId") String groupId,@Param("doctorId") String doctorId);

    List<ShiftInfoVO> findOtherDeptTakeInfoList(@Param("deptId") String deptId,@Param("doctorId") String doctorId);

    List<ShiftInfoVO> findMyTakeInfoList(Map map);

   /* @Select("select * from m_cs_data where ")
    List<ShiftWorkInfo>findOtherDeptTaked(@Param("doctorCode") String doctorCode,@Param("departmentId")String departmentId);*/


}
