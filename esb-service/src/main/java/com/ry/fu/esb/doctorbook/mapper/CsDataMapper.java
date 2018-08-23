package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.CsData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/9 15:16
 **/
@Mapper
public interface CsDataMapper extends BaseMapper<CsData>{

    @Select("select pc_id from m_cs_data where st_state=1 and take_doctor=#{doctorId} and patient_id=#{patientId}")
    List<Long> findHandPatientId(@Param("doctorId") Long doctorId,@Param("patientId") Long patientId);
}
