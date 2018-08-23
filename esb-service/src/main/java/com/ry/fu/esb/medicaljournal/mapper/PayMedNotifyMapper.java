package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.PayMedNotify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 支付请求数据，存储到数据库记录下来
 */
@Mapper
public interface PayMedNotifyMapper extends BaseMapper<PayMedNotify> {
    @Select("select n.id,n.Req_Id,n.order_no,n.order_status,n.order_pay_time,n.notify_data from M_PAY_MED_NOTIFY n where n.Req_Id = ${reqId};")
    List<PayMedNotify> selectByReqId(@Param("reqId") String reqId);
}
