package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.doctorbook.model.Fufile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FufileMapper {

    @Select("select * from fu_file where group_id = #{groupId,jdbcType=NUMERIC}")
    public List<Fufile> getFilePaths(@Param("groupId") Long groupId);

}
