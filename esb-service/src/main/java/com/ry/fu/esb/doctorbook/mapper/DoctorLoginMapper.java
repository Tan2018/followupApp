package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.DoctorLoginInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 医生信息，缓存到FUM数据库
 */
@Mapper
public interface DoctorLoginMapper extends BaseMapper<DoctorLoginInfo> {



}
