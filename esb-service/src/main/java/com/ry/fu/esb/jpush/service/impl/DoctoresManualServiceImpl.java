package com.ry.fu.esb.jpush.service.impl;

import com.github.pagehelper.PageHelper;
import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.http.HttpRequest;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.RestTemplateMethodParamUtil;
import com.ry.fu.esb.doctorbook.model.request.InpatientBasicRequest;
import com.ry.fu.esb.doctorbook.model.request.InspectionReportRequest;
import com.ry.fu.esb.doctorbook.model.request.OtherQueryRequest;
import com.ry.fu.esb.doctorbook.model.response.*;
import com.ry.fu.esb.doctorbook.service.IPService;
import com.ry.fu.esb.jpush.mapper.*;
import com.ry.fu.esb.jpush.model.*;
import com.ry.fu.esb.jpush.model.request.*;
import com.ry.fu.esb.jpush.model.response.PushBarNumberResponse;
import com.ry.fu.esb.jpush.model.response.SendCriticalValuesHandlerResponse;
import com.ry.fu.esb.jpush.model.response.SendCriticalValuesRelieveResponse;
import com.ry.fu.esb.jpush.model.response.SendCriticalValuesRelieveResponseRecord;
import com.ry.fu.esb.jpush.service.DoctoresManualService;
import com.ry.fu.esb.medicaljournal.enums.PushType;
import com.ry.fu.esb.medicaljournal.mapper.ContactsMapper;
import com.ry.fu.esb.medicaljournal.mapper.PatientMapper;
import com.ry.fu.esb.medicaljournal.mapper.PushListMapper;
import com.ry.fu.esb.medicaljournal.model.Contacts;
import com.ry.fu.esb.medicaljournal.model.Patient;
import com.ry.fu.esb.medicaljournal.model.PushList;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26
 * @description 危急值controller
 */
@Service
public class DoctoresManualServiceImpl implements DoctoresManualService {

    private static Logger logger = LoggerFactory.getLogger(DoctoresManualServiceImpl.class);

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private IPService ipService;

    @Autowired
    private PatientInformationMapper patientInformationMapper;

    @Autowired
    private CrisisProjectMapper crisisProjectMapper;

    @Autowired
    private PublicService publicService;

    @Autowired
    private CriticalValueProcessingMapper criticalValueProcessingMapper;

    @Autowired
    private RelieveToExamineMapper relieveToExamineMapper;

    @Autowired
    private RelieveToExamineItemCodeMapper relieveToExamineItemCodeMapper;

