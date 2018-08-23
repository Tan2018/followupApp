package com.ry.fu.esb.instantmessaging.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.instantmessaging.model.MessageContentList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯
 */
@Mapper
public interface MessageContentListMapper extends BaseMapper<MessageContentList> {
    /**
     * 查询单聊历史记录
     * @param fromAccount
     * @return
     */
    @Select("SELECT FROMACCOUNT,TID,BODY,MSGTIMESTAMP,MSGIDCLIENT,MSGIDSERVER,MSGTYPE,ATTACHEXT,ATTACHSIZE,ATTACHDUR,ATTACHURL,ATTACHMD5 FROM (SELECT * FROM M_MESSAGE_CONTENT WHERE ((FROMACCOUNT = #{fromAccount} AND TID = #{to} AND MSGTIMESTAMP < to_date(#{startTime},'yyyy/mm/dd HH24:MI:SS')) OR (FROMACCOUNT = #{to} AND TID = #{fromAccount} AND MSGTIMESTAMP < to_date(#{startTime},'yyyy/mm/dd HH24:MI:SS'))) ORDER BY MSGTIMESTAMP DESC) WHERE ROWNUM <= #{pageSize}")
    List<MessageContentList> selectByFromAccountAndTo(@Param("fromAccount") String fromAccount,@Param("to") String to,@Param("startTime") String startTime,@Param("pageSize") Integer pageSize);

    /**
     * 查询近三天的聊天记录
     * @param fromaccount
     * @param to
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("SELECT FROMACCOUNT,TID,BODY,MSGTIMESTAMP,MSGTYPE,ATTACHEXT,ATTACHSIZE,ATTACHDUR,ATTACHURL,ATTACHMD5 FROM M_MESSAGE_CONTENT WHERE MSGTIMESTAMP >= #{startTime} AND MSGTIMESTAMP <= #{endTime} AND ((FROMACCOUNT = #{fromaccount} AND TID = #{to}) OR (FROMACCOUNT = #{to} AND TID = #{fromaccount}))ORDER BY MSGTIMESTAMP DESC")
    List<MessageContentList> selectByMsgTimeStamp(@Param("fromaccount") String fromaccount, @Param("to") String to, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计近三天的聊天记录总条数
     * @param fromaccount
     * @param to
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("SELECT COUNT(1) FROM M_MESSAGE_CONTENT WHERE (FROMACCOUNT = #{fromaccount} AND TID = #{to}) OR (FROMACCOUNT = #{to} AND TID = #{fromaccount}) AND MSGTIMESTAMP between #{startTime} AND #{endTime}")
    int selectByMsgTimeStampCount(@Param("fromaccount") String fromaccount, @Param("to") String to, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
