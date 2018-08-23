package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.AppointDoctor;
import com.ry.fu.esb.doctorbook.model.PtRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/6 14:47
 **/
@Mapper
public interface PtRecordMapper extends BaseMapper<PtRecord> {

    @Select("select id,patient_id from m_pt_record where to_char(create_time,'yyyymmdd') = to_char(sysdate,'yyyymmdd') and type=1")
    public List<PtRecord> selectHandPatientInfo();
}
