package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.HospitalPatient;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/25 21:01
 * @description 交接班记录信息存储到FUM数据库
 */
@Mapper
public interface HospitalPatientMapper extends BaseMapper<HospitalPatient> {

}
