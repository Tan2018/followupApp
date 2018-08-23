package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.FlowInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/12/25.
 *
 */
@Mapper
public interface FlowInstanceMapper extends BaseMapper<FlowInstance> {

    List<FlowInstance> queryReportDateByQuesId(@Param("quesId") Long quesId);

    Integer updateStatus(@Param("status") Integer status,@Param("flowProgramInstanceId") Long flowProgramInstanceId);

    /**
     * 根据住院号ID和状态查询随访实例
     */
    List<Map<String,Object>> queryFuInstanceByPatientId(@Param("patientId") Long patientId,@Param("status") String status);
}


