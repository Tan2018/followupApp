package com.ry.fu.followup.doctorbook.mapper;

import org.apache.catalina.LifecycleState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupRecordMapper {

    @Update("update fu_group_record set TRANSFER_DOCTOR = #{dotorId} where id =#{groupRecordId}")
    public int updateTransferDoctor(@Param("dotorId")Long dotorId,@Param("groupRecordId")Long groupRecordId);

    public List<Map<String,Object>> selectDoctors(@Param("patientId")Long patientId,@Param("groupId")Long groupId);
}
