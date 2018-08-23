package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.HandInform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/7 16:16
 **/
@Mapper
public interface HandInformMapper extends BaseMapper<HandInform>{

    //List<HandInform> selectHandInformInfo(@Param("map") Map<String,Object> map);
    List<HandInform> selectHandInformInfo(@Param("doctorId") String doctorId);

    int selectAppointDoctorCount(@Param("doctorId") String doctorId);
}
