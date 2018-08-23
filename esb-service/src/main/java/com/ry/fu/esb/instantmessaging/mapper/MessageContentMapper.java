package com.ry.fu.esb.instantmessaging.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.instantmessaging.model.MessageContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯
 */
@Mapper
public interface MessageContentMapper extends BaseMapper<MessageContent> {
    /**
     * 判断抄送单聊内容是否存在
     * @param msgidServer
     * @return
     */
    @Select("SELECT COUNT(0) FROM M_MESSAGE_CONTENT WHERE MSGIDSERVER = #{msgidServer}")
    int selectByMsgIdServer(@Param("msgidServer") String msgidServer);
}
