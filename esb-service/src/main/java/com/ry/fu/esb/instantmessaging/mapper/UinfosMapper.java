package com.ry.fu.esb.instantmessaging.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.instantmessaging.model.Uinfos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯
 */
@Mapper
public interface UinfosMapper extends BaseMapper<Uinfos> {

    /**
     * 根据网易云信注册的id查询医生信息
     * @param doctorCode
     * @return
     */
    @Select("SELECT PROFESS_NAME,CH_NAME,HEAD_IMG,DOCTOR_ID,USER_NAME FROM M_DOCTOR WHERE USER_NAME = #{doctorCode}")
    List<Uinfos> inquireOfDoctorInformation(@Param("doctorCode") String doctorCode);
}
