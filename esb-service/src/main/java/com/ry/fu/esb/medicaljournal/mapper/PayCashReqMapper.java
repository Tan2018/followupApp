package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.PayCashReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 支付请求数据，存储到数据库记录下来
 */
@Mapper
public interface PayCashReqMapper extends BaseMapper<PayCashReq> {

    /**
     *
     * @param outOrderNo 传递给阳光康众的外部ID，实则为我们系统的ID
     * @return
     */
    @Select("select a.id as id from M_PAY_CASH_REQ a where rownum = 1 and a.out_order_no = #{outOrderNo} order by a.id desc")
    Long selectByNoncestrAndOutOrderId(@Param("outOrderNo") String outOrderNo);

}
