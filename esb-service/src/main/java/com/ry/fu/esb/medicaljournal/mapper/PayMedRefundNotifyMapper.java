package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.PayMedRefundNotify;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/10 17:38
 * @description 医保退费支付请求数据，存储到数据库记录下来
 */
@Mapper
public interface PayMedRefundNotifyMapper extends BaseMapper<PayMedRefundNotify> {


}
