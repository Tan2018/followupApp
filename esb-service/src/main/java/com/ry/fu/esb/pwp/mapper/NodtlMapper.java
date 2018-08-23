package com.ry.fu.esb.pwp.mapper;


import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.pwp.model.PwpNodtl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author walker
 */
@Mapper
public interface NodtlMapper extends BaseMapper<PwpNodtl> {

    PwpNodtl getNextId(@Param("noId") String noId);

    void updateNextId(@Param("noId") String noId);
}
