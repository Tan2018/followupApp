package com.ry.fu.esb.doctorbook.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;

/**
 * @author Joker
 * @Date 2018/7/4 16:33
 * @description
 */
public interface ConsultationDoctorService {

    ResponseData consultationPatient(String consDoctorId,String orderMode) throws ESBException, SystemException;
}
