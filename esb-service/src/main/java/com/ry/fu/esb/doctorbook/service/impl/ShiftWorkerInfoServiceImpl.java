package com.ry.fu.esb.doctorbook.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.http.HttpRequest;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.*;
import com.ry.fu.esb.doctorbook.mapper.*;
import com.ry.fu.esb.doctorbook.model.*;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.model.response.*;
import com.ry.fu.esb.doctorbook.service.ShiftWorkerInfoService;
import com.ry.fu.esb.jpush.model.request.PushBarNumberRequest;
import com.ry.fu.esb.jpush.service.DoctoresManualService;
import com.ry.fu.esb.medicaljournal.util.InpatientDayStatus;
import com.ry.fu.esb.medicalpatron.common.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/27 11:01
 * @description 交接班service接口实现
 */
@Service
public class ShiftWorkerInfoServiceImpl implements ShiftWorkerInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ShiftWorkerInfoServiceImpl.class);

    @Autowired
    private PublicService publicService;

    @Autowired
    private ShiftWorkInfoMapper shiftWorkInfoMapper;

    @Autowired
    private PatientCheckRecordMapper patientCheckRecordMapper;

    @Autowired
    private PatientRecordFileMapper patientRecordFileMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private HospitalPatientMapper hospitalPatientMapper;

    @Autowired
    private HandInformMapper handInformMapper;

    @Autowired
    private ShiftDutyStatisticsMapper shiftDutyStatisticsMapper;

    @Autowired
    private DoctoresManualService doctoresManualService;

    @Autowired
    private Environment env;

    @Autowired
    private PtRecordMapper ptRecordMapper;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;
    /**
     * 保存交接班信息(交班)
     *
     * @param shiftWorkInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData saveShiftInfo(ShiftWorkInfo shiftWorkInfo) {
        ResponseData responseData = new ResponseData();
        try {
            String seq = BeanMapUtils.getTableSeqKey(shiftWorkInfo);
            Long id = redisIncrementGenerator.increment(seq, 1);
            shiftWorkInfo.setId(id);
            shiftWorkInfo.setShiftState("0");
            shiftWorkInfo.setShiftTime(new Date());
            int num = shiftWorkInfoMapper.insertSelective(shiftWorkInfo);
            responseData.setStatus("200");
            responseData.setMsg("操作成功");
            responseData.setData(num);
            return responseData;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.error(e.getMessage());
            responseData.setStatus("500");
            responseData.setMsg("操作失败");
            responseData.setData(e.getMessage());
            return responseData;
        }
    }

    /**
     * 更新交接班信息(接班)
     *
     * @param shiftWorkInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateShiftInfo(ShiftWorkInfo shiftWorkInfo) {
        try {
            shiftWorkInfo.setTakeTime(new Date());
            int num = 0;
            if(null !=shiftWorkInfo.getId()){
                shiftWorkInfo.setShiftState("1");
                num = shiftWorkInfoMapper.updateByPrimaryKeySelective(shiftWorkInfo);
            }else {
                //TODO:只为兼容,7月10号后的版本只用id
                 num = shiftWorkInfoMapper.update(shiftWorkInfo.getPatientId(), shiftWorkInfo.getTakeDoctor(), new Date());
            }
            if (num >= 1) {
                return ResponseUtil.getSuccessResultBean(null);
            }
            return ResponseUtil.getFailResultBean(null);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.toString());
            return ResponseUtil.getFailResultBean(null);
        }
    }

    /**
     * 根据科室ID获取医生信息列表
     *
     * @param deptId
     * @return
     */
    @Override
    public ResponseData findDoctorInfo(Integer deptId) {
        DoctorInfomationRequest doctorInfomationRequest = new DoctorInfomationRequest();
        doctorInfomationRequest.setDeptId(deptId);
        try {
            ResponseData responseData = publicService.query(ESBServiceCode.DOCTORINFOQUERY, doctorInfomationRequest, DoctorInfoResponse.class);
            if( null !=responseData.getData()){
                DoctorInfoResponse response = (DoctorInfoResponse) responseData.getData();
                return ResponseUtil.getSuccessResultMap(response.getList());
            }else {
                return ResponseUtil.getSuccessResultBean(null);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseData("500","获取医生信息失败",null);
        }
    }


    /**
     * 保存患者检查非文件记录信息
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData saveShiftDutyRecord(PatientCheckRecordRequest request) {
        logger.info("交接班"+request.toString());

        //ResponseData responseData = new ResponseData();
        if ("1".equals(request.getType())) {
            //保存患者信息
            HospitalPatient hospitalPatient = new HospitalPatient();
            hospitalPatient.setPatientId(request.getPatientId());
            //int count = hospitalPatientMapper.selectCount(hospitalPatient);

            String hospitalPatientSeq = BeanMapUtils.getTableSeqKey(hospitalPatient);
            Long hospitalPatientId = redisIncrementGenerator.increment(hospitalPatientSeq, 1);
            hospitalPatient.setId(hospitalPatientId);
            hospitalPatient.setAge(request.getAge());
            hospitalPatient.setSex(StringUtils.isBlank(request.getSex()) ? 3:Integer.valueOf(request.getSex()));
            //hospitalPatient.setHNo(request.getHNo());
            hospitalPatient.setName(request.getPatientName());
            hospitalPatient.setBedNo(request.getSickBedNo());
            //hospitalPatient.setStatus();
            hospitalPatient.setDiagnosis(request.getStrDiagnosis());
            hospitalPatient.setInHospitalDays(request.getPostoperativeDay());
            hospitalPatient.setResponsibleDoctor(request.getResponsibleDoctor());
            hospitalPatient.setCreateTime(new Date());

            Example example = new Example(HospitalPatient.class);
            example.createCriteria().andEqualTo("patientId", request.getPatientId());
            int updatePatientCount = hospitalPatientMapper.updateByExample(hospitalPatient, example);
            if (updatePatientCount < 1) {
                hospitalPatientMapper.insert(hospitalPatient);
            }

            //更新该患者最近一次接班信息
            shiftWorkInfoMapper.updateLastShiftInfo(request.getPatientId(), request.getDoctorId());

            //接班医生信息
            String doctorIds = request.getAppointDoctors()+"";
            String doctorNames = request.getDoctorTkName()+"";
            String supDoctorIds = request.getSupTakeDoctorId()+"";
            String supDoctorNames = request.getSupTakeDoctorName()+"";
            String deptIds = request.getAppointDeptId()+"";
            String deptNames = request.getAppointDeptName()+"";
            if (StringUtils.isNotBlank(doctorIds)) {
                String[] doctorIdsArray = doctorIds.split("\\,");
                String[] doctorNamesArray = doctorNames.split("\\,",-1);
                String[] supDoctorIdsArray = supDoctorIds.split("\\,",-1);
                String[] supDoctorNamesArray = supDoctorNames.split("\\,",-1);
                String[] deptIdsArray = deptIds.split("\\,");
                String[] deptNamesArray = deptNames.split("\\,");

                for (int i = 0; i < doctorIdsArray.length; i++) {
                    ShiftWorkInfo shiftWorkInfo = new ShiftWorkInfo();
                    String shiftWorkInfoSeq = BeanMapUtils.getTableSeqKey(shiftWorkInfo);
                    Long handInformId = redisIncrementGenerator.increment(shiftWorkInfoSeq, 1);
                    shiftWorkInfo.setId(handInformId);
                    shiftWorkInfo.setShiftDeptId(Long.valueOf(request.getDeptId()));
                    shiftWorkInfo.setShiftGroupId(request.getGroupId());
                    shiftWorkInfo.setShiftDeptName(request.getOrgName());
                    shiftWorkInfo.setShiftDoctor(request.getDoctorId());
                    shiftWorkInfo.setShiftDoctorName(request.getDoctorName());
                    shiftWorkInfo.setSubShiftDoctorId(request.getSubShiftDoctorId());
                    shiftWorkInfo.setSubShiftDoctorName(request.getSubShiftDoctorName());
                    shiftWorkInfo.setShiftTime(new Date());
                    shiftWorkInfo.setShiftState("0");
                    shiftWorkInfo.setPatientId(request.getPatientId());
                    shiftWorkInfo.setHospitalPatientId(hospitalPatientId);
                    shiftWorkInfo.setTakeDeptId(StringUtils.isBlank(deptIdsArray[0])? null : (Long.valueOf(deptIdsArray[0])));
                    shiftWorkInfo.setTakeDeptName(StringUtils.isBlank(deptNames) ? null : deptNamesArray[0]);
                    shiftWorkInfo.setTakeDoctor(Long.valueOf(doctorIdsArray[i]));
                    shiftWorkInfo.setTakeDoctorName(StringUtils.isBlank(doctorNames) ? null : doctorNamesArray[i]);
                    shiftWorkInfo.setSupTakeDoctorId(StringUtils.isBlank(supDoctorIdsArray[i]) ? null : Long.valueOf(supDoctorIdsArray[i]));
                    shiftWorkInfo.setSupTakeDoctorName(StringUtils.isBlank(supDoctorNamesArray[i]) ? null : supDoctorNamesArray[i]);
                    shiftWorkInfo.setShiftWords(request.getShiftWord());
                    shiftWorkInfo.setShiftFlag("1");
                    shiftWorkInfoMapper.insert(shiftWorkInfo);
                }
                //极光推送
                handInfo(request);
            }
            //返回某次交班的唯一标识作为上传文件的标识
            return ResponseUtil.getSuccessResultBean(new HashMap<String,Object>(2){{put("id",hospitalPatientId);}});
        } else {//查房
            PatientCheckRecord patientCheckRecord = copyPatientCheckRecordReqVoToPatientCheckRecord(request);
            int patientCheckRecordNum = patientCheckRecordMapper.insertSelective(patientCheckRecord);
            if (1 != patientCheckRecordNum) {
                return ResponseUtil.getFailResultBean(null);
            }
            Map<String, Object> data = new HashMap<>(2);
            data.put("id", String.valueOf(patientCheckRecord.getId()));
            return ResponseUtil.getSuccessResultBean(data);
        }
    }

    private void handInfo(PatientCheckRecordRequest request) {
        HandInform handInform = new HandInform();
        String handInformSeq = BeanMapUtils.getTableSeqKey(handInform);
        Long handInformId = redisIncrementGenerator.increment(handInformSeq, 1);
        handInform.setId(handInformId);
        handInform.setCreateDate(new Date());

        String titleContent = "";
        String pushInfo = "";
        if ("1".equals(request.getPushTitleType())) {
            titleContent = "交班通知";
            if(StringUtils.isBlank(request.getSubShiftDoctorName())){
                pushInfo = request.getDoctorName() + "医生已向你交班";
            }else {
                pushInfo =( request.getSubShiftDoctorName()+"/"+request.getDoctorName()+"医生已向你交班");
            }
           /* handInform.setPatientId(String.valueOf(request.getPatientId()));
            handInform.setPatientName(request.getPatientName());
            handInform.setDoctorName(request.getDoctorName());
            handInform.setPostoperativeDay(request.getPostoperativeDay());
            handInform.setSickBedNo(request.getSickBedNo());
            handInform.setStrDiagnosis(request.getStrDiagnosis());
            handInform.setAge(request.getAge());
            handInform.setSex(request.getSex());*/
            handInform.setPushTitleType(request.getPushTitleType());

            //交班
        } else if ("2".equals(request.getPushTitleType())) {
            titleContent = "值班交班通知";
            if(StringUtils.isBlank(request.getSubShiftDoctorName())){
                pushInfo = request.getDoctorName() + "医生已向你交班";
            }else {
                pushInfo =( request.getSubShiftDoctorName()+"/"+request.getDoctorName()+"医生已向你交班");
            }
            handInform.setPushTitleType(request.getPushTitleType());

        } /*else if (request.getIsOhterDept() != null
                || StringUtils.isNotBlank(request.getIsOhterDept())
                || "1".equals(request.getIsOhterDept())) {
            titleContent = "交班通知";
            pushInfo = request.getDoctorName() + "已向你交班";
            handInform.setId(handInformId);
            handInform.setPatientId(String.valueOf(request.getPatientId()));
                    handInform.setPatientName(request.getPatientName());
                    handInform.setDoctorName(request.getDoctorName());
                    handInform.setPostoperativeDay(request.getPostoperativeDay());
                    handInform.setSickBedNo(request.getSickBedNo());
                    handInform.setStrDiagnosis(request.getStrDiagnosis());
                    handInform.setAge(request.getAge());
                    handInform.setSex(request.getSex());
            handInform.setPushTitleType(request.getPushTitleType());
            handInform.setCreateDate(new Date());
        }
        if (null != handInform) {
            int result = handInformMapper.insertSelective(handInform);
        }*/
         handInformMapper.insertSelective(handInform);

        Map<String, String> map = new HashMap<>();
        map.put("noticeType", "2");
        List<String> list1 = Arrays.asList(request.getAppointDoctors().split("\\,"));
        Map<String, Object> params = new HashMap<>();
        params.put("doctorbook", "1");//推送的app
        params.put("aliaes", list1);
        params.put("title", titleContent);
        params.put("notification_title", pushInfo);
        params.put("extras", map);
        if(!"pro".equals(env.getActiveProfiles()[0])) {
            params.put("pushTo", "1");
        }
        Header header = restTemplateMethodParamUtil.getPostHeader(params);

        try {
            String result = HttpRequest.post(netServerHostAndPort + Constants.JPUSH_ADDRESS, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON, header);
            /*ResponseData responseData1 = JsonUtils.readValue(result, ResponseData.class);*/
            logger.info("交接班推送结果"+ result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("极光推送错误");
        }

        PushBarNumberRequest pushBarNumberRequest = new PushBarNumberRequest();
        for (int i = 0; i < list1.size(); i++) {
            pushBarNumberRequest.setPersonId(list1.get(i));
            pushBarNumberRequest.setNoticeType(2);
            doctoresManualService.savePushBarNumber(pushBarNumberRequest);
        }
    }

    /**
     * 保存患者检查文件记录信息
     *
     * @param patientCheckRecordFileRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData uploadFile(PatientCheckRecordFileRequest patientCheckRecordFileRequest) {
        try {
            MultipartFile[] shiftPicture = patientCheckRecordFileRequest.getShiftPicture();
            MultipartFile[] shiftVoice = patientCheckRecordFileRequest.getShiftVoice();
            List<String> picFilePaths = null;
            if (null != shiftPicture) {
                picFilePaths = FileUtil.fileUpload(shiftPicture);
                if (null == picFilePaths) {
                    return ResponseUtil.getFailResultBean(null);
                }
            }
            List<String> voiceFilePaths = null;
            if (null != shiftVoice) {
                voiceFilePaths = FileUtil.fileUpload(shiftVoice);
                if (null == voiceFilePaths) {
                    return ResponseUtil.getFailResultBean("文件上传失败");
                }
            }

            List<PatientRecordFile> patientRecordFiles = new ArrayList<>();
            if (null != picFilePaths) {
                for (String picFilePath : picFilePaths) {
                    PatientRecordFile patientRecordFile = new PatientRecordFile();
                    String seq = BeanMapUtils.getTableSeqKey(patientRecordFile);
                    Long id = redisIncrementGenerator.increment(seq, 1);
                    patientRecordFile.setId(id);
                    patientRecordFile.setFilePath(picFilePath);
                    patientRecordFile.setFlag("0");
                    patientRecordFile.setPatientRecordId(Long.parseLong(patientCheckRecordFileRequest.getPatientRecordId()));
                    patientRecordFile.setUploadTime(new Date());
                    patientRecordFiles.add(patientRecordFile);
                }
            }
            if (null != voiceFilePaths) {
                for (String voiceFilePath : voiceFilePaths) {
                    PatientRecordFile patientRecordFile = new PatientRecordFile();
                    String seq = BeanMapUtils.getTableSeqKey(patientRecordFile);
                    Long id = redisIncrementGenerator.increment(seq, 1);
                    patientRecordFile.setId(id);
                    patientRecordFile.setFilePath(voiceFilePath);
                    patientRecordFile.setFlag("1");
                    patientRecordFile.setPatientRecordId(Long.parseLong(patientCheckRecordFileRequest.getPatientRecordId()));
                    patientRecordFile.setUploadTime(new Date());
                    patientRecordFiles.add(patientRecordFile);
                }
            }
            if (patientRecordFiles.size() > 0) {
                int patientRecordNum = patientRecordFileMapper.insertBatch(patientRecordFiles);
                if (patientRecordFiles.size() != patientRecordNum) {
                    return ResponseUtil.getFailResultBean("文件上传失败");
                }
            }
            if (patientRecordFiles.size() == 0) {
                return ResponseUtil.getFailResultBean("文件为空");
            }
            return ResponseUtil.getSuccessResultBean(patientRecordFiles.size());
        } catch (Exception e) {
            logger.error("============上传文件错误============"+e.toString());
            return ResponseUtil.getFailResultBean("文件上传失败");
        }
    }

    /**
     * 根据医生姓名获取医生信息(分页)
     * @param doctocInfo
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData findDoctorInfoByName(DoctorInfoByPageRequest doctocInfo) throws ESBException, SystemException {
        ResponseData responseData = publicService.query(ESBServiceCode.DOCTORINFOBYNAME,doctocInfo,DoctorInfoByPageResponse.class);
        if(null == responseData.getData()){
            Map<String,Object> map = new HashMap<>();
            map.put("records",new ArrayList<>());
            responseData.setData(map);
            return responseData;
        }
        return responseData;
    }

    @Override
    public ResponseData patientStatistics(Map<String, String> map) {
        PageHelper.startPage(Integer.parseInt(map.get("pageNum")), Integer.parseInt(map.get("pageSize")));
        List<PatientStatisticsVO> resultList = shiftDutyStatisticsMapper.selectPatientStatisticsByDeptId(map.get("orgId"), map.get("date"));
        PageInfo pageInfo = new PageInfo<>(resultList);
        return ResponseUtil.getSuccessResultBean(pageInfo);
    }

    @Override
    public ResponseData departmentStatistics(Map<String, String> map) {
        PageHelper.startPage(Integer.parseInt(map.get("pageNum")), Integer.parseInt(map.get("pageSize")));
        List<DepartmentShiftedStatisticsVO> resultList = shiftDutyStatisticsMapper.selectDepartmentStatistics(map.get("date"));
        PageInfo pageInfo = new PageInfo<>(resultList);
        return ResponseUtil.getSuccessResultBean(pageInfo);
    }

    @Override
    public ResponseData findHospital() {
        List<HashMap<String, String>> mapList = shiftDutyStatisticsMapper.findHospital();
        return ResponseUtil.getSuccessResultBean(mapList);
    }

    @Override
    public ResponseData findOrgList(String hospitalId) {
        Integer orgPid = 1;
        if("1".equals(hospitalId)){
            orgPid=2536;
        }else if("2".equals(hospitalId)){
            orgPid=2986;
        }
        List<HashMap<String, String>> mapList = shiftDutyStatisticsMapper.findOrgList(orgPid);
        return ResponseUtil.getSuccessResultBean(mapList);
    }

    @Override
    public ResponseData cancelShift(Map<String, String> map) {
        if(StringUtils.isBlank(map.get("id"))||StringUtils.isBlank(map.get("doctorId"))){
            return new ResponseData("400", "缺失必要参数,删除失败!",null);
        }
        Example example = new Example(ShiftWorkInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(map.get("subShiftDoctorId"))){
            criteria.andEqualTo("subShiftDoctorId",map.get("subShiftDoctorCode"));
        }
        criteria.andEqualTo("id",map.get("id")).andEqualTo("shiftDoctor",map.get("doctorId"))
                .andEqualTo("shiftState",0);
        int row = shiftWorkInfoMapper.deleteByExample(example);
        if(row == 1){
            return ResponseUtil.getSuccessResultMap(new HashMap<String, Object>(){{put("resultDesc","取消成功");}});
        }else {
            return new ResponseData("400", "删除失败!您只能删除您本人的交班记录,且对方未接班。",null);
        }
    }

    @Override
    public ResponseData findSuperiorOrg(String deptId) {
        HashMap<String, String> superiorOrg = shiftDutyStatisticsMapper.findSuperiorOrg(deptId);
        return ResponseUtil.getSuccessResultBean(superiorOrg);
    }

    @Override
    public ResponseData delRoundRecord(Map<String,String> map) {
        Example example = new Example(PtRecord.class);
        Example.Criteria criteria = example.createCriteria();
        Example.Criteria criteria1 = example.createCriteria();
        Example.Criteria criteria2 = example.createCriteria();
        criteria.andEqualTo("id",map.get("id"));

        if(StringUtils.isNotEmpty(map.get("doctorId"))){
            criteria1.andEqualTo("doctorId",map.get("doctorId"));
            example.and(criteria1);
        }
        if(StringUtils.isNotEmpty(map.get("subDoctorId"))){
            criteria2.andEqualTo("subDoctorId",map.get("subDoctorId"));
            example.and(criteria2);
        }
        int result=ptRecordMapper.deleteByExample(example);
        if(result == 1){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),result);
        }else {
            return new ResponseData("400", "删除失败!您只能删除您本人的查房记录。",null);
        }

    }


    /**
     * 获取患者检查记录信息(分页)
     *
     * @param patientRecordPagerRequest
     * @return
     */
    @Override
    public ResponseData findCheckRecordInfoByPager(PatientRecordPagerRequest patientRecordPagerRequest) {
        logger.info("patientRecordPagerRequest"+JsonUtils.toJSon(patientRecordPagerRequest));
        ResponseData responseData = new ResponseData();
        List<Map<String, Object>> recordDetails = new ArrayList<>();
        //PatientCheckRecord patientRecord = new PatientCheckRecord();
        Map<String, Object> result = new HashMap<>(4);

        //1.交接班
        if ("1".equals(patientRecordPagerRequest.getType())) {
            Example example = new Example(ShiftWorkInfo.class);
            example.setOrderByClause("shift_Time desc");
            example.createCriteria().andEqualTo("patientId",patientRecordPagerRequest.getPatientId());
            PageHelper.startPage(patientRecordPagerRequest.getPageNumber(), patientRecordPagerRequest.getPageSize());
            List<ShiftWorkInfo> shiftWorkInfoList = shiftWorkInfoMapper.selectByExample(example);
            PageInfo<ShiftWorkInfo> pageInfo = new PageInfo<>(shiftWorkInfoList);

            if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
                for (ShiftWorkInfo shiftWorkInfo1 : pageInfo.getList()) {
                    //查询文件信息
                    List<PatientRecordFile> patientRecordFiles = patientRecordFileMapper.findFileByRecordId(shiftWorkInfo1.getHospitalPatientId());
                    List<String> shiftPictureList = new ArrayList<>();
                    List<String> shiftVoiceList = new ArrayList<>();

                    if (patientRecordFiles != null && patientRecordFiles.size() > 0) {
                        for (PatientRecordFile patientRecordFile : patientRecordFiles) {
                            if ("0".equals(patientRecordFile.getFlag())) {
                                shiftPictureList.add(patientRecordFile.getFilePath());
                            } else if ("1".equals(patientRecordFile.getFlag())) {
                                shiftVoiceList.add(patientRecordFile.getFilePath());
                            }
                        }
                    }
                    Map<String, Object> map = new HashMap<>(8);
                    map.put("id",shiftWorkInfo1.getId());
                    map.put("doctorId", shiftWorkInfo1.getShiftDoctor());
                    map.put("createTime", shiftWorkInfo1.getShiftTime());
                    map.put("shiftPictures", shiftPictureList);
                    if(StringUtils.isBlank(shiftWorkInfo1.getSubShiftDoctorName())){
                        map.put("doctorName", shiftWorkInfo1.getShiftDoctorName());
                    }else {
                        map.put("doctorName", shiftWorkInfo1.getSubShiftDoctorName()+"/"+shiftWorkInfo1.getShiftDoctorName()+"医生");
                    }
                    map.put("shiftVoices", shiftVoiceList);
                    map.put("shiftWord", shiftWorkInfo1.getShiftWords());
                    recordDetails.add(map);

                }
            }
            result.put("total", pageInfo.getPages());

        } else {//2.查房
            Example example = new Example(PatientCheckRecord.class);
            example.setOrderByClause("CREATE_TIME DESC");
            example.createCriteria().andEqualTo("type",patientRecordPagerRequest.getType())
                    .andEqualTo("patientId",patientRecordPagerRequest.getPatientId());
            PageHelper.startPage(patientRecordPagerRequest.getPageNumber(), patientRecordPagerRequest.getPageSize());
            List<PatientCheckRecord> patientCheckRecords = patientCheckRecordMapper.selectByExample(example);
            PageInfo<PatientCheckRecord> pageInfo = new PageInfo<>(patientCheckRecords);

            if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
                for (PatientCheckRecord patientCheckRecord : pageInfo.getList()) {
                    //查询文件信息
                    List<PatientRecordFile> patientRecordFiles = patientRecordFileMapper.findFileByRecordId(patientCheckRecord.getId());
                    //List<PatientRecordFile> patientRecordFiles = patientCheckRecord.getPatientRecordFiles();
                    List<String> shiftPictureList = new ArrayList<>();
                    List<String> shiftVoiceList = new ArrayList<>();
                    if (patientRecordFiles != null && patientRecordFiles.size() > 0) {
                        for (PatientRecordFile patientRecordFile : patientRecordFiles) {
                            if ("0".equals(patientRecordFile.getFlag())) {
                                shiftPictureList.add(patientRecordFile.getFilePath());
                            } else if ("1".equals(patientRecordFile.getFlag())) {
                                shiftVoiceList.add(patientRecordFile.getFilePath());
                            }
                        }
                    }
                    Map<String, Object> map = new HashMap<>(8);
                    map.put("id",patientCheckRecord.getId());
                    map.put("doctorId", patientCheckRecord.getDoctorId());
                    map.put("createTime", patientCheckRecord.getCreateTime());
                    map.put("shiftPictures", shiftPictureList);
                    if(StringUtils.isBlank(patientCheckRecord.getSubDoctorName())){
                        map.put("doctorName", patientCheckRecord.getDoctorName());
                    }else {
                        map.put("doctorName", patientCheckRecord.getSubDoctorName()+"/"+patientCheckRecord.getDoctorName()+"医生");
                    }
                    map.put("doctorName", patientCheckRecord.getDoctorName());
                    map.put("shiftVoices", shiftVoiceList);
                    map.put("shiftWord", patientCheckRecord.getShiftWord());
                    recordDetails.add(map);
                }

            }
            result.put("total", pageInfo.getPages());
        }
        result.put("recordDetails", recordDetails);
        responseData.setStatus("200");
        responseData.setMsg("操作成功");
        responseData.setData(result);
        return responseData;
    }

    /**
     * 获取患者列表信息
     * @param request
     * @return
     */
    @Override
    public ResponseData findPatientInfo(InpatientInfoRequest request) {
        logger.info("findPatientInfo{}",request);
        PatientInformationResponse patientInfos = null;
        Map responseMap = new HashMap(2);
        try {
            if ("0".equals(request.getQueryType())) {//交班列表
                if (!"4".equals(request.getPatientType())) {
                    patientInfos = findPatientInfoFromESB(request);
                }else {//其他科室
                    responseMap.put("inpatientInfoDetailResponse",shiftWorkInfoMapper.findOtherDeptShiftInfoList
                            (request.getDepartmentId(),request.getDoctorId()));
                    return ResponseUtil.getSuccessResultBean(responseMap);
                }
            } else if ("1".equals(request.getQueryType())) {//接班列表

                if ("1".equals(request.getPatientType()) ) {//本科室
                    responseMap.put("inpatientInfoDetailResponse",shiftWorkInfoMapper.findThisDeptTakeInfoList
                            (request.getDepartmentId(),request.getDoctorId()));
                }
                if ("2".equals(request.getPatientType()) ) {//本组
                    responseMap.put("inpatientInfoDetailResponse",shiftWorkInfoMapper.findThisGroupTakeInfoList
                            (request.getGroupId(),request.getDoctorId()));
                }
                if ("4".equals(request.getPatientType()) ) {//其他科室
                    responseMap.put("inpatientInfoDetailResponse",shiftWorkInfoMapper.findOtherDeptTakeInfoList
                            (request.getDepartmentId(),request.getDoctorId()));
                }
                if ("3".equals(request.getPatientType()) ) {//我的患者
                    patientInfos = findPatientInfoFromESB(request);
                    if(null != patientInfos && null != patientInfos.getInpatientInfoDetailResponse()
                            && patientInfos.getInpatientInfoDetailResponse().size() >0){
                        List<String> patientIdList = new ArrayList();
                        for(PatientInformationResponseRecord p:patientInfos.getInpatientInfoDetailResponse()){
                           if(null != p && null != p.getPatientId()){
                               patientIdList.add(p.getPatientId());
                           }
                        }
                        Map map = new HashMap();
                        map.put("doctorId",request.getDoctorId());
                        map.put("list",patientIdList);
                        responseMap.put("inpatientInfoDetailResponse",shiftWorkInfoMapper.findMyTakeInfoList(map));
                    }
                }
                return  ResponseUtil.getSuccessResultBean(responseMap);
            }
            return ResponseUtil.getSuccessResultBean(patientInfos);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            return ResponseUtil.getFailResultBean(null);
        }
    }

    private ResponseData getOtherDeptShiftData(InpatientInfoRequest patientInformationRequest) {
        //分为交班列表和接班列表
        Example example = new Example(ShiftWorkInfo.class);
        Example.Criteria criteria = example.createCriteria();
        Example.Criteria criteria2 = example.createCriteria();
        //交班列表
        if ("0".equals(patientInformationRequest.getQueryType())) {
            //1.本医生交给其他科室的--显示已交班
            criteria.andEqualTo("shiftDoctor", patientInformationRequest.getDoctorId());
            criteria.andNotEqualTo("takeDeptId", patientInformationRequest.getDepartmentId());
            criteria.andGreaterThan("shiftTime", DateUtils.addHours(new Date(), -12));
            //criteria.andEqualTo("shiftState","0");
            //2.本医生从其他科室接班的
            criteria2.andEqualTo("takeDoctor", patientInformationRequest.getDoctorId());
            criteria2.andEqualTo("shiftState", "1");
            criteria2.andEqualTo("shiftAgain", null);
            criteria2.andNotEqualTo("shiftDeptId", patientInformationRequest.getDepartmentId());

            Example.Criteria criteria3 = example.createCriteria();
            criteria3.andEqualTo("supTakeDoctorId",patientInformationRequest.getDoctorId());
            criteria3.andEqualTo("shiftState", "1");
            criteria3.andEqualTo("shiftAgain", null);
            criteria3.andNotEqualTo("shiftDeptId", patientInformationRequest.getDepartmentId());
            example.or(criteria2);
            example.or(criteria3);

        }
        //接班列表--未接班的其他科室的患者
        /*if ("1".equals(patientInformationRequest.getQueryType())) {
            criteria.andEqualTo("takeDoctor", patientInformationRequest.getDoctorId())
                    .orEqualTo("supTakeDoctorId",patientInformationRequest.getDoctorId());
            criteria2.andEqualTo("shiftState", "0");
            criteria2.andNotEqualTo("shiftDeptId", patientInformationRequest.getDepartmentId());
            criteria2.andGreaterThan("shiftTime", DateUtils.addDays(new Date(),-1));
            example.and(criteria2);
        }*/
        List<ShiftWorkInfo> shiftWorkInfoList = shiftWorkInfoMapper.selectByExample(example);
        List<PatientInformationResponseRecord> otherDeptPatientRecordList = new ArrayList<>();
        PatientInformationResponse patientInformationResponse = new PatientInformationResponse();

        if (shiftWorkInfoList != null && shiftWorkInfoList.size() > 0) {
            for (ShiftWorkInfo shiftInfo : shiftWorkInfoList) {
                Long patientId = shiftInfo.getPatientId();
                Example example1 = new Example(HospitalPatient.class);
                example1.createCriteria().andEqualTo("patientId",patientId);
                List<HospitalPatient> hospitalPatientList = hospitalPatientMapper.selectByExample(example1);
                if (hospitalPatientList != null && hospitalPatientList.size()>0) {
                    HospitalPatient hospitalPatient = hospitalPatientList.get(0);
                    PatientInformationResponseRecord otherDeptPatientRecord = new PatientInformationResponseRecord();
                    otherDeptPatientRecord.setPatientId(String.valueOf(hospitalPatient.getPatientId()));
                    otherDeptPatientRecord.setPatientName(hospitalPatient.getName());
                    otherDeptPatientRecord.setSexFlag(String.valueOf(hospitalPatient.getSex()));
                    otherDeptPatientRecord.setAge(hospitalPatient.getAge());
                    otherDeptPatientRecord.setSickBedNo(hospitalPatient.getBedNo());
                    otherDeptPatientRecord.setPostoperativeDay(hospitalPatient.getInHospitalDays());
                    otherDeptPatientRecord.setStrDiagnosis(hospitalPatient.getDiagnosis());
                    if ("1".equals(shiftInfo.getShiftAgain()) || shiftInfo.getShiftDoctor().equals(patientInformationRequest.getDoctorId())) {
                        otherDeptPatientRecord.setShiftStatus("1");
                    } else {
                        otherDeptPatientRecord.setShiftStatus("0");
                    }
                    otherDeptPatientRecord.setTakeStatus(shiftInfo.getShiftState());
                    otherDeptPatientRecordList.add(otherDeptPatientRecord);
                }
            }
            patientInformationResponse.setInpatientInfoDetailResponse(otherDeptPatientRecordList);
        }
        return ResponseUtil.getSuccessResultBean(patientInformationResponse);
    }

    /**
     * 从ESB中获取病人信息
     *
     * @param inpatientInfoRequest
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    private PatientInformationResponse findPatientInfoFromESB(InpatientInfoRequest inpatientInfoRequest) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData esbResponseData = publicService.query(ESBServiceCode.INPATIENTINFO, inpatientInfoRequest, PatientInformationResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        if (null == esbResponseData.getData()) {
            return null;
        }
        PatientInformationResponse patientInfoResponse = (PatientInformationResponse) esbResponseData.getData();
        if (null == patientInfoResponse || null == patientInfoResponse.getInpatientInfoDetailResponse()) {
            return null;
        }
        List<PatientInformationResponseRecord> patientInfoRecords = patientInfoResponse.getInpatientInfoDetailResponse();

        Iterator<PatientInformationResponseRecord> iterator = patientInfoRecords.iterator();

        String nurseFlag = "";
        while (iterator.hasNext()) {
            PatientInformationResponseRecord patient = iterator.next();
            if ("1".equals(patient.getChangeDeptFlag())) {
                iterator.remove();
            }
        }

        for (PatientInformationResponseRecord record : patientInfoRecords) {
            //护理等级
            if (StringUtils.isNotBlank(record.getNurseFlag()) && "1".equals(record.getNurseFlag()) && "4".equals(record.getNurseFlag())) {
                record.setInpatientDayStatus(0);
            } else {
                //计算年龄
                DateTime birthday = TimeUtils.getSdfDate(record.getBirthday());
                if (birthday != null) {
                    DateTime now = new DateTime();
                    String strAge = TimeUtils.getAge(birthday, now);
                    record.setAge(strAge);
                }
                //入院诊断
                if (null == record.getPatientDiagnos()) {
                    continue;
                } else {
                    ArrayList<String> diagnosisArry = new ArrayList<>();
                    StringBuilder strdiagnosis = new StringBuilder();
                    for (InpatientInfoDiagnose diagnose : record.getPatientDiagnos()
                            ) {
                        if ("1".equals(diagnose.getMode()) && "1".equals(diagnose.getDiagnoseType())) {
                            diagnosisArry.add(diagnose.getDescription());
                        }
                    }
                    if (null == diagnosisArry) {
                        continue;
                    } else {
                        //入院诊断去重
                        ArrayList diagnosisNew = new ArrayList(new HashSet(diagnosisArry));
                        record.setPreliminaryDiagnosis(diagnosisNew);
                        //入院诊断拼接字符串
                        for (Object sb : diagnosisNew
                                ) {
                            strdiagnosis.append(sb);
                            strdiagnosis.append(",");
                        }
                        if (StringUtils.isBlank(strdiagnosis)) {
                            record.setStrDiagnosis("暂无数据");
                            continue;
                        }
                        record.setStrDiagnosis(strdiagnosis.toString().substring(0, strdiagnosis.length() - 1));
                    }
                }

                DateTime operationTime;
                DateTime behospitalTime;
                behospitalTime = TimeUtils.getSdfTime(record.getInDate());
                if (behospitalTime != null) {
                    int inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurrentTime().withMillisOfDay(0));
                    if (inpatientDay == 0) {
                        inpatientDay = 1;
                    }
                    if (!"1".equals(record.getNurseFlag())) {
                        record.setInpatientDayStatus(0);
                    }

                    record.setInpatientDayStatus(InpatientDayStatus.getInpatientDayStatus(inpatientDay));

                    if (StringUtils.isNotBlank(record.getLcljFlag()) && "1".equals(record.getLcljFlag())) {
                        record.setPatientNameFlag("1");
                        record.setPatientName(record.getPatientName());
                        record.setPatientFlag("路径");
                    } else if (StringUtils.isNotBlank(record.getChangeDeptFlag()) && "1".equals(record.getChangeDeptFlag())) {
                        record.setPatientNameFlag("2");
                        record.setPatientFlag("转科");
                    }


                    //护理等级一级或者四级
                    if ("1".equals(record.getNurseFlag()) || "4".equals(record.getNurseFlag())) {
                        record.setPatientStatus("0");
                    } else {
                        //新增执行医嘱时间
                        if (StringUtils.isNotBlank(record.getDaInputDateTime())) {
                            DateTime daInputDateTime = TimeUtils.getSdfTime(record.getDaInputDateTime());
                            int dateFlag = TimeUtils.getHours(new DateTime().withMillisOfDay(0), daInputDateTime);

                            if (0 == dateFlag) {
                                record.setDaInputDate(record.getDaInputDateTime().substring(5, 10));
                                record.setPatientStatus("1");
                            }
                        } else {
                            if ("1".equals(String.valueOf(inpatientDay))) {
                                record.setPatientStatus("2");
                            } else {
                                record.setPatientStatus("4");
                            }


                        }
                    }

                    //术后天数
                    if (null != record.getOperationTime()) {
                        operationTime = TimeUtils.getSdfDate(record.getOperationTime());
                        int count;
                        if (null == record.getIpTimes()) {
                            count = 1;
                        } else {
                            count = Integer.parseInt(record.getIpTimes());
                        }
                        int postDay = TimeUtils.getDays(operationTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                        if (postDay == 0) {
                            postDay = 1;
                        }
                        if (postDay > 0) {
                            String postoperativeDay = "第" + count + "次手术后：" + postDay + "天";
                            record.setPostoperativeDay(postoperativeDay);
                        } else {
                            if (null != record.getInDate()) {
                                behospitalTime = TimeUtils.getSdfTime(record.getInDate());
                                inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                                if (inpatientDay == 0) {
                                    inpatientDay = 1;
                                }
                                String hospitalDay = "住院第：" + inpatientDay + "天";
                                record.setPostoperativeDay(hospitalDay);
                            }
                        }
                    } else {
                        //住院天数
                        if (null != record.getInDate() && null == record.getOperationTime()) {
                            behospitalTime = TimeUtils.getSdfTime(record.getInDate());
                            if (null != behospitalTime) {
                                inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                                if (inpatientDay == 0) {
                                    inpatientDay = 1;
                                }
                                String hospitalDay = "住院第：" + inpatientDay + "天";
                                record.setPostoperativeDay(hospitalDay);
                            }
                        }
                    }
                }
            }
        }
        patientInfoResponse.setInpatientInfoDetailResponse(patientInfoRecords);
        return patientInfoResponse;
    }

    /**
     * PatientInformationRequest copy到 InpatientInfoRequest
     *
     * @param patientInformationRequest
     * @return
     */
    private InpatientInfoRequest copyPatientInfoReqVoToInpatientInfoRequest(PatientInformationRequest patientInformationRequest) {
        InpatientInfoRequest inpatientInfoRequest = new InpatientInfoRequest();
        inpatientInfoRequest.setDepartmentId(patientInformationRequest.getDepartmentId());
        inpatientInfoRequest.setDoctorCode(patientInformationRequest.getDoctorCode());
        inpatientInfoRequest.setPatientType(patientInformationRequest.getPatientType());
        return inpatientInfoRequest;
    }

    /**
     * PatientCheckRecordRequest copy到 patientCheckRecord
     *
     * @param patientCheckRecordRequest
     * @return
     */
    private PatientCheckRecord copyPatientCheckRecordReqVoToPatientCheckRecord(PatientCheckRecordRequest patientCheckRecordRequest) {
        PatientCheckRecord patientCheckRecord = new PatientCheckRecord();
        String seq = BeanMapUtils.getTableSeqKey(patientCheckRecord);
        Long id = redisIncrementGenerator.increment(seq, 1);
        patientCheckRecord.setId(id);
        patientCheckRecord.setPatientId(patientCheckRecordRequest.getPatientId());
        patientCheckRecord.setDoctorId(patientCheckRecordRequest.getDoctorId());
        patientCheckRecord.setShiftVisible(patientCheckRecordRequest.getShiftVisible());
        patientCheckRecord.setCreateTime(new Date());
        patientCheckRecord.setShiftWord(patientCheckRecordRequest.getShiftWord());
        patientCheckRecord.setType(patientCheckRecordRequest.getType());
        patientCheckRecord.setDoctorName(patientCheckRecordRequest.getDoctorName());
        patientCheckRecord.setDeptId(patientCheckRecordRequest.getDeptId());
        patientCheckRecord.setGroupId(patientCheckRecordRequest.getGroupId());
        patientCheckRecord.setSubDoctorId(patientCheckRecordRequest.getSubShiftDoctorId());
        patientCheckRecord.setSubDoctorName(patientCheckRecord.getSubDoctorName());
        return patientCheckRecord;
    }
}
