package com.ry.fu.esb.jpush.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.jpush.model.CrisisProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26
 * @description 危急值患者项目信息
 */
@Mapper
public interface CrisisProjectMapper extends BaseMapper<CrisisProject> {
    /**
     * 根据项目编码判断是否已存在
     * @param itemCode
     * @return
     */
    @Select("SELECT COUNT(0) FROM M_INSPECTION_PROJECT WHERE ITEMCODE = #{itemCode} and CRITICAL_ID = #{lisLableNo}")
    int selectByItemCode(@Param("itemCode") String itemCode,@Param("lisLableNo") Long lisLableNo);

    /**
     * 根据项目编码修改为已审核状态
     * @param itemCode
     * @return
     */
    @Update("UPDATE M_INSPECTION_PROJECT SET ISRELIEVE = 1 WHERE EXAMINE_REQUEST_ID = #{examineRequestId} AND ITEMCODE = #{itemCode}")
    int updateByItemCode(@Param("examineRequestId") Long examineRequestId,@Param("itemCode") String itemCode);
}
