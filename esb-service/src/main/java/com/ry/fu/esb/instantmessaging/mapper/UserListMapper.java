package com.ry.fu.esb.instantmessaging.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.instantmessaging.model.UserList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯
 */
@Mapper
public interface UserListMapper extends BaseMapper<UserList> {
    /**
     * 修改网易云信密码
     *
     * @param accid 网易云账号
     * @param token 网易云密码
     * @return
     */
    @Update("UPDATE M_NETEASE_CLOUD_LETTER_USER_LIST SET USERTOKEN = #{token},UPDATETIME = #{updateTime} WHERE USERACCID = #{accid}")
    int updateUserToken(@Param("accid") String accid, @Param("token") String token, @Param("updateTime") Date updateTime);
}
