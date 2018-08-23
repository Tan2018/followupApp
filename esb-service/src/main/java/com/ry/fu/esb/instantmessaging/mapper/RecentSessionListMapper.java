package com.ry.fu.esb.instantmessaging.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.instantmessaging.model.RecentSessionList;
import com.ry.fu.esb.instantmessaging.model.Uinfos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯
 */
@Mapper
public interface RecentSessionListMapper  extends BaseMapper<RecentSessionList> {

    /**
     * 查询是否有此记录
     * @param fromaccount
     * @param to
     * @return
     */
    @Select("SELECT COUNT(1) FROM M_RECENT_SESSION_LIST WHERE FROMACCOUNT = #{fromaccount} OR TID = #{to}")
    int selectByFromAccountToCount(@Param("fromaccount") String fromaccount, @Param("to") String to);

    /**
     * 查询最近会话列表(医生)
     * @param fromAccount
     * @return
     */
    @Select("SELECT TID,FROMACCOUNT,CONVTYPE,BODY,MSGTYPE,MSGTIMESTAMP,EVENTTYPE FROM M_RECENT_SESSION_LIST WHERE FROMACCOUNT = #{fromAccount} OR TID =#{fromAccount}")
    List<RecentSessionList> selectByFromAccount(@Param("fromAccount") String fromAccount);

    /**
     * 查询最近会话列表总条数(医生)
     * @param fromAccount
     * @return
     */
    @Select("SELECT COUNT(1) FROM M_RECENT_SESSION_LIST WHERE FROMACCOUNT = #{fromAccount} OR TID =#{fromAccount}")
    int selectByFromAccountCount(@Param("fromAccount") String fromAccount);

   /**
     * 查询最近会话列表(患者)
     * @param tid
     * @return
     */
    @Select("SELECT TID,FROMACCOUNT,CONVTYPE,BODY,MSGTYPE,MSGTIMESTAMP,EVENTTYPE FROM M_RECENT_SESSION_LIST WHERE TID = #{tid}")
    List<RecentSessionList> selectByTid(@Param("tid") String tid);

    /**
     * 查询最近会话列表总条数(患者)
     * @param tid
     * @return
     */
    @Select("SELECT COUNT(1) FROM M_RECENT_SESSION_LIST WHERE TID = #{tid}")
    int selectByTidCount(@Param("tid") String tid);

    /**
     * 更新最近会话列表
     * @param convType
     * @param body
     * @param msgType
     * @param msgTimeStamp
     * @param eventtype
     * @param fromAccount
     * @return
     */
    @Update("UPDATE M_RECENT_SESSION_LIST SET CONVTYPE = #{convType},BODY = #{body},MSGTYPE = #{msgType},MSGTIMESTAMP = #{msgTimeStamp},EVENTTYPE = #{eventtype},FROMACCOUNT = #{fromAccount},TID = #{to} WHERE (FROMACCOUNT = #{fromAccount} AND TID = #{to}) OR (FROMACCOUNT = #{to} AND TID = #{fromAccount})")
    int updateByFromAccount(@Param("convType") String convType, @Param("body") String body, @Param("msgType") String msgType, @Param("msgTimeStamp") Date msgTimeStamp, @Param("eventtype") String eventtype, @Param("fromAccount") String fromAccount,@Param("to") String to);

    /**
     * 查询医生信息
     * @param doctorId
     * @return
     */
    @Select("SELECT CH_NAME,PROFESS_NAME,HEAD_IMG,DOCTOR_ID FROM M_DOCTOR WHERE DOCTOR_ID = #{doctorId}")
    Uinfos selectByDoctor(@Param("doctorId") String doctorId);
}
