package com.ry.fu.followup.doctorbook.mapper;

import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.doctorbook.model.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by jackson on 2018/4/8.
 */
@Mapper
public interface PlanMapper extends BaseMapper<Plan> {

    List<Map<String, Object>> queryPlanListByGroupId(@Param("groupId") Long groupId);
}
