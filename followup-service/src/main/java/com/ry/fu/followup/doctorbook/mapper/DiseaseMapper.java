package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Disease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/12/7.
 *
 */
@Mapper
public interface DiseaseMapper extends BaseMapper<Disease> {

    @Select("select name ,org_id from FU_DISEASE where id = #{id}")
    List<Disease> queryNameById(@Param("id") Long id);

}
