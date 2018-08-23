package com.ry.fu.esb.medicalpatron.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicalpatron.model.FileUploadReqInfo;
import com.ry.fu.esb.medicalpatron.model.ReqInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 16:03
 * @description 随医拍文件Service接口
 */
public interface MedicalFileService{
    ResponseData save(FileUploadReqInfo fileUploadReqInfo);
    ResponseData findAllByDoctorCode(String doctorCode,Long patientId);
    ResponseData getPagerByDoctorCode(ReqInfo reqInfo);
    ResponseData deleteById(Long id);
    ResponseData deleteBatchByIds(List<Long> ids);
    ResponseData findPatientInfo(Map<String,Object> params) throws ESBException, SystemException;
}
