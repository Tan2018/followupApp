package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.OrderDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @author Joker
 * @version V1.0.0
 * @Date 2018/4/25 19:51
 * @description 药品详细信息
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Select("select ID,DETAIL_FEE,DETAIL_ID,DETAIL_NAME,DETAIL_COUNT,DETAIL_UNIT,DETAIL_AMOUT,ORDER_ID,CREATE_TIME from M_ORDER_DETAIL " +
            "where order_id=#{orderId}")
    List<OrderDetail> selectByOrderId(@Param("orderId") String orderId);

    @Select("select ID,DETAIL_FEE,DETAIL_ID,DETAIL_NAME,DETAIL_COUNT,DETAIL_UNIT,DETAIL_AMOUT,ORDER_ID,CREATE_TIME from M_ORDER_DETAIL " +
            "where order_id=#{orderId} and DETAIL_FEE LIKE '%药%'")
    List<OrderDetail> selectByOrderIdLike(@Param("orderId") String orderId);

    /**
     * 删除order_detail的数据
     * @param orderId
     * @return
     */
    @Delete("delete from m_order_detail where order_id=#{orderId}")
    int deleteByOrderId(@Param("orderId") String orderId);


}
