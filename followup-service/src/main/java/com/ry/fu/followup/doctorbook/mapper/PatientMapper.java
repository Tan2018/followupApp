package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Patient;
import com.ry.fu.followup.doctorbook.model.Remind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Jam on 2017/12/8.
 *
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {

    // 通过groupId查询组下面所有患者
    @Select("select fp.id as id, fp.name as name, fp.height as height, fp.blood_type as bloodType, fp.phone as phone, fp.email as email, fp.sex as sex, fp.weight as weight, fp.marriage as marriage, fp.age as age from FU_GROUP fg, FU_GROUP_RECORD fgr, FU_RECORD fr, FU_PATIENT fp where fg.id=fgr.group_id and fgr.record_id=fr.id and fr.patient_id=fp.id and fg.id=#{groupId}")
    List<Patient> queryPatientsByGroupId(@Param("groupId") Long groupId);

    Integer getPatientsCountByGroupId(@Param("groupId") Long groupId, @Param("patientName") String patientName);

    List<Patient> queryPatientsByGroupIdByPage(@Param("groupId") Long groupId, @Param("start") Long start, @Param("end") Long end);

    List<Patient> queryPatientsByGroupIdAndNameByPage(@Param("groupId") Long groupId, @Param("patientName") String patientName, @Param("start") Long start, @Param("end") Long end);

    @Select("select id,name,phone from FU_PATIENT where id=#{id}")
    Patient queryPatientById(@Param("id") Long id);

    List<Remind> queryRemindsByDoctorId(@Param("doctorId") Long doctorId);

    List<Patient> queryPatientStatus(@Param("list") List list);
}
