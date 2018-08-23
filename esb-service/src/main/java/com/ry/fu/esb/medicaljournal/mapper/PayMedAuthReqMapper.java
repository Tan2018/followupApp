package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.PayMedAuthReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/10 17:38
 * @description 医保授权请求
 */
@Mapper
public interface PayMedAuthReqMapper extends BaseMapper<PayMedAuthReq> {

    /**
     *
     * @param attach 传递给阳光康众的外部ID，实则为我们系统的ID
     * @return
     */
    @Select("select a.id as id from M_MED_AUTH_REQ a where rownum = 1 and a.attach = #{attach} order by a.id desc")
    Long selectByAttach(@Param("attach") String attach);

}
