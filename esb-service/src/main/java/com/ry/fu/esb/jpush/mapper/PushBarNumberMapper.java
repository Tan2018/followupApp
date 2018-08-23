package com.ry.fu.esb.jpush.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.jpush.model.PushBarNumber;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26
 * @description 医生手册推送条数统计
 */
@Mapper
public interface PushBarNumberMapper extends BaseMapper<PushBarNumber> {
    /**
     * 统计医生手册条数
     * @param personId
     * @param noticeType
     * @return
     */
    @Select("SELECT COUNT(1) FROM M_PUSH_BAR_NUMBER WHERE ALIAS = #{personId} AND NOTICETYPE = #{noticeType}")
    int selectByPersonId(@Param("personId")String personId,@Param("noticeType")Integer noticeType);

    /**
     * 清空医生手册统计条数
     * @param personId
     * @param noticeType
     * @return
     */
    @Delete("DELETE FROM M_PUSH_BAR_NUMBER WHERE ALIAS = #{personId} AND NOTICETYPE = #{noticeType}")
    int deleteByPersonIdAndNoticeType(@Param("personId")String personId,@Param("noticeType")String noticeType);
}
