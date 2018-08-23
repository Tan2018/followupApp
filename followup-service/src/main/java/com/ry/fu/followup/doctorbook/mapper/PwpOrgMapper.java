package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Disease;
import com.ry.fu.followup.doctorbook.model.PwpOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Jam on 2017/12/7.
 *
 */
@Mapper
public interface PwpOrgMapper extends BaseMapper<PwpOrg> {

    @Select("select org_name from pwp_org where org_id = #{orgId}")
    PwpOrg queryOrgNameById(@Param("orgId") Long orgId);

}