    @Autowired
    private PushBarNumberMapper pushBarNumberMapper;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PushListMapper pushListMapper;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 危急值通知(供平台调用)
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData sendCriticalValues(SendCriticalValuesRequest request) throws SystemException, ESBException, ParseException {

        Map<String, Object> redisTemplateMap = new HashMap<>();
        PatientInformation patientInformation = new PatientInformation();
        //判断是住院患者还是门诊患者
        if (StringUtils.isNotBlank(request.getIpTimes())) {
            InpatientBasicRequest inpatientBasicRequest = new InpatientBasicRequest();
            inpatientBasicRequest.setIpTimes(request.getIpTimes().toString());
            inpatientBasicRequest.setIpseqNoText(request.getIpSeqnoText());
            //根据住院次数、住院号来查询住院患者基础信息
            ResponseData data = ipService.inpatientBasic(inpatientBasicRequest);
            if (data.getData() != null) {
                InpatientBasicResponse inpatientBasicResponse = (InpatientBasicResponse) data.getData();
                if (inpatientBasicResponse != null && inpatientBasicResponse.getInpatientBasicResponse() != null) {
                    List<InpatientBasicResponseRecode> inpatientBasicResponseRecodes = inpatientBasicResponse.getInpatientBasicResponse();
                    if (inpatientBasicResponseRecodes != null) {
                        for (InpatientBasicResponseRecode inpatientBasicResponseRecode : inpatientBasicResponseRecodes) {
                            patientInformation.setSex(inpatientBasicResponseRecode.getSex());//性别
                            patientInformation.setAge(inpatientBasicResponseRecode.getAge());//年龄
                            patientInformation.setInpatientArea(inpatientBasicResponseRecode.getInpatientCategory());//科室
                            patientInformation.setSeat(inpatientBasicResponseRecode.getSickBedNo());//床号
                        }
                        patientInformation.setPatientFlag("2");//表示住院患者
                    }
                }
            }
        } else {
            OtherQueryRequest otherQueryRequest = new OtherQueryRequest();
            otherQueryRequest.setPatientName(request.getPatientName());
            otherQueryRequest.setPatientFlag("1");
            ResponseData otherQueryResponse = ipService.otherQueryOld(otherQueryRequest);
            OtherQueryResponse OtherqueryRecord = (OtherQueryResponse) otherQueryResponse.getData();
            if (OtherqueryRecord != null && OtherqueryRecord.getHospitalPatientsResponseRecord() != null) {
                for (OtherqueryRecord otherqueryRecord : OtherqueryRecord.getHospitalPatientsResponseRecord()) {
                    if (request.getPatientId().equals(otherqueryRecord.getPatientId())) {
                        patientInformation.setSex(otherqueryRecord.getSexFlag());//性别
                        patientInformation.setAge(otherqueryRecord.getAge());//年龄
                    }
                }
                patientInformation.setPatientFlag("1");//表示门诊患者
            }
        }
        patientInformation.setPatientId(request.getPatientId());
        patientInformation.setCreateTime(new Date());
        patientInformation.setSender("系统");
        patientInformation.setIsHandler("0");
        patientInformation.setUpdateTime(new Date());
        //TODO
        patientInformation.setIpTimes(request.getIpTimes());
        patientInformation.setIpSeqNoText(request.getIpSeqnoText());
        patientInformation.setInpatientId(request.getInPatientId());
        patientInformation.setLisLableNo(request.getLisLableNo());
        patientInformation.setExamineRequestId(request.getExamineRequestId());
        patientInformation.setPatientName(request.getPatientName());
        patientInformation.setNoticeType("1");
        if (request.getNoteList() != null) {
            for (NoteList noteList : request.getNoteList()) {
                redisTemplate.opsForList().leftPush(noteList.getPersonId() + "examineRequestId", request.getExamineRequestId());
                redisTemplate.opsForList().leftPush(noteList.getPersonId() + "lisLableNo", request.getLisLableNo());
                logger.info(noteList.getPersonId() + "examineRequestId");
                logger.info(noteList.getPersonId() + "lisLableNo");
                String patientInformationKey = BeanMapUtils.getTableSeqKey(patientInformation);
                Long patientInformationId = redisIncrementGenerator.increment(patientInformationKey, 1);
                patientInformation.setId(patientInformationId);
                patientInformation.setOpendId(noteList.getPersonId());
                patientInformation.setPersonId(noteList.getPersonId());
                patientInformation.setDoctorCode(noteList.getPersonNo());
                patientInformation.setPersonName(noteList.getPersonName());
                patientInformation.setNoTelevel(noteList.getNoTelevel());
                patientInformation.setNoTetype(noteList.getNoTeType());
                patientInformation.setNoTetimeout(noteList.getNoTetimeOut());
                patientInformation.setCrisisTime(new Date());
                //保存时，判断该标本号和该病人id是否已存在
                if (request.getItemList() != null) {
                    int count = patientInformationMapper.selectByLisLableNoCount(request.getLisLableNo(), noteList.getPersonId());
                    if (count <= 0) {
                        logger.info("保存危急值患者信息：json{}," + JsonUtils.toJSon(patientInformation));
                        patientInformationMapper.insertSelective(patientInformation);
                    } else {
                        logger.info("不保存危急值患者信息：json{}," + JsonUtils.toJSon(patientInformation));
                    }
                }
            }
        }
        //设置请求查询检验项目信息
        InspectionReportRequest inspectionReportRequest = new InspectionReportRequest();
        inspectionReportRequest.setExamineRequestId(request.getExamineRequestId().toString());
        String itemSet = "";//项目组合名称
        CrisisProject crisisProject = new CrisisProject();
        //查询检验项目信息
        ResponseData responseDataTwo = ipService.inspectionReport(inspectionReportRequest);
        logger.info("esb返回的检验报告结果：" + responseDataTwo);
        if (responseDataTwo.getData() != null) {
            InspectionReportResponse inspectionReportResponse = (InspectionReportResponse) responseDataTwo.getData();
            if (inspectionReportResponse != null) {
                List<InspectionReportResponseRecode> inspectionReportResponseRecodes = inspectionReportResponse.getInspectionReportResponseRecode();
                if (inspectionReportResponseRecodes != null && inspectionReportResponseRecodes.size() > 0) {
                    for (InspectionReportResponseRecode inspectionReportResponseRecode : inspectionReportResponseRecodes) {
                        itemSet = inspectionReportResponseRecode.getItemSet();
                        if (inspectionReportResponseRecode.getInspectionReportResponseResult() != null) {
                            for (ItemList itemList : request.getItemList()) {
                                for (InspectionReportResponseRecodeResult inspectionReportResponseRecodeResult : inspectionReportResponseRecode.getInspectionReportResponseResult()) {
                                    if (itemList.getItemCode().equals(inspectionReportResponseRecodeResult.getLisCode())) {
                                        logger.info("esb传递的检验项目code：" + inspectionReportResponseRecodeResult.getLisCode() + ",调用esb接口查出来的检验项目code：" + itemList.getItemCode());
                                        String crisisProjectKey = BeanMapUtils.getTableSeqKey(crisisProject);
                                        Long crisisProjectId = redisIncrementGenerator.increment(crisisProjectKey, 1);
                                        crisisProject.setId(crisisProjectId);
                                        crisisProject.setCriticalId(request.getLisLableNo());
                                        if (itemList.getItemId() != null) {
                                            crisisProject.setItemId(Long.valueOf(itemList.getItemId()));
                                        }
                                        crisisProject.setItemCode(inspectionReportResponseRecodeResult.getLisCode());
                                        crisisProject.setItemName(inspectionReportResponseRecodeResult.getLisName());
                                        crisisProject.setResult(inspectionReportResponseRecodeResult.getLisValue());
                                        crisisProject.setCompany(inspectionReportResponseRecodeResult.getLisUom());
                                        crisisProject.setRange(inspectionReportResponseRecodeResult.getLisRange());
                                        crisisProject.setIsRelieve("0");
                                        if (inspectionReportResponseRecodeResult.getLisRange() != null && inspectionReportResponseRecodeResult.getLisRange().contains("－") && !inspectionReportResponseRecodeResult.getLisRange().contains("①")) {
                                            boolean result = inspectionReportResponseRecodeResult.getLisValue().matches("-?[0-9]+.?[0-9]+");
                                            if (result == true) {
                                                Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("－")[0]);
                                                Double str2 = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("－")[1]);
                                                if (value < str) {
                                                    crisisProject.setPrompt("1");
                                                } else if (value >= str && value < str2) {
                                                    crisisProject.setPrompt("0");
                                                } else {
                                                    crisisProject.setPrompt("2");
                                                }
                                            } else {
                                                boolean res = isNumeric(inspectionReportResponseRecodeResult.getLisValue());
                                                if (res == true) {
                                                    Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                    Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("－")[0]);
                                                    Double str2 = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("－")[1]);
                                                    if (value < str) {
                                                        crisisProject.setPrompt("1");
                                                    } else if (value >= str && value < str2) {
                                                        crisisProject.setPrompt("0");
                                                    } else {
                                                        crisisProject.setPrompt("2");
                                                    }
                                                } else {
                                                    crisisProject.setPrompt("0");
                                                    logger.info("检验结果值不是数字无法比较");
                                                }
                                            }
                                        } else if (inspectionReportResponseRecodeResult.getLisRange() != null && inspectionReportResponseRecodeResult.getLisRange().contains("≤") && !inspectionReportResponseRecodeResult.getLisRange().contains("①")) {
                                            boolean result = inspectionReportResponseRecodeResult.getLisValue().matches("-?[0-9]+.?[0-9]+");
                                            if (result == true) {//结果如果是数字则进行判断
                                                Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());//结果
                                                Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("≤")[1]);
                                                if (value <= str) {
                                                    crisisProject.setPrompt("0");
                                                } else {
                                                    crisisProject.setPrompt("2");
                                                }
                                            } else {
                                                boolean res = isNumeric(inspectionReportResponseRecodeResult.getLisValue());
                                                if (res == true) {
                                                    Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                    Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("≤")[1]);
                                                    if (value < str) {
                                                        crisisProject.setPrompt("0");
                                                    } else {
                                                        crisisProject.setPrompt("2");
                                                    }
                                                } else {
                                                    crisisProject.setPrompt("0");
                                                    logger.info("检验结果值不是数字无法比较");
                                                }
                                            }
                                        } else if (inspectionReportResponseRecodeResult.getLisRange() != null && inspectionReportResponseRecodeResult.getLisRange().contains("≥") && !inspectionReportResponseRecodeResult.getLisRange().contains("①")) {
                                            boolean result = inspectionReportResponseRecodeResult.getLisValue().matches("-?[0-9]+.?[0-9]+");
                                            if (result == true) {//结果如果是数字则进行判断
                                                Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("≥")[0]);
                                                if (value >= str) {
                                                    crisisProject.setPrompt("0");
                                                } else {
                                                    crisisProject.setPrompt("1");
                                                }
                                            } else {
                                                boolean res = isNumeric(inspectionReportResponseRecodeResult.getLisValue());
                                                if (res == true) {
                                                    Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                    Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("≥")[0]);
                                                    if (value >= str) {
                                                        crisisProject.setPrompt("2");
                                                    } else {
                                                        crisisProject.setPrompt("0");
                                                    }
                                                } else {
                                                    crisisProject.setPrompt("0");
                                                    logger.info("检验结果值不是数字无法比较");
                                                }
                                            }
                                        } else if (inspectionReportResponseRecodeResult.getLisRange() != null && inspectionReportResponseRecodeResult.getLisRange().contains("－") && inspectionReportResponseRecodeResult.getLisRange().contains("①")) {
                                            boolean result = inspectionReportResponseRecodeResult.getLisValue().matches("-?[0-9]+.?[0-9]+");
                                            String prefix = inspectionReportResponseRecodeResult.getLisRange().split("①")[0];
                                            Double str = Double.valueOf(prefix.split("－")[0]);
                                            Double str2 = Double.valueOf(prefix.split("－")[1]);
                                            Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                            if (result == true) {
                                                if (value < str) {
                                                    crisisProject.setPrompt("1");
                                                } else if (value >= str && value < str2) {
                                                    crisisProject.setPrompt("0");
                                                } else {
                                                    crisisProject.setPrompt("2");
                                                }
                                            } else {
                                                boolean res = isNumeric(inspectionReportResponseRecodeResult.getLisValue());
                                                if (res == true) {
                                                    if (value < str) {
                                                        crisisProject.setPrompt("1");
                                                    } else if (value >= str && value < str2) {
                                                        crisisProject.setPrompt("0");
                                                    } else {
                                                        crisisProject.setPrompt("2");
                                                    }
                                                } else {
                                                    crisisProject.setPrompt("0");
                                                    logger.info("检验结果值不是数字无法比较");
                                                }
                                            }
                                        } else {
                                            if (inspectionReportResponseRecodeResult.getLisRange() != null) {
                                                logger.info("检验结果参考范围为不是数字无法比较");
                                                crisisProject.setPrompt("0");
                                            } else {
                                                crisisProject.setPrompt("0");
                                                logger.info("检验结果参考范围为null无法比较");
                                            }
                                        }
                                        crisisProject.setExamineRequestId(request.getExamineRequestId());
                                        int count = crisisProjectMapper.selectByItemCode(itemList.getItemCode(), request.getLisLableNo());
                                        logger.info("项目编码：" + itemList.getItemCode() + ",标本号：" + request.getLisLableNo());
                                        if (count <= 0) {
                                            logger.info("项目编号和标本号不存在，添加进患者检验项目表中...json{}" + JsonUtils.toJSon(crisisProject));
                                            crisisProjectMapper.insertSelective(crisisProject);
                                        } else {
                                            logger.info("项目编号和标本号存在，不添加进患者检验项目表中...json{}," + JsonUtils.toJSon(crisisProject));
                                        }
                                    }
                                }
                            }
                        } else {
                            logger.info("查询esb接口--->inspectionReportResponseRecode.getInspectionReportResponseResult()为空!");
                            List<ItemList> itemTemp = request.getItemList();
                            for (ItemList item : itemTemp) {
                                redisTemplate.opsForSet().add(request.getLisLableNo().toString(), item);
                            }
                        }
                    }
                } else {
                    logger.info("查询esb接口--->inspectionReportResponseRecodes为空!");
                    List<ItemList> itemTemp = request.getItemList();
                    for (ItemList item : itemTemp) {
                        redisTemplate.opsForSet().add(request.getLisLableNo().toString(), item);
                    }
                }
            } else {
                logger.info("查询esb接口--->inspectionReportResponse为空!");
                List<ItemList> itemTemp = request.getItemList();
                for (ItemList item : itemTemp) {
                    redisTemplate.opsForSet().add(request.getLisLableNo().toString(), item);
                }
            }
        } else {
            logger.info("查询esb接口--->responseDataTwo.getData()为空!");
            List<ItemList> itemTemp = request.getItemList();
            for (ItemList item : itemTemp) {
                redisTemplate.opsForSet().add(request.getLisLableNo().toString(), item);
            }
        }
        if (request.getNoteList() != null) {
            for (NoteList noteList : request.getNoteList()) {
                if (noteList.getNoTelevel() != null && noteList.getNoTelevel() == 1) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("doctorbook", "1");//推送的app
                    params.put("title", "危急值通知");
                    params.put("notification_title", "您有一个新的危急值通知。--》详情请点击");
                    params.put("alias", noteList.getPersonId());
                    Map<String, String> extras = new HashMap<>();
                    extras.put("noticeType", "1");//附加参数给前端
                    params.put("extras", extras);
                    //params.put("pushTo",1);
                    logger.info("请求推送接口参数：{}", JsonUtils.toJSon(params));
                    Header header = restTemplateMethodParamUtil.getPostHeader(params);
                    String result = HttpRequest.post(netServerHostAndPort + Constants.JPUSH_ADDRESS, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON, header);
                    ResponseData responseDataResult = JsonUtils.readValue(result, ResponseData.class);
                    if (responseDataResult.getData().toString().equals("1")) {//推送成功
                        PushBarNumber pushBarNumber = new PushBarNumber();
                        String pushBarNumberKey = BeanMapUtils.getTableSeqKey(pushBarNumber);
                        Long pushBarNumberId = redisIncrementGenerator.increment(pushBarNumberKey, 1);
                        pushBarNumber.setId(pushBarNumberId);
                        pushBarNumber.setAlias(noteList.getPersonId());
                        pushBarNumber.setNoticeType(1);
                        pushBarNumberMapper.insertSelective(pushBarNumber);
                        logger.info("医生手册危急值推送成功!");
                    } else {
                        logger.info("医生手册危急值推送失败!请求推送接口返回参数：{}", JsonUtils.toJSon(result));
                    }
                }
            }
        }

        Patient patient = patientMapper.selectByESBPatientId(request.getPatientId());
        if (patient != null) {
            List<Contacts> contactsList = contactsMapper.selectByPatientId(patient.getId());//根据平台传递的patientId来获取account_id
            if (contactsList != null) {
                for (Contacts contacts : contactsList) {
                    PushList pushList = new PushList();
                    String pushListKey = BeanMapUtils.getTableSeqKey(pushList);
                    Long pushListId = redisIncrementGenerator.increment(pushListKey, 1);
                    pushList.setId(pushListId);
                    pushList.setAlias(contacts.getAccountId().toString());
                    pushList.setTitle("危急值通知");
                    pushList.setContent(request.getPatientName() + "先生/女士,您的" + itemSet + "检验出现异常，请马上来院就诊");
                    pushList.setPushTime(new Date());
                    pushList.setPushType(String.valueOf(PushType.CRITICALVALUENOTICE.getIndex()));
                    Map<String, String> extrasparam = new HashMap<String, String>();//设置返回给前端的参数额外参数
                    extrasparam.put("patientId", request.getPatientId());
                    extrasparam.put("lisLableNo", request.getLisLableNo().toString());
                    extrasparam.put("pushType", String.valueOf(PushType.CRITICALVALUENOTICE.getIndex()));
                    Map<String, String> extrasTwo = new HashMap<>();
                    extrasTwo.put("data", JsonUtils.toJSon(extrasparam));//保存到数据库的额外参数
                    Map<String, Object> paramsTwo = new HashMap<>();
                    pushList.setExtras(JsonUtils.toJSon(extrasTwo));
                    paramsTwo.put("doctorbook", "0");
                    paramsTwo.put("title", "危急值通知");
                    paramsTwo.put("notification_title", request.getPatientName() + "先生/女士,您的" + itemSet + "检验出现异常，请马上来院就诊");
                    paramsTwo.put("alias", contacts.getAccountId().toString());
                    paramsTwo.put("extras", extrasTwo);
                    paramsTwo.put("pushType", PushType.CRITICALVALUENOTICE.getIndex());
                    //paramsTwo.put("pushTo",1);
                    logger.info("请求推送接口参数：{}", JsonUtils.toJSon(paramsTwo));
                    Header header = restTemplateMethodParamUtil.getPostHeader(paramsTwo);
                    String resultTwo = HttpRequest.post(netServerHostAndPort + Constants.JPUSH_ADDRESS, JsonUtils.toJSon(paramsTwo), ContentType.APPLICATION_JSON, header);
                    ResponseData responseDataResultTwo = JsonUtils.readValue(resultTwo, ResponseData.class);
                    if (responseDataResultTwo.getData().toString().equals("1")) {
                        pushListMapper.insertSelective(pushList);
                        logger.info("就医日志危机值推送成功.");
                    } else {
                        logger.info("就医日志危机值推送成失败!请求推送接口返回参数：{}", JsonUtils.toJSon(resultTwo));
                    }
                }
            }
        }

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 1);
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 危急值列表(供前端调用)
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData selectByPatientInformation(PatientInformationsRequest request) throws SystemException, ESBException {

        logger.info("redistTemplate.size()：" + redisTemplate.opsForList().size(request.getPersonId() + "lisLableNo"));

        Integer examineRequestId = 0;

        Integer lisLableNo = 0;

        List<String> lisLanNos = new ArrayList<>();

        if (redisTemplate.opsForList().size(request.getPersonId() + "lisLableNo") > 0) {
            for (int i = 0; i <= redisTemplate.opsForList().size(request.getPersonId() + "lisLableNo"); i++) {
                examineRequestId = (Integer) redisTemplate.opsForList().rightPop(request.getPersonId() + "examineRequestId");
                lisLableNo = (Integer) redisTemplate.opsForList().rightPop(request.getPersonId() + "lisLableNo");
                Set<Object> items = redisTemplate.opsForSet().members(lisLableNo.toString());
                lisLanNos.add(lisLableNo.toString());
                if (items != null && items.size() > 0) {
                    List<ItemList> itemLists = new ArrayList<>();
                    for (Object obj : items) {
                        itemLists.add((ItemList) obj);
                    }
                    logger.info("examineRequestId：" + examineRequestId + ",lisLableNo：" + lisLableNo + ",itemLists：" + itemLists);
                    //设置请求查询检验项目信息
                    InspectionReportRequest inspectionReportRequest = new InspectionReportRequest();
                    inspectionReportRequest.setExamineRequestId(examineRequestId.toString());
                    String itemSet = "";//项目组合名称
                    CrisisProject crisisProject = new CrisisProject();
                    //查询检验项目信息
                    ResponseData responseDataTwo = ipService.inspectionReport(inspectionReportRequest);
                    logger.info("esb返回的检验报告结果：" + responseDataTwo);
                    if (responseDataTwo.getData() == null) {
                        logger.info("esb返回的检验报告结果：--->responseDataTwo.getData()：" + responseDataTwo.getData());
                    }
                    InspectionReportResponse inspectionReportResponse = (InspectionReportResponse) responseDataTwo.getData();
                    if (inspectionReportResponse != null) {
                        List<InspectionReportResponseRecode> inspectionReportResponseRecodes = inspectionReportResponse.getInspectionReportResponseRecode();
                        if (inspectionReportResponseRecodes != null && inspectionReportResponseRecodes.size() > 0) {
                            for (InspectionReportResponseRecode inspectionReportResponseRecode : inspectionReportResponseRecodes) {
                                itemSet = inspectionReportResponseRecode.getItemSet();
                                if (inspectionReportResponseRecode.getInspectionReportResponseResult() != null) {
                                    for (ItemList itemList : itemLists) {
                                        for (InspectionReportResponseRecodeResult inspectionReportResponseRecodeResult : inspectionReportResponseRecode.getInspectionReportResponseResult()) {
                                            if (itemList.getItemCode().equals(inspectionReportResponseRecodeResult.getLisCode())) {
                                                logger.info("esb传递的检验项目code：" + inspectionReportResponseRecodeResult.getLisCode() + ",调用esb接口查出来的检验项目code：" + itemList.getItemCode());
                                                String crisisProjectKey = BeanMapUtils.getTableSeqKey(crisisProject);
                                                Long crisisProjectId = redisIncrementGenerator.increment(crisisProjectKey, 1);
                                                crisisProject.setId(crisisProjectId);
                                                crisisProject.setCriticalId(lisLableNo.longValue());
                                                if (itemList.getItemId() != null) {
                                                    crisisProject.setItemId(Long.valueOf(itemList.getItemId()));
                                                }
                                                crisisProject.setItemCode(inspectionReportResponseRecodeResult.getLisCode());
                                                crisisProject.setItemName(inspectionReportResponseRecodeResult.getLisName());
                                                crisisProject.setResult(inspectionReportResponseRecodeResult.getLisValue());
                                                crisisProject.setCompany(inspectionReportResponseRecodeResult.getLisUom());
                                                crisisProject.setRange(inspectionReportResponseRecodeResult.getLisRange());
                                                crisisProject.setIsRelieve("0");
                                                if (inspectionReportResponseRecodeResult.getLisRange() != null && inspectionReportResponseRecodeResult.getLisRange().contains("－") && !inspectionReportResponseRecodeResult.getLisRange().contains("①")) {
                                                    boolean result = inspectionReportResponseRecodeResult.getLisValue().matches("-?[0-9]+.?[0-9]+");
                                                    if (result == true) {
                                                        Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                        Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("－")[0]);
                                                        Double str2 = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("－")[1]);
                                                        if (value < str) {
                                                            crisisProject.setPrompt("1");
                                                        } else if (value >= str && value < str2) {
                                                            crisisProject.setPrompt("0");
                                                        } else {
                                                            crisisProject.setPrompt("2");
                                                        }
                                                    } else {
                                                        boolean res = isNumeric(inspectionReportResponseRecodeResult.getLisValue());
                                                        if (res == true) {
                                                            Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                            Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("－")[0]);
                                                            Double str2 = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("－")[1]);
                                                            if (value < str) {
                                                                crisisProject.setPrompt("1");
                                                            } else if (value >= str && value < str2) {
                                                                crisisProject.setPrompt("0");
                                                            } else {
                                                                crisisProject.setPrompt("2");
                                                            }
                                                        } else {
                                                            crisisProject.setPrompt("0");
                                                            logger.info("检验结果值不是数字无法比较");
                                                        }
                                                    }
                                                } else if (inspectionReportResponseRecodeResult.getLisRange() != null && inspectionReportResponseRecodeResult.getLisRange().contains("≤") && !inspectionReportResponseRecodeResult.getLisRange().contains("①")) {
                                                    boolean result = inspectionReportResponseRecodeResult.getLisValue().matches("-?[0-9]+.?[0-9]+");
                                                    if (result == true) {//结果如果是数字则进行判断
                                                        Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());//结果
                                                        Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("≤")[1]);
                                                        if (value <= str) {
                                                            crisisProject.setPrompt("0");
                                                        } else {
                                                            crisisProject.setPrompt("2");
                                                        }
                                                    } else {
                                                        boolean res = isNumeric(inspectionReportResponseRecodeResult.getLisValue());
                                                        if (res == true) {
                                                            Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                            Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("≤")[1]);
                                                            if (value < str) {
                                                                crisisProject.setPrompt("0");
                                                            } else {
                                                                crisisProject.setPrompt("2");
                                                            }
                                                        } else {
                                                            crisisProject.setPrompt("0");
                                                            logger.info("检验结果值不是数字无法比较");
                                                        }
                                                    }
                                                } else if (inspectionReportResponseRecodeResult.getLisRange() != null && inspectionReportResponseRecodeResult.getLisRange().contains("≥") && !inspectionReportResponseRecodeResult.getLisRange().contains("①")) {
                                                    boolean result = inspectionReportResponseRecodeResult.getLisValue().matches("-?[0-9]+.?[0-9]+");
                                                    if (result == true) {//结果如果是数字则进行判断
                                                        Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                        Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("≥")[0]);
                                                        if (value >= str) {
                                                            crisisProject.setPrompt("0");
                                                        } else {
                                                            crisisProject.setPrompt("1");
                                                        }
                                                    } else {
                                                        boolean res = isNumeric(inspectionReportResponseRecodeResult.getLisValue());
                                                        if (res == true) {
                                                            Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                            Double str = Double.valueOf(inspectionReportResponseRecodeResult.getLisRange().split("≥")[0]);
                                                            if (value >= str) {
                                                                crisisProject.setPrompt("2");
                                                            } else {
                                                                crisisProject.setPrompt("0");
                                                            }
                                                        } else {
                                                            crisisProject.setPrompt("0");
                                                            logger.info("检验结果值不是数字无法比较");
                                                        }
                                                    }
                                                } else if (inspectionReportResponseRecodeResult.getLisRange() != null && inspectionReportResponseRecodeResult.getLisRange().contains("－") && inspectionReportResponseRecodeResult.getLisRange().contains("①")) {
                                                    boolean result = inspectionReportResponseRecodeResult.getLisValue().matches("-?[0-9]+.?[0-9]+");
                                                    String prefix = inspectionReportResponseRecodeResult.getLisRange().split("①")[0];
                                                    Double str = Double.valueOf(prefix.split("－")[0]);
                                                    Double str2 = Double.valueOf(prefix.split("－")[1]);
                                                    Double value = Double.valueOf(inspectionReportResponseRecodeResult.getLisValue());
                                                    if (result == true) {
                                                        if (value < str) {
                                                            crisisProject.setPrompt("1");
                                                        } else if (value >= str && value < str2) {
                                                            crisisProject.setPrompt("0");
                                                        } else {
                                                            crisisProject.setPrompt("2");
                                                        }
                                                    } else {
                                                        boolean res = isNumeric(inspectionReportResponseRecodeResult.getLisValue());
                                                        if (res == true) {
                                                            if (value < str) {
                                                                crisisProject.setPrompt("1");
                                                            } else if (value >= str && value < str2) {
                                                                crisisProject.setPrompt("0");
                                                            } else {
                                                                crisisProject.setPrompt("2");
                                                            }
                                                        } else {
                                                            crisisProject.setPrompt("0");
                                                            logger.info("检验结果值不是数字无法比较");
                                                        }
                                                    }
                                                } else {
                                                    if (inspectionReportResponseRecodeResult.getLisRange() != null) {
                                                        logger.info("检验结果参考范围为不是数字无法比较");
                                                        crisisProject.setPrompt("0");
                                                    } else {
                                                        crisisProject.setPrompt("0");
                                                        logger.info("检验结果参考范围为null无法比较");
                                                    }
                                                }
                                                crisisProject.setExamineRequestId(examineRequestId.longValue());
                                                int count = crisisProjectMapper.selectByItemCode(itemList.getItemCode(), lisLableNo.longValue());
                                                logger.info("项目编码：" + itemList.getItemCode() + ",标本号：" + lisLableNo);
                                                if (count <= 0) {
                                                    logger.info("项目编号和标本号不存在，添加进患者检验项目表中...json{}" + JsonUtils.toJSon(crisisProject));
                                                    crisisProjectMapper.insertSelective(crisisProject);
                                                } else {
                                                    logger.info("项目编号和标本号存在，不添加进患者检验项目表中...json{}," + JsonUtils.toJSon(crisisProject));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    logger.info("查询esb接口--->inspectionReportResponseRecode.getInspectionReportResponseResult()为空!");
                                }
                            }
                        } else {
                            logger.info("查询esb接口--->inspectionReportResponseRecodes为空!");
                        }
                    } else {
                        logger.info("查询esb接口--->inspectionReportResponse为空!");
                    }
                }
            }
            for (String lisLanNo : lisLanNos) {
                redisTemplate.delete(lisLanNo);//根据key删除缓存
            }
        }

        if (lisLableNo != null) {
            redisTemplate.delete(request.getPersonId() + "lisLableNo");//根据key删除缓存
        }
        if (examineRequestId != null) {
            redisTemplate.delete(request.getPersonId() + "examineRequestId");//根据key删除缓存
        }

        //执行查询操作
        BaseResponseList<PatientInformation> listBaseResponseList = new BaseResponseList<PatientInformation>();
        PageHelper.startPage(request.getPageNum() - 1, request.getPageSize());
        if (request.getWhetherDealWith() == null) {
            request.setWhetherDealWith("0");
        }
        List<PatientInformation> patientInformationList = patientInformationMapper.selectByPatientInformation(request.getPersonId(), request.getWhetherDealWith());
        if (patientInformationList == null || patientInformationList.size() == 0) {
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
        }
        int count = patientInformationMapper.selectByPatientInformationCount(request.getPersonId());
        if (patientInformationList != null && count > 0) {
            for (PatientInformation patientInformation : patientInformationList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
                //危急值接受时间
                if (patientInformation.getCrisisTime() != null) {
                    String crisisTime = sdf.format(patientInformation.getCrisisTime());
                    patientInformation.setCrisisTimeStr(crisisTime);
                }
                //创建时间和修改时间
                if (patientInformation.getCreateTime() != null) {
                    patientInformation.setCreateTimeStr(sdf.format(patientInformation.getCreateTime()));
                }
                if (patientInformation.getUpdateTime() != null) {
                    patientInformation.setUpdateTimeStr(sdf.format(patientInformation.getUpdateTime()));
                }
                //患者检验项目集合
                List<CrisisProject> crisisProjectList = patientInformationMapper.selectByCriticalId(patientInformation.getLisLableNo());
                if (crisisProjectList.size() > 0) {
                    patientInformation.setCrisisProjectList(crisisProjectList);
                }
            }
            int total = (count % request.getPageSize() == 0) ? count / request.getPageSize() : count / request.getPageSize() + 1;//计算总分页数
            listBaseResponseList.setList(patientInformationList);
            listBaseResponseList.setTotal(total);
            //判断请求页数是否大于总分页数
            if (request.getPageNum() > total) {
                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
            }
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), listBaseResponseList);
        } else {
            return new ResponseData(StatusCode.ARGU_ERROR.getCode(), StatusCode.ARGU_ERROR.getMsg(), null);
        }
    }

    /**
     * 危急值处理(供平台调用)
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData sendCriticalValuesHandler(SendCriticalValuesHandlerRequest request) throws ParseException {

        //根据检验标本号把危急值患者表修改为以处理
        patientInformationMapper.updateByLisLableNo(request.getLisLableNo());

        CriticalValueProcessing criticalValue = new CriticalValueProcessing();

        String crisisValueKey = BeanMapUtils.getTableSeqKey(criticalValue);

        Long crisisValueProcessingId = redisIncrementGenerator.increment(crisisValueKey, 1);

        criticalValue.setId(crisisValueProcessingId);

        criticalValue.setLisLableNo(request.getLisLableNo());

        criticalValue.setExamineRequestId(request.getExamineRequestId());

        criticalValue.setPatientId(request.getPatientId());

        criticalValue.setPatientName(request.getPatientName());

        criticalValue.setDisposeWAY(request.getDisposeWAY());

        criticalValue.setDisposeEmployeeNo(request.getDisposeEmployeeNo());

        criticalValue.setDisposeEmployeeName(request.getDisposeEmployeeName());

        criticalValue.setDisposeDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getDisposeDatetime()));

        criticalValue.setDisposeTypeCode(request.getDisposeTypeCode());

        criticalValue.setDisposeTypeName(request.getDisposeTypeName());

        criticalValue.setDisposeDescribe(request.getDisposeDescribe());

        for (Disposeresults disposeresults : request.getDisposeresults()) {

            criticalValue.setItemId(disposeresults.getItemId());

            criticalValue.setItemCode(disposeresults.getItemCode());

            criticalValue.setItemName(disposeresults.getItemName());

            criticalValue.setDisposeMinutes(disposeresults.getDisposeMinutes());

            //增加已处理危急值项目记录
            criticalValueProcessingMapper.insertSelective(criticalValue);
        }

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 1);
    }

    /**
     * 危急值解除(供平台调用)
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData sendCriticalValuesRelieve(SendCriticalValuesRelieveRequest request) throws ParseException {

        RelieveToExamine relieveToExamine = new RelieveToExamine();

        String relieveToExamineKey = BeanMapUtils.getTableSeqKey(relieveToExamine);

        Long relieveToExamineId = redisIncrementGenerator.increment(relieveToExamineKey, 1);

        relieveToExamine.setId(relieveToExamineId);//主键

        relieveToExamine.setExamineRequestId(request.getExamineRequestId());//检验申请单号

        relieveToExamine.setStatreMark(request.getStatremark());//状态描述

        if (request.getOptm() != null) {
            relieveToExamine.setOptm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getOptm()));//操作时间
        }

        //添加解除审核的记录
        relieveToExamineMapper.insertSelective(relieveToExamine);

        for (String results : request.getResults().getLisCode()) {

            RelieveToExamineItemCode relieveToExamineItemCode = new RelieveToExamineItemCode();

            relieveToExamineItemCode.setExamineRequestID(request.getExamineRequestId());//检验申请单号

            relieveToExamineItemCode.setLisCode(results);//检验编号
            //添加接触审核项目的记录
            relieveToExamineItemCodeMapper.insertSelective(relieveToExamineItemCode);
            //把检验编码修改为解除审核
            int crisisItemCode = crisisProjectMapper.updateByItemCode(request.getExamineRequestId(), relieveToExamineItemCode.getLisCode());

            if (crisisItemCode > 0) {
                logger.info("根据检验申请单id：" + request.getExamineRequestId() + "来修改" + relieveToExamineItemCode.getLisCode() + "项目编码修改成功!");
            } else {
                logger.info("根据检验申请单id：" + request.getExamineRequestId() + "来修改" + relieveToExamineItemCode.getLisCode() + "项目编码修改失败!");
            }
        }

        SendCriticalValuesRelieveResponse sendCriticalValuesRelieveResponse = new SendCriticalValuesRelieveResponse();

        SendCriticalValuesRelieveResponseRecord sendCriticalValuesRelieveResponseRecord = new SendCriticalValuesRelieveResponseRecord();

        sendCriticalValuesRelieveResponseRecord.setResultCode((long) 1);

        sendCriticalValuesRelieveResponseRecord.setResultDesc(StatusCode.OK.getMsg());

        List<SendCriticalValuesRelieveResponseRecord> sendCriticalList = new ArrayList<>();

        if (sendCriticalValuesRelieveResponseRecord != null) {
            sendCriticalList.add(sendCriticalValuesRelieveResponseRecord);
        }

        sendCriticalValuesRelieveResponse.setSendCriticalValuesRelieveResponseRecords(sendCriticalList);

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), sendCriticalValuesRelieveResponse);
    }

    /**
     * 危急值处理(供前端调用)
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData criticalValueProcessing(CriticalValueProcessingRequest request) throws SystemException, ESBException {

        CriticalValueProcessing criticalValue = new CriticalValueProcessing();

        String criticalValueKey = BeanMapUtils.getTableSeqKey(criticalValue);

        Long criticalValueId = redisIncrementGenerator.increment(criticalValueKey, 1);

        criticalValue.setId(criticalValueId);//设置主键

        criticalValue.setLisLableNo(request.getLisLableNo());//检验标本号

        if (request.getExamineRequestId() != null) {
            criticalValue.setExamineRequestId(request.getExamineRequestId());//检验申请单号
        }
        if (request.getPatientId() != null) {
            criticalValue.setPatientId(request.getPatientId());//患者id
        }
        if (request.getPatientName() != null) {
            criticalValue.setPatientName(request.getPatientName());//患者姓名
        }
        criticalValue.setDisposeWAY((long) 2);//处理途径

        criticalValue.setDisposeEmployeeNo(request.getDisposeEmployeeNo());//处理人工号

        criticalValue.setDisposeEmployeeName(request.getDisposeEmployeeName());//处理人姓名

        criticalValue.setDisposeDatetime(new Date());//处理时间

        criticalValue.setDisposeTypeCode("99");//处理类型编码

        criticalValue.setDisposeTypeName("其他");//处理类型描述

        criticalValue.setDisposeDescribe(request.getDisposeDescribe());//处理备注

        criticalValue.setItemId(request.getItemId());//项目id

        criticalValue.setItemCode(request.getItemCode());//项目编码

        criticalValue.setItemName(request.getItemName());//项目名称
        //把修改人的信息保存到数据库中
        criticalValueProcessingMapper.insertSelective(criticalValue);
        //把患者表中的是否处理修改为以处理
        patientInformationMapper.updateByLisLableNo(request.getLisLableNo());

        //调用平台接口

        SendCriticalValuesHandlerRequest sendCriticalValuesHandlerRequest = new SendCriticalValuesHandlerRequest();

        sendCriticalValuesHandlerRequest.setLisLableNo(request.getLisLableNo());

        sendCriticalValuesHandlerRequest.setExamineRequestId(request.getExamineRequestId());

        sendCriticalValuesHandlerRequest.setPatientId(request.getPatientId());

        sendCriticalValuesHandlerRequest.setPatientName(request.getPatientName());

        sendCriticalValuesHandlerRequest.setDisposeWAY((long) 2);

        sendCriticalValuesHandlerRequest.setDisposeDatetime(request.getCrisisTime());

        sendCriticalValuesHandlerRequest.setDisposeEmployeeNo(request.getDisposeEmployeeNo());

        sendCriticalValuesHandlerRequest.setDisposeEmployeeName(request.getDisposeEmployeeName());

        sendCriticalValuesHandlerRequest.setDisposeDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        sendCriticalValuesHandlerRequest.setDisposeTypeCode(request.getDisposeTypeCode());

        sendCriticalValuesHandlerRequest.setDisposeTypeName(request.getDisposeTypeName());

        sendCriticalValuesHandlerRequest.setDisposeDescribe(request.getDisposeDescribe());

        List<Disposeresults> disposeresultsList = new ArrayList<>();

        List<CrisisProject> crisisProjectList = patientInformationMapper.selectByCriticalId(request.getLisLableNo());

        for (CrisisProject crisisProject : crisisProjectList) {

            Disposeresults disposeresults = new Disposeresults();

            disposeresults.setItemId(crisisProject.getItemId());

            disposeresults.setItemCode(crisisProject.getItemCode());

            disposeresults.setItemName(crisisProject.getItemName());

            disposeresultsList.add(disposeresults);
        }

        sendCriticalValuesHandlerRequest.setDisposeresults(disposeresultsList);

        logger.info("移动后台请求平台参数：{}" + sendCriticalValuesHandlerRequest);

        ResponseData responseData = publicService.query(ESBServiceCode.CRITICALVALUESHANDLER, sendCriticalValuesHandlerRequest, SendCriticalValuesHandlerResponse.class);

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 1);
    }

    /**
     * 查询医生手册推送记录条数
     *
     * @param request
     * @return
     */
    @Override
    public ResponseData selectByPersonId(PushBarNumberRequest request) {

        int count = pushBarNumberMapper.selectByPersonId(request.getPersonId(), 1);

        int count2 = pushBarNumberMapper.selectByPersonId(request.getPersonId(), 2);

        int count3 = pushBarNumberMapper.selectByPersonId(request.getPersonId(), 3);

        int count4 = pushBarNumberMapper.selectByPersonId(request.getPersonId(), 4);

        PushBarNumberResponse response = new PushBarNumberResponse();

        response.setCriticalNotice(count);

        response.setShiftsNotice(count2);

        response.setProjectNotice(count3);

        response.setConsultationNotice(count4);

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), response);
    }

    /**
     * 根据医生别名，清空某一类型的推送记录
     *
     * @param personId   医生别名
     * @param noticeType 类型标识
     * @return
     */
    @Override
    public ResponseData deleteByPersonIdAndNoticeType(String personId, String noticeType) {

        int result = pushBarNumberMapper.deleteByPersonIdAndNoticeType(personId, noticeType);

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), result);
    }

    /**
     * 医生手册保存推送记录
     *
     * @param request
     * @return
     */
    @Override
    public ResponseData savePushBarNumber(PushBarNumberRequest request) {

        PushBarNumber pushBarNumber = new PushBarNumber();

        String pushBarNumberKey = BeanMapUtils.getTableSeqKey(pushBarNumber);

        Long pushBarNumberId = redisIncrementGenerator.increment(pushBarNumberKey, 1);

        pushBarNumber.setId(pushBarNumberId);

        pushBarNumber.setAlias(request.getPersonId());

        pushBarNumber.setNoticeType(request.getNoticeType());

        int count = pushBarNumberMapper.insertSelective(pushBarNumber);

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 1);
    }
}
