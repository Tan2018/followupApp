package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Program;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/12/11.
 *
 */
@Mapper
public interface ProgramMapper extends BaseMapper<Program> {

    @Select("select * from fu_program")
    List<Program> queryAllProgram();

    @Select("select id from fu_program")
    List<Program> queryAllProgramId();

    List<Program> queryProgramIdsByGroupId(@Param("groupId") Long groupId);

    List<Map<String, Object>> queryProgramDetailByGroupId(@Param("groupId") Long groupId);

    List<Map<String, Object>> queryProgramDetailByPlanId(@Param("planId") Long planId);

    List<Program> queryProgramByFlowId(@Param("flowInstanceId") Long flowInstanceId);

    List<Map<String, Object>> queryProgramDatasByGroupId(@Param("groupId") Long groupId);

    @Update("update fu_program set pdf_path=#{pdfPath,jdbcType=VARCHAR}  where id=#{programId}")
    Integer savePdfPathById(@Param("pdfPath")String pdfPath,@Param("programId")Long programId);

}
