package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.PayCashNotify;
import com.ry.fu.esb.medicaljournal.model.PayCashRefundReq;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 退费支付请求数据，存储到数据库记录下来
 */
@Mapper
public interface PayCashRefundReqMapper extends BaseMapper<PayCashRefundReq> {


}
