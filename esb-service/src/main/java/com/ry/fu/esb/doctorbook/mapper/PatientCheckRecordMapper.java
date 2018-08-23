package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.PatientCheckRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/30 13:34
 * @description
 */
@Mapper
public interface PatientCheckRecordMapper extends BaseMapper<PatientCheckRecord> {
   /* @Select("select count(1) from (select t.* from ((select b.id,b.doctor_id,b.doctor_name,b.create_time,b.st_word from m_pt_record b where b.type = #{type} and b.patient_id = #{patientId} and #{doctorId} in " +
            " (select t2.doctor_id from m_pt_record t1,m_ap_doctor t2 where t1.id = t2.pc_id and t1.type = #{type} and t1.patient_id = #{patientId})) union " +
            " (select t3.id,t3.doctor_id,t3.doctor_name,t3.create_time,t3.st_word from m_pt_record t3 where t3.patient_id = #{patientId} and t3.type = #{type} and t3.st_visible = 1 and t3.dept_id = #{deptId}) union " +
            " (select t4.id,t4.doctor_id,t4.doctor_name,t4.create_time,t4.st_word from m_pt_record t4 where t4.patient_id = #{patientId} and t4.type = #{type} and t4.st_visible = 2 and t4.group_id = #{groupId})) t " +
            " union  select  m.id,r.DOCTOR_ID,r.DOCTOR_NAME,m.shift_time CREATE_TIME,r.ST_WORD from M_OTHER_DEP m inner join m_pt_record r on m.patient_id = r.id " +
            " where r.patient_id = #{patientId} and r.type = #{type})j")*/
//   @Select
//           (
//           " (select t3.id,t3.doctor_id,t3.doctor_name,t3.create_time,t3.st_word from m_pt_record t3 where t3.patient_id = #{patientId} and t3.type = #{type} and t3.st_visible = 1 and t3.dept_id = #{deptId}) union\n" +
//           " (select t0.id,t0.doctor_id,t0.doctor_name,t0.create_time,t0.st_word from m_pt_record t0 where t0.patient_id = #{patientId} and t0.type = #{type} and t0.st_visible = 0 and t0.DOCTOR_ID = #{doctorId}) union\n" +
//           " (select t4.id,t4.doctor_id,t4.doctor_name,t4.create_time,t4.st_word shift_words from m_pt_record t4 where t4.patient_id = #{patientId} and t4.type = #{type} and t4.st_visible = 2 and t4.group_id = #{groupId}) ")
   //Integer getCountByPatientId(PatientRecordPagerRequest recordPagerRequest);
    List<PatientCheckRecord> findByCheckRecordPatientId(Map<String,Object> params);

    @Select(" SELECT ID FROM M_PT_RECORD WHERE PATIENT_ID = #{patientId} AND TYPE = '2'")
    List<Long> findIdByPatientId(@Param("patientId") Long patientId);
}
