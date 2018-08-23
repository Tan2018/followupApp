package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.Patient;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author Walker
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 患者信息
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
    @Select("select a.id as id,a.name as name,a.auth_code as authCode, a.auth_status as authStatus from m_patient a where a" +
            ".esb_patient_id = #{patientId}")
    List<Patient> selectByPatientId(@Param("patientId") Long patientId);

    @Select("select id from m_Patient where esb_patient_id = #{patientId}")
    Patient selectByESBPatientId(@Param("patientId") String patientId);

    @Select("select name,esb_patient_id from m_patient a where NETEASE_TOKEN is null and ESB_PATIENT_ID is not null " +
            "and rownum < 20 order by CREATE_TIME desc" )
    @Results({
            @Result(property="ESBPatientId",column="esb_patient_id"),
            @Result(property="name",column="name")
    })
    List<HashMap<String,String>> selectNoTokenList();

    @Select("select name,esb_patient_id from m_patient a where NETEASE_TOKEN is null and ESB_PATIENT_ID is not null " +
            "and rownum < 20 order by CREATE_TIME desc" )
    List<Patient> selectNoTokenPatientList();

    @Update("update m_patient set NETEASE_TOKEN = #{netEaseToken} where esb_patient_id = #{patientId}")
    int updateByESBPatientId(@Param("patientId") String patientId,@Param("netEaseToken") String netEaseToken);
}
