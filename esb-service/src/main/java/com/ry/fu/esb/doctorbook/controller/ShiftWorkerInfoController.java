package com.ry.fu.esb.doctorbook.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.ShiftWorkInfo;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.service.ShiftWorkerInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/27 11:06
 * @description 交接班controller
 */
@RestController
@RequestMapping("${prefixPath}/v1/docBook/ShiftWorkerInfo")
public class ShiftWorkerInfoController {
    @Autowired
    private ShiftWorkerInfoService shiftWorkerInfoService;

    /**
     * 保存交接班信息(交班)
     * TODO:To Delete
     * @param shiftWorkInfo
     * @return
     */
    @PostMapping("/saveShiftInfo")
    public ResponseData saveShiftInfo(@RequestBody ShiftWorkInfo shiftWorkInfo){
        return shiftWorkerInfoService.saveShiftInfo(shiftWorkInfo);
    }
    /**
     * 更新交接班信息(接班/接班值班)
     * @param shiftWorkInfo
     * @return
     */
    @PostMapping("/updateShiftInfo")
    public ResponseData updateShiftInfo(@RequestBody ShiftWorkInfo shiftWorkInfo){
        return shiftWorkerInfoService.updateShiftInfo(shiftWorkInfo);
    }

    /**
     * 保存患者检查非文件记录信息
     * @param patientCheckRecordRequest
     * @return
     */
    @PostMapping("/saveRecordInfo")
    public ResponseData saveRecordInfo(@RequestBody PatientCheckRecordRequest patientCheckRecordRequest){
        return shiftWorkerInfoService.saveShiftDutyRecord(patientCheckRecordRequest);
    }

    /**
     * 患者交班统计
     */
    @PostMapping("/patientStatistics")
    public ResponseData patientStatistics(@RequestBody Map<String,String> map){
        return shiftWorkerInfoService.patientStatistics(map);
    }

    /**
     * 科室交班统计
     */
    @PostMapping("/departmentStatistics")
    public ResponseData departmentStatistics(@RequestBody Map<String,String> map){
        return shiftWorkerInfoService.departmentStatistics(map);
    }

    /**
     * 保存患者检查文件记录信息
     * @param patientCheckRecordFileRequest
     * @return
     */
    @PostMapping("/upload")
    public ResponseData uploadFile(PatientCheckRecordFileRequest patientCheckRecordFileRequest){
        return shiftWorkerInfoService.uploadFile(patientCheckRecordFileRequest);
    }

    /**
     * 获取患者列表信息(本科室内)
     * @param patienInformationRequest
     * @return
     */
    @PostMapping("/findPatientInfo")
    public ResponseData findPatientInfo(@RequestBody InpatientInfoRequest patienInformationRequest){
        return shiftWorkerInfoService.findPatientInfo(patienInformationRequest);
    }

    /**
     * 取消本次交班
     * @param map
     * @return
     */
    @PostMapping("/cancelShift")
    public ResponseData cancelShift(@RequestBody Map<String,String> map){
        return shiftWorkerInfoService.cancelShift(map);
    }


    /**
     * 根据科室ID获取医生信息列表
     * @param doctorInfomationRequest
     * @return
     */
    @PostMapping("/findDoctorInfo")
    public ResponseData findDoctorInfo(@RequestBody DoctorInfomationRequest doctorInfomationRequest){
        return shiftWorkerInfoService.findDoctorInfo(doctorInfomationRequest.getDeptId());
       /* try {
        } catch (ESBException e) {
            e.printStackTrace();
            return new ResponseData("500", "系统异常", "ESB系统异常");
        } catch (SystemException e) {
            e.printStackTrace();
            return new ResponseData("500", "获取医生信息失败", "获取医生信息失败");
        }*/
    }

    /**
     * 获取患者记录信息(分页)
     * @param patientRecordPagerRequest
     * @return
     */
    @PostMapping("/findPatientRecordInfo")
    public ResponseData findPatientRecordInfo(@RequestBody PatientRecordPagerRequest patientRecordPagerRequest){
        return shiftWorkerInfoService.findCheckRecordInfoByPager(patientRecordPagerRequest);
    }

    /**
     * 根据医生姓名获取医生信息(分页)
     * @param requestInfo
     * @return
     */
    @PostMapping("/findDoctorInfoByName")
    public ResponseData findDoctorInfoByName(@RequestBody DoctorInfoByPageRequest requestInfo)  {
        try {
            return shiftWorkerInfoService.findDoctorInfoByName(requestInfo);
        } catch (ESBException e) {
            e.printStackTrace();
            return new ResponseData("500", "ESB系统异常", "ESB系统异常");
        } catch (SystemException e) {
            e.printStackTrace();
            return new ResponseData("500", "获取医生信息失败", "获取医生信息失败");
        }
    }

    /**
     * 选择院区
     * @return
     */
    @PostMapping("/findHospital")
    public ResponseData findHospital(){
        return shiftWorkerInfoService.findHospital();
    }

    /**
     * 科室列表
     * @return
     */
    @PostMapping("/findOrgList")
    public ResponseData findOrgList(@RequestBody Map<String,String> map){
        return shiftWorkerInfoService.findOrgList(map.get("hospitalId"));
    }

    /**
     * 查询上级科室
     * @return
     */
    @PostMapping("/findSuperiorOrg")
    public ResponseData findSuperiorOrg(@RequestBody Map<String,String> map){
        return shiftWorkerInfoService.findSuperiorOrg(map.get("deptId"));
    }


    /**
     * 删除查房记录
     * @param map
     * @return
     */
    @PostMapping("/removeRoundRecord")
    public ResponseData removeRoundRecord(@RequestBody Map<String,String> map){
        if(map!=null&&!(map.size()>0)){
            return new ResponseData(StatusCode.ARGU_ERROR.getCode(),"请求参数不能为空",null);
        }
        if(StringUtils.isEmpty(map.get("id"))){
            return new ResponseData(StatusCode.ARGU_ERROR.getCode(),StatusCode.ARGU_ERROR.getMsg(),null);
        }
        return shiftWorkerInfoService.delRoundRecord(map);
    }
}
