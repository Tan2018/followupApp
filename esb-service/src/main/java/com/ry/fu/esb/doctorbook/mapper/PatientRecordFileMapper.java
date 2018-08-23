package com.ry.fu.esb.doctorbook.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.doctorbook.model.PatientRecordFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/27 12:49
 * @description
 */
@Mapper
public interface PatientRecordFileMapper extends BaseMapper<PatientRecordFile> {
    Integer insertBatch(@Param("patientRecordFiles") List<PatientRecordFile> PatientRecordFiles);
    List<PatientRecordFile> findFileByRecordId(@Param("patientRecordId") Long patientRecordId);
}
