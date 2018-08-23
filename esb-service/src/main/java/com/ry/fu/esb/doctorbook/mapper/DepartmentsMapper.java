package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.*;
import com.ry.fu.esb.doctorbook.model.request.QueryOtherDepPatientRequest;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentsMapper extends BaseMapper<HandoverStatisticsInfo>{

    public List<HandoverStatisticsInfo> handoverStatistics(Map<String,Object> map);

    @Select("select count(1) from M_HOSPITAL_PATIENT p " +
            "inner join M_CS_DATA m on p.patient_id = m.patient_id where p.org_name = (select g.org_name from M_ORG g where g.org_id = #{orgId}) " +
            "and to_char(m.SHIFT_TIME,'yyyy/MM/dd') = #{date}")
    public Integer getCount(@Param("orgId") Integer orgId, @Param("date") String date);

    public List<DepartmentsStatisticsInfo> departmentsStatistics(Map<String,Object> map);

    @Select("select count(1) " +
            "from M_DE_DATA d " +
            "inner join M_ORG o on d.org_id = o.org_id " +
            "where to_char(d.shift_time,'YYYY/MM/DD') = #{date}")
    public Integer getDepCount(@Param("date") String date);

    /*@Insert("insert into M_DE_DOCTOR(ID,DOCTOR_ID,DE_ID)values(#{id},#{doctorId},#{deId})")
    public Integer insertDocTor(DeDoctorInfo deDoctorInfo);*/

    @Insert("insert into M_DE_DATA(ID,ORG_ID,SHIFT_DOCTOR,TAKE_DOCTOR,ST_STATE,SHIFT_TIME,Describe) " +
            "values(#{id},#{orgId},#{shiftDoctor},#{takeDoctor},#{stState},#{shiftTime},#{describe})")
    public Integer insertDocLog(DeDataInfo deDataInfo);

    @Select("insert into M_OTHER_DEP(ID," +
            "TAKE_DOCTOR," +
            "PATIENT_ID," +
            "BED_NO," +
            "HOSPITAL_DAY," +
            "P_AGE," +
            "P_SEX," +
            "STR_DIAGNOSIS," +
            "ST_STATE," +
            "SHIFT_TIME," +
            "TAKE_TIME," +
            "PATIENT_NAME," +
            "ORG_ID," +
            "DOCTOR_NAME," +
            "ISO_HTER_DEPT," +
            "ORG_NAME)"+
            "values" +
            "(#{id},#{takeDoctor},#{patientId},#{bedNo},#{hospitalDay},#{pAge},#{pSex},#{strDiagnosis},#{stState}" +
            ",#{shiftTime},#{takeTime},#{patientName},#{orgId},#{doctorName},#{isoHterDept},#{orgName})")
    public Integer addOtherDep(OtherDepInfo otherDepInfo);

    public List<OtherDepInfo> queryOtherDepPatient(QueryOtherDepPatientRequest queryOtherDepPatientRequest);

    @Select("select count(1) " +
            "from M_DE_DATA d " +
            "inner join M_ORG o on d.org_id = o.org_id")
    public Integer getDepLogCount();

    public List<DepartmentsStatisticsInfoLog> departmentsStatisticsLog(Map<String,Object> map);

    @Select("select distinct o.org_name,o.org_id from M_DE_DATA d inner join M_ORG o on d.org_id = o.org_id")
    public List<DepDoctor> queryAllDep();

    @Update("update M_OTHER_DEP m set m.ST_STATE = 1,m.Take_Time = #{date}  where m.ID = #{id}")
    public Integer updateState(@Param("id")Long id,@Param("date") Date date);

    public List<OtherDepInfoLog> otherDepInfoLog(Map<String,Object> map);

    public Integer queryOtherDepInfoLogCount(Map<String,Object> map);
}
