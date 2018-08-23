package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.PayMedReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 医保支付请求数据，存储到数据库记录下来
 */
@Mapper
public interface PayMedReqMapper extends BaseMapper<PayMedReq> {

    /**
     *
     * @param outOrderNo 传递给阳光康众的外部ID，实则为我们系统的ID
     * @return
     */
    @Select("select a.id as id from M_PAY_MED_REQ a where rownum = 1 and a.MCH_ORDER_NO = #{MCH_ORDER_NO} order by a.id desc")
    Long selectByMchOrderNo(@Param("MCH_ORDER_NO") String mchOrderNo);

}
