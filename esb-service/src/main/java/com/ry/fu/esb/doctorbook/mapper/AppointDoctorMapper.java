package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.AppointDoctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/30 14:15
 * @description
 */
@Mapper
public interface AppointDoctorMapper extends BaseMapper<AppointDoctor> {
    Integer insertBatch(@Param("appointDoctors") List<AppointDoctor> appointDoctors);
    @Select("select doctor_id from m_ap_doctor where pc_id = #{patientRecordId}")
    List<Long> findDoctorIdByRecordId(@Param("patientRecordId") Long patientRecordId);

    @Update("update m_ap_doctor set hand_inform_id='' where doctor_id=#{doctorId} and hand_inform_id=#{handInformId}")
    Integer updateAppointDoctorInfo(@Param("doctorId") Long doctorId,@Param("handInformId") Long handInformId);

    List<AppointDoctor> selectAppointeeDoctorInfo(@Param("doctorId") String doctorId);
}
