package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.ActHiTaskinst;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by Jam on 2017/12/8.
 *
 */
@Mapper
public interface ActHiTaskinstMapper extends BaseMapper<ActHiTaskinst> {

    Integer getActHiTaskinstctCountByInstId(@Param("proc_inst_id_") Long proc_inst_id_);

    //通过proc_inst_id_查找所有的记录
    List<ActHiTaskinst> queryActHiTaskinstByInstid(@Param("proc_inst_id_") Long proc_inst_id_,
                                                   @Param("start") Long start, @Param("end") Long end);
}
