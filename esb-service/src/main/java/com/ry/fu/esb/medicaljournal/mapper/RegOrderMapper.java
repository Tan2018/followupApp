package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.medicaljournal.model.RegOrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 订单信息
 */
@Mapper
public interface RegOrderMapper {

    List<RegOrderVO> selectWaittingPayment();

    List<RegOrderVO> seeDoctorTomorrowInform();

    List<RegOrderVO> seeDoctorTodayInform();

    List<RegOrderVO> syncDiagnoseFlag();
}
