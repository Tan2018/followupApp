package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.PayCashRefundNotify;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/5/23 19:28
 * @description 收银台退费异步通知 的Mapper文件
 */
@Mapper
public interface PayCashRefundNotifyMapper extends BaseMapper<PayCashRefundNotify> {
}
