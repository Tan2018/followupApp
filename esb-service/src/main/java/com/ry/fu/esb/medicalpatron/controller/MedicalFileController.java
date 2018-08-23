package com.ry.fu.esb.medicalpatron.controller;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicalpatron.model.FileUploadReqInfo;
import com.ry.fu.esb.medicalpatron.model.ReqInfo;
import com.ry.fu.esb.medicalpatron.service.MedicalFileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 16:03
 * @description 随医拍文件Controller
 */
@RestController
@RequestMapping(value = "${prefixPath}/v1/medicalPatron/medicalFile")
public class MedicalFileController {
    @Autowired
    private MedicalFileService medicalFileService;

    /**
     * 上传图片
     * @param fileUploadReqInfo
     * @return
     */
    @PostMapping(value="/upload")
    public ResponseData uploadImg(FileUploadReqInfo fileUploadReqInfo){
        return medicalFileService.save(fileUploadReqInfo);
    }

    /**
     * 获取医生随医拍所有照片
     * @param map
     * @return
     */
    @PostMapping (value="/findAllByDoctorCode")
    public ResponseData findAllByDoctorCode(@RequestBody Map<String,Object> map){
        return medicalFileService.findAllByDoctorCode((String) map.get("doctorCode"),Long.parseLong(map.get("patientId").toString()));
    }

    /**
     * 获取医生随医拍所有照片（分页）
     * @param reqInfo
     * @return
     */
    @PostMapping (value="/getPagerByDoctorCode")
    public ResponseData getPagerByDoctorCode(@RequestBody ReqInfo reqInfo){
        return medicalFileService.getPagerByDoctorCode(reqInfo);
    }

    /**
     * 删除随医拍照片
     * @param id
     * @return
     */
    @PostMapping(value="/deleteById/{id}")
    public ResponseData deleteById(@PathVariable("id") Long id){
        return medicalFileService.deleteById(id);
    }

    /**
     * 批量删除随医拍照片
     * @param params
     * @return
     */
    @PostMapping(value="/deleteBatchByIds")
    public ResponseData deleteBatchByIds(@RequestBody Map<String,Object> params){
        //统一参数名为ids
        List<Long> ids = (List<Long>) params.get("ids");
        return medicalFileService.deleteBatchByIds(ids);
    }

    /**
     * 根据诊疗卡号或患者姓名查找患者信息
     * @param params
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    @PostMapping(value="/findPatientInfo")
    public ResponseData  findPatientInfo(@RequestBody Map<String,Object> params) throws SystemException, ESBException {
        return medicalFileService.findPatientInfo(params);
    }
}
