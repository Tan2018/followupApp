package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.medicaljournal.model.PaySucesessRecordVO;
import com.ry.fu.esb.medicaljournal.model.RegisterSucesessRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 支付成功页面
 */
@Mapper
public interface PaysuccessRecordMapper{

    RegisterSucesessRecordVO selectRegisterSucesessRecordByOrderId(@Param("orderId") String orderId);

    PaySucesessRecordVO selectPaySucesessRecordByOrderId(@Param("orderId") String orderId);


}
