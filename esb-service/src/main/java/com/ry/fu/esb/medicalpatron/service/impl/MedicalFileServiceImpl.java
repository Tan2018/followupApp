package com.ry.fu.esb.medicalpatron.service.impl;

import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.RegExpressionUtils;
import com.ry.fu.esb.common.utils.ResponseUtil;
import com.ry.fu.esb.common.utils.TimeUtils;
import com.ry.fu.esb.medicalpatron.model.request.PatientDataRequest;
import com.ry.fu.esb.medicalpatron.controller.MedicalFileController;
import com.ry.fu.esb.medicalpatron.mapper.MedicalFileMapper;
import com.ry.fu.esb.medicalpatron.model.*;
import com.ry.fu.esb.medicalpatron.model.response.PatientDataResponse;
import com.ry.fu.esb.medicalpatron.model.response.PatientDataResponseRecord;
import com.ry.fu.esb.medicalpatron.service.MedicalFileService;
import com.tt.fastdfs.FastdfsFacade;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 16:03
 * @description 随医拍文件Service接口实现
 */
@Service
public class MedicalFileServiceImpl implements MedicalFileService{
    private static final Logger logger = Logger.getLogger(MedicalFileController.class);

    @Autowired
    private MedicalFileMapper medicalFileMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private PublicService publicService;

    /**
     * 上传随医拍照片
     * @param fileUploadReqInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData save(FileUploadReqInfo fileUploadReqInfo){
        try{
            MultipartFile[] files = fileUploadReqInfo.getFile();
            if(null == files){
                return ResponseUtil.getFailResultBean(null);
            }
            List<MedicalFile> medicalFiles = new ArrayList<MedicalFile>();
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isEmpty()) {
                    MedicalFile medicalFile = new MedicalFile();
                    String seq = BeanMapUtils.getTableSeqKey(medicalFile);
                    Long id = redisIncrementGenerator.increment(seq,1);
                    byte[] buf = files[i].getBytes();
                    String fileType = files[i].getOriginalFilename().substring(files[i].getOriginalFilename().lastIndexOf(".") + 1);
                    String fileName = files[i].getOriginalFilename();
                    Long fileSize = (long)files[i].getBytes().length;

                    // 启用了fastDFS，将图片上传到FastDFS服务器
                    FastdfsFacade facade = new FastdfsFacade();
                    String filePath = facade.uploadFile(buf, fileName, fileType);

                    medicalFile.setId(id);
                    medicalFile.setFilePath(filePath);
                    medicalFile.setBusFileType(fileType);
                    medicalFile.setFileName(fileName);
                    medicalFile.setFileSize(fileSize);
                    medicalFile.setUploadTime(new Date());
                    medicalFile.setStatus("0");
                    medicalFile.setDoctorCode(fileUploadReqInfo.getDoctorCode());
                    medicalFile.setPatientId(fileUploadReqInfo.getPatientId());
                    medicalFiles.add(medicalFile);
                }
            }
            if(medicalFiles.size() > 0){
                // 保存图片信息到数据库里
                Integer i = medicalFileMapper.insertBatch(medicalFiles);
                if(i == medicalFiles.size()){
                    return ResponseUtil.getSuccessResultBean(i);
                }
            }
            return ResponseUtil.getFailResultBean(null);
        }catch (Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseUtil.getFailResultBean(null);
        }
    }

    /**
     * 获取医生随医拍所有照片
     * @param doctorCode
     * @return
     */
    @Override
    public ResponseData findAllByDoctorCode(String doctorCode,Long patientId){
        try{
            Integer count = medicalFileMapper.getCountByDoctorCode(doctorCode,patientId);
            Map<String, Object> innerDataMap = new HashMap<String, Object>();
            if(count == 0){
                List<ResInfo> innerData = new ArrayList<ResInfo>();
                innerDataMap.put("total",0);
                innerDataMap.put("list",innerData);
                return ResponseUtil.getSuccessResultMap(innerDataMap);
            }
            List<ResInfo> medicalFiles = medicalFileMapper.findAllByDoctorCode(doctorCode);
            innerDataMap.put("total",medicalFiles.size());
            innerDataMap.put("list",medicalFiles);
            if (medicalFiles.size() > 0){
                return ResponseUtil.getSuccessResultMap(innerDataMap);
            }
            return ResponseUtil.getFailResultMapMap();
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseUtil.getFailResultMapMap();
        }
    }

