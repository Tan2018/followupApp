package com.ry.fu.esb.medicalpatron.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicalpatron.model.MedicalFile;
import com.ry.fu.esb.medicalpatron.model.ResInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 15:31
 * @description 随医拍文件mapper接口
 */
@Mapper
public interface MedicalFileMapper extends BaseMapper<MedicalFile>{
    Integer insertBatch(@Param(value = "medicalFiles") List<MedicalFile> medicalFiles);
    Integer getCountByDoctorCode (@Param("doctorCode") String doctorCode,@Param("patientId") Long patientId);
    List<ResInfo> findAllByDoctorCode (@Param("doctorCode") String doctorCode);
    List<ResInfo> getPagerByDoctorCode(@Param("doctorCode") String doctorCode,@Param("patientId") Long patientId, @Param("start") Integer start, @Param("end") Integer end);
    @Update("update m_medical_file set status = '1' where status = '0' and id = #{id}")
    Integer deleteById(@Param("id") Long id);
    Integer deleteBatchByIds(@Param("ids") List<Long> ids);
}
