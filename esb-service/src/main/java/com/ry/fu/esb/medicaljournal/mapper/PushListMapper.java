package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.PushList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/24 14:54
 * @description 推送的记录，存储到数据库记录下来
 */
@Mapper
public interface PushListMapper extends BaseMapper<PushList> {
    @Select("SELECT P.ID,P.ALIAS,P.TITLE,P.CONTENT,P.PUSH_TIME,P.EXTRAS FROM (SELECT PL.ID,PL.ALIAS,PL.TITLE,PL.CONTENT,PL.PUSH_TIME,PL.EXTRAS,ROWNUM RN FROM PUSH_LIST PL) P WHERE P.ALIAS = #{alias} AND RN > #{pageNum} AND RN <= #{pageSize}")
    List<PushList>  selectByPrimaryAlias(@Param("alias") String alias, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
