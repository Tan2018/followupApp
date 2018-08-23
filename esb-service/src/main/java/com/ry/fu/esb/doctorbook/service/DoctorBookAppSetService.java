package com.ry.fu.esb.doctorbook.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.model.request.DoctorQueryRequest;

import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/24 16:35
 **/
public interface DoctorBookAppSetService {
    ResponseData findDoctorPersonalData(DoctorQueryRequest request) throws ESBException, SystemException;
}
