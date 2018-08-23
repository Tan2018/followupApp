package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.DoctorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 医生信息，缓存到FUM数据库
 */
@Mapper
public interface DoctorMapper extends BaseMapper<DoctorInfo> {
    void saveOrUpdate(DoctorInfo doctorInfo);

    List<DoctorInfo> selectAppointDoctorInfo(List<Long> doctors);

    @Select("select doctor_Id from m_doctor where user_name=#{userName}")
    String selectDoctorId(@Param("userName") String userName);


}