    /**
     * 获取医生随医拍所有照片（分页）
     * @param reqInfo
     * @return
     */
    @Override
    public ResponseData getPagerByDoctorCode(ReqInfo reqInfo) {
        try{
            Integer count = medicalFileMapper.getCountByDoctorCode(reqInfo.getDoctorCode(),reqInfo.getPatientId());
            Map<String, Object> innerDataMap = new HashMap<String, Object>();
            if(count == 0){
                List<ResInfo> innerData = new ArrayList<ResInfo>();
                innerDataMap.put("total",0);
                innerDataMap.put("list",innerData);
                return ResponseUtil.getSuccessResultMap(innerDataMap);
            }
            Pagenation pagenation = new Pagenation(reqInfo.getPageNum(),count,reqInfo.getPageSize());
            List<ResInfo> medicalFiles = medicalFileMapper.getPagerByDoctorCode(reqInfo.getDoctorCode(),reqInfo.getPatientId(),pagenation.getStartRow(),pagenation.getEndRow());
            innerDataMap.put("total",count);
            innerDataMap.put("list",medicalFiles);
            if (medicalFiles.size() > 0){
                return ResponseUtil.getSuccessResultMap(innerDataMap);
            }
            return ResponseUtil.getFailResultMapMap();
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseUtil.getFailResultMapMap();
        }
    }

    /**
     * 删除随医拍照片
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResponseData deleteById(Long id) {
        try{
            if(id != null){
                Integer num = medicalFileMapper.deleteById(id);
                if(1 == num){
                    return ResponseUtil.getSuccessResultBean(num);
                }
            }
            return ResponseUtil.getFailResultMapMap();
        }catch (Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseUtil.getFailResultMapMap();
        }
    }

    /**
     * 批量删除随医拍照片
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public ResponseData deleteBatchByIds(List<Long> ids) {
        try{
            if(0 != ids.size()) {
                int num = medicalFileMapper.deleteBatchByIds(ids);
                if (ids.size() == num) {
                    return ResponseUtil.getSuccessResultBean(num);
                }
            }
            return ResponseUtil.getFailResultMapMap();
        }catch (Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseUtil.getFailResultMapMap();
        }
    }

    /**
     * 根据诊疗卡号或住院号或患者姓名查找患者（住院）信息
     * @param params
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData findPatientInfo(Map<String,Object> params) throws ESBException,SystemException {
        String condition = (String) params.get("condition");
        boolean flag = RegExpressionUtils.isDigit(condition);
        String deptId = (String) params.get("deptId");
        PatientDataRequest patientDataReq = new PatientDataRequest();
        if(flag){
            if(condition.length() > 8){//位数大于8就是诊疗卡号
                long cardNo = Long.valueOf(condition);
                String str = String.format("%020d", cardNo);//数据库里面存储诊疗卡号为20位
                patientDataReq.setPatientCardNo(str);
            }else{
                patientDataReq.setIpSeqNOText(condition);
            }
        }else{
            patientDataReq.setPatientName(condition);
        }
        patientDataReq.setDeptId(deptId);
        ResponseData responseData = publicService.query(ESBServiceCode.OTHERQUERY, patientDataReq, PatientDataResponse.class);
        List<PatientDataResponseRecord> records = new ArrayList<>();
        if(null == responseData.getData()){
            Map<String,Object> map = new HashMap<>();
            map.put("records",records);
            return ResponseUtil.getSuccessResultMap(map);
        }
        PatientDataResponse response = (PatientDataResponse)responseData.getData();
        records = response.getRecords();
        for(PatientDataResponseRecord record : records){
            //计算年龄
            DateTime birthday = TimeUtils.getSdfDate(record.getBirthDate());
            if (birthday != null) {
                DateTime now = new DateTime();
                String strAge = TimeUtils.getAge(birthday,now);
                record.setAge(strAge);
            }
            String patientCardNo = record.getPatientCardNo().replaceAll("^(0+)", "");
            record.setPatientCardNo(patientCardNo);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("records",records);
        return ResponseUtil.getSuccessResultMap(map);
    }
}
