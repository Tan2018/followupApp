package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.TimeUtils;
import com.ry.fu.esb.instantmessaging.service.NetEaseCloudLetterService;
import com.ry.fu.esb.medicaljournal.constants.RedisKeyConstants;
import com.ry.fu.esb.medicaljournal.mapper.*;
import com.ry.fu.esb.medicaljournal.model.*;
import com.ry.fu.esb.medicaljournal.model.request.DoctorQueryRequest;
import com.ry.fu.esb.medicaljournal.model.request.DoctorSearchReq;
import com.ry.fu.esb.medicaljournal.model.request.PayTheFeesRequest;
import com.ry.fu.esb.medicaljournal.model.request.RegisteredPayRequest;
import com.ry.fu.esb.medicaljournal.model.response.*;
import com.ry.fu.esb.medicaljournal.service.MedicalService;
import com.ry.fu.esb.medicaljournal.service.OPService;
import com.ry.fu.esb.medicaljournal.util.AccountType;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.ry.fu.esb.common.utils.ResponseUtil.getSuccessResultBean;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/16 14:40
 * @description description
 */
@Service
public class MedicalServiceImpl implements MedicalService {

    private Logger logger = LoggerFactory.getLogger(MedicalServiceImpl.class);

    @Autowired
    private PublicService publicService;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private OrgInfoMapper orgInfoMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private OPService opService;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private NetEaseCloudLetterService netEaseCloudLetterService;

    //@Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData searchDoctor(String content, String pageSize, String pageNum) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        DoctorSearchReq req = new DoctorSearchReq();
        req.setPageSize(pageSize);
        req.setPageNum(pageNum);
        req.setContent(content);
        long t2 = System.currentTimeMillis();
        ResponseData responseData = publicService.query(ESBServiceCode.DOCTORINFOBYNAME, req, DoctorByNameInfoRecord.class);
        long t3 = System.currentTimeMillis();
        if (responseData != null && responseData.getData() != null && responseData.getData() instanceof DoctorByNameInfoRecord) {
            DoctorByNameInfoRecord record = (DoctorByNameInfoRecord) responseData.getData();
           /* List<DoctorByNameInfo> list = record.getRecord();
            if (list != null && list.size() > 0) {
                for (DoctorByNameInfo doctorByNameInfo : list) {
                    DoctorInfo doctorInfo = new DoctorInfo();
                    Long id = redisIncrementGenerator.increment(Constants.M_DOCTOR_SEQ, 1);
                    doctorInfo.setId(id);
                    doctorInfo.setSex(doctorByNameInfo.getSex());
                    doctorInfo.setHeadImg(doctorByNameInfo.getDocImg());
                    doctorInfo.setGoodAt(doctorByNameInfo.getDesc());
                    doctorInfo.setProcessName(doctorByNameInfo.getTitle());
                    doctorInfo.setChName(doctorByNameInfo.getDoctorName());
                    doctorInfo.setStatus("0");
                    doctorInfo.setDoctorId(Long.parseLong(doctorByNameInfo.getDoctorId()));
                    doctorInfo.setUserName(doctorByNameInfo.getLoginName());
                    doctorMapper.saveOrUpdate(doctorInfo);
                }
            }*/
        }
        long t4 = System.currentTimeMillis();
        logger.info("=====响应时间t3=" + (t3) + "---响应时长---" + (t3 - t2));
        logger.info("=====运行时间t4=" + (t4) + "---运行时间---" + ((t4 - t1) - (t3 - t2)));
        return img(responseData);
    }


    //查询图片；
    private ResponseData img(ResponseData responseData) {
        if (responseData != null && responseData.getData() != null && responseData.getData() instanceof
                DoctorByNameInfoRecord) {
            DoctorByNameInfoRecord record = (DoctorByNameInfoRecord) responseData.getData();
            List<DoctorByNameInfo> list = record.getRecord();
            if (list != null && list.size() > 0) {
                for (DoctorByNameInfo doctorByNameInfo : list) {
                    List<String> headimg = orgInfoMapper.img(Long.parseLong(doctorByNameInfo.getDoctorId()));
                    if (headimg != null && !headimg.isEmpty()) {
                        doctorByNameInfo.setDocImg(headimg.get(0));
                    }

                }
            }

        }
        return responseData;
    }

    /**
     * 插入门诊科室信息
     *
     * @param params
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOpOrgInfo(Map<String, Object> params) throws ESBException, SystemException {
        //一级科室
        List<DeptItemResponse> firstDeptList = (List<DeptItemResponse>) params.get("depts");
        if (!CollectionUtils.isEmpty(firstDeptList)) {
            for (DeptItemResponse firstDeptItem : firstDeptList) {
                if (!CollectionUtils.isEmpty(firstDeptItem.getDepts())) {
                    //二级科室
                    List<DeptItemResponse> secondDeptList = firstDeptItem.getDepts();
                    for (DeptItemResponse secondDeptItem : secondDeptList) {
                        if (!CollectionUtils.isEmpty(secondDeptItem.getDepts())) {
                            //三级科室
                            List<DeptItemResponse> thirdDeptList = secondDeptItem.getDepts();
                            for (DeptItemResponse thirdDeptItem : thirdDeptList) {
                                //保存三级科室
                                saveOrgInfo(thirdDeptItem.getParentId(), thirdDeptItem.getDeptId(), thirdDeptItem.getDeptName());
                            }
                        }
                        //保存二级科室
                        saveOrgInfo(secondDeptItem.getParentId(), secondDeptItem.getDeptId(), secondDeptItem.getDeptName());
                    }
                }
                //保存一级科室
                saveOrgInfo(firstDeptItem.getParentId(), firstDeptItem.getDeptId(), firstDeptItem.getDeptName());
            }
        }
    }


    /**
     * 挂号支付
     *
     * @param orderId 订单号
     * @param payId   支付交易号
     * @param payFee  支付金额
     * @param payTime 支付时间
     * @param payDesc 支付描述
     * @return
     */
    @Override
    public ResponseData regPay(String orderId, String payId, String payFee, String payTime, String payDesc)
            throws ESBException, SystemException {
        RegisteredPayRequest req = new RegisteredPayRequest();
        req.setOrderType("40");     //医程通
        req.setOrderId(orderId);
        req.setPayNum(payId);
        req.setPayMout(payFee);
        req.setPayTime(payTime);
        req.setPayDesc(payDesc);
        return publicService.query(ESBServiceCode.REGISTEREDPAY, req, ResultResponse.class);
    }

    @Override
    public ResponseData pharmacys(String medicalNo) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        List<Registration> registrationList = registrationMapper.selectRegistration(medicalNo);
        PharmacysResponse pharmacysResponse = new PharmacysResponse();
        ArrayList<PharmacysRecord> PharmacysRecordList = new ArrayList();
        if (registrationList != null && registrationList.size() > 0) {
            for (Registration registration : registrationList) {
                //获取患者就诊时间；
                Date visitDate = null;
                if (registration.getVisitDate() != null) {
                    visitDate = registration.getVisitDate();
                }
                //判断就诊时间是否是当天；
                //获取当天的凌晨时间；
                Date startDate = DateTime.now().withMillisOfDay(0).toDate();
                //获取一天最后的时间段；
                Date endDate = DateTime.now().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
                if (visitDate.getTime() >= startDate.getTime() && visitDate.getTime() < endDate.getTime()) {
                    //获取科室和医生名字
                    HashMap stringObjectHashMap = doctorOrg(registration.getDoctorId(), registration.getDeptId());
                    OrgInfo orgInfo = new OrgInfo();
                    DoctorInfo doctorInfo = new DoctorInfo();
                    if (stringObjectHashMap != null && stringObjectHashMap.size() > 0) {
                        orgInfo = (OrgInfo) stringObjectHashMap.get("orgInfo");
                        doctorInfo = (DoctorInfo) stringObjectHashMap.get("doctorQueryRecord");
                    }

                    List<Order> orderList = orderMapper.selectRegid(registration.getId());
                    if (orderList != null && orderList.size() > 0) {
                        for (Order order : orderList) {
                            PharmacysRecord pharmacysRecord = new PharmacysRecord();
                            List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderIdLike(order.getId());
                            pharmacysRecord.setOrderId(order.getId());
                            pharmacysRecord.setOrderDetail(orderDetailList);
                            pharmacysRecord.setPharmacyAddress(order.getOrderDesc());
                            pharmacysRecord.setCreateTime(order.getCreateTime());
                            pharmacysRecord.setDoctorName(doctorInfo.getChName());
                            pharmacysRecord.setDept(orgInfo.getOrgName());
                            PharmacysRecordList.add(pharmacysRecord);
                        }
                    }
                }
            }
        }
        pharmacysResponse.setRecord(PharmacysRecordList);
        long t2 = System.currentTimeMillis();
        logger.info("=====运行时间t2=" + (t2) + "---运行时间---" + (t2 - t1));
        return new ResponseData("200", "请求成功", pharmacysResponse);
    }

    @Override
    public ResponseData registrationDepartment(String medicalNo) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        //获取15天之内号源；
        List<Registration> registrationList = registrationMapper.selectAndPatientId(medicalNo);
        List<registrationDepartmentRecord> departmentListRecord = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //返回所有的挂号信息；

        //joda 时间工具类处理时间
        //获取当前时间
        DateTime startTime = TimeUtils.getCurrentTime().withMillisOfDay(0);
        String startTimes = startTime.toString("yyyy-MM-dd HH:mm:ss");

        DateTime endTime = TimeUtils.getCurrentTime().millisOfDay().withMaximumValue();
        String endTimes = endTime.toString("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(startTimes);
            endDate = simpleDateFormat.parse(endTimes);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (registrationList != null && registrationList.size() != 0) {
            for (Registration registration : registrationList) {
                Order order = orderMapper.selectAndRegid(registration.getId());
                long drugAccordTime = 0;
                if (order != null) {
                    boolean flag = false;
                    if ("0".equals(order.getOrderStatus())) {
                        //未交费判断是否已经过了15分钟；
                        Date day = new Date();
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(day);
                        instance.set(Calendar.MINUTE, instance.get(Calendar.MINUTE) - 10);
                        Date ThreeDays = instance.getTime();
                        drugAccordTime = order.getCreateTime().getTime() - ThreeDays.getTime();
                    }

                    if ("1".equals(order.getOrderStatus()) || "8".equals(order.getOrderStatus())) {
                        //已缴费判断是否是当前时间；2018-05-23 00:00:00 ~ 2018-05-23 23:59:59
                        //获取就诊时间；
                        Date visitDate = registration.getVisitDate();
                        if (visitDate.getTime() >= startDate.getTime() && visitDate.getTime() < endDate.getTime()) {
                            System.out.println("++++++++++++++++++符合就诊时间的；");
                            flag = true;
                        }
                    }

                    if (drugAccordTime > 0 || flag) {
                        registrationDepartmentRecord departmentRecord = new registrationDepartmentRecord();
                        //获取医生名字和科室；
                        HashMap<String, Object> stringObjectHashMap = doctorOrg(registration.getDoctorId(), registration
                                .getDeptId());

                        DoctorInfo doctorInfo = (DoctorInfo) stringObjectHashMap.get("doctorQueryRecord");
                        OrgInfo orgInfo = (OrgInfo) stringObjectHashMap.get("orgInfo");

                        departmentRecord.setDoctorName(doctorInfo.getChName());
                        departmentRecord.setDeptName(orgInfo.getOrgName());
                        departmentRecord.setOrderStatus(order.getOrderStatus());
                        if (order.getCreateTime() != null) {
                            departmentRecord.setCreateTime(order.getCreateTime());
                        }
                        if (registration.getVisitDate() != null) {
                            departmentRecord.setVisitDate(simpleDateFormat.format(registration.getVisitDate()));
                        }
                        departmentRecord.setVisitTime(registration.getVisitTime());
                        departmentRecord.setInfoSeq(registration.getHisOrderId());
                        departmentListRecord.add(departmentRecord);
                    }
                }
            }
        }
        long t2 = System.currentTimeMillis();
        logger.info("=====运行时间t2=" + (t2) + "---运行时间---" + (t2 - t1));
        return new ResponseData("200", "请求成功", departmentListRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData PayTheFeesInquires(String operateType, String userCardType, String userCardId, String infoSeq, String
            orderStatus) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        //获取挂号源；
        Registration registration = new Registration();
        registration.setHisOrderId(infoSeq);
        List<Registration> registrations = registrationMapper.select(registration);
        //获取就诊卡号患者信息；
        Patient patient = new Patient();
        patient.setHealthCardNo(registrations.get(0).getMedicalNo());
        List<Patient> patientList = patientMapper.select(patient);

        PayTheFeesRespone payTheFeesRespone = new PayTheFeesRespone();
        List<PayTheFeesResponeRecord> record = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //判断是挂号费还是门诊费；
        long t2 = 0;
        long t3 = 0;
        if ("1".equals(orderStatus) || "8".equals(orderStatus)) {
            PayTheFeesRequest req = new PayTheFeesRequest();
            req.setOperateType(operateType);
            req.setUserCardType(userCardType);
            req.setUserCardId(userCardId);
            req.setInfoSeq(infoSeq);
            t2 = System.currentTimeMillis();
            ResponseData responseData = publicService.query(ESBServiceCode.USERPAYRECORDINQUIRE, req, PayTheFeesRespone.class);
            t3 = System.currentTimeMillis();


            PayTheFeesRespone data = (PayTheFeesRespone) responseData.getData();
            List<PayTheFeesResponeRecord> records = data.getRecord();
            PayTheFeesResponeRecord payTheFeesResponeRecord = records.get(0);
            List<PayTheFeesResponeRecordOrderDetailInfo> orderDetailInfo = payTheFeesResponeRecord.getOrderDetailInfo();
            //判断是否到线下处理；
            if ("0".equals(payTheFeesResponeRecord.getResultCode())) {
                //挂号；医生并且做了操作；
                if (orderDetailInfo != null && orderDetailInfo.size() > 0) {
                    //获取orderID；
                    HashMap<String, Object> registrationOrdersOk = registrationOrder(infoSeq);
                    Registration registrationNo = (Registration) registrationOrdersOk.get("registration");
                    //获取医生的名字和科室；
                    HashMap<String, Object> stringObjectHashMapok = doctorOrg(registrationNo.getDoctorId(), registrationNo.getDeptId());
                    DoctorInfo doctorInfo = (DoctorInfo) stringObjectHashMapok.get("doctorQueryRecord");
                    OrgInfo orgInfo = (OrgInfo) stringObjectHashMapok.get("orgInfo");
                    payTheFeesResponeRecord.setDoctorName(doctorInfo.getChName());
                    payTheFeesResponeRecord.setOrgName(orgInfo.getOrgName());
                    //对于金额的处理；
                    //总费用金额：
                    String payAmouts = payTheFeesResponeRecord.getPayAmout();
                    Long payAmout = Double.valueOf(payAmouts).longValue();
                    payTheFeesResponeRecord.setPayAmout(String.valueOf(payAmout));

                    //医保金额：
                    String accountAmounts = payTheFeesResponeRecord.getAccountAmount();
                    Long accountAmount = Double.valueOf(accountAmounts).longValue();
                    payTheFeesResponeRecord.setAccountAmount(String.valueOf(accountAmount));

                    //自己处理的个人自负金额：(总费用-医保金额)
                    Long personagePayAmout = payAmout - accountAmount;
                    payTheFeesResponeRecord.setSelfPayAmout(String.valueOf(personagePayAmout));


                    String selfPayAmouts = payTheFeesResponeRecord.getSelfPayAmout();
                    Long selfPayAmout = Double.valueOf(selfPayAmouts).longValue();
                    if (personagePayAmout.equals(selfPayAmout)) {
                        System.out.println("处理的个人自负金额和获取的相等");
                    }

                    //订单唯一值：
                    List<Long> detailId = new ArrayList<>();
                    String doctorAdviceId = "";
                    for (PayTheFeesResponeRecordOrderDetailInfo orderDetailInfos : orderDetailInfo) {
                        detailId.add(Long.parseLong(orderDetailInfos.getDetailID()));
                    }
                    Collections.sort(detailId, null);
                    for (Long l : detailId) {
                        doctorAdviceId += l + ",";
                    }
                    List<String> a = new ArrayList<>();
                    System.out.println(a.size());

                    List<Order> orderLastTimeList = orderMapper.selectAndRegidAndOrderName(registrationNo.getId());
                    if (orderLastTimeList != null && !CollectionUtils.isEmpty(orderLastTimeList) && !orderLastTimeList.get(0)
                            .getDoctorAdviceId().equals(doctorAdviceId)) {
                        orderDetailMapper.deleteByOrderId(orderLastTimeList.get(0).getId());
                        orderMapper.deleteById(orderLastTimeList.get(0).getId());
                        logger.info("=========================删除上个没有缴费的订单=========================");
                    }


                    Order costorders = orderMapper.selectAndRegidRest(registrationNo.getId(), doctorAdviceId);
                    System.out.println(costorders + "+++++++++++++++++++++++++++++++++++++++++++++");
                    if (costorders == null) {
                        //生成费用订单
                        Order costOrder = new Order();
                        Long orderTableId = redisIncrementGenerator.increment(Constants.M_ORDER_SEQ, 1);
                        String orderId = "RL" + orderTableId;
                        costOrder.setId(orderId);
                        costOrder.setOrderType("40");
                        costOrder.setOrderStatus("0");
                        costOrder.setOperater(registrationNo.getDoctorId());
                        costOrder.setOrderName("总费用");
                        costOrder.setFeeType("总费用");
                        //总费用金额：
                        costOrder.setTotalFee(String.valueOf(payAmout));
                        //医保金额：
                        costOrder.setMedicalFee(String.valueOf(accountAmount));
                        //个人自负金额
                        costOrder.setPersonalFee(String.valueOf(personagePayAmout));

                        costOrder.setRegId(registrationNo.getId());
                        costOrder.setDoctorAdviceId(doctorAdviceId);
                        costOrder.setCreateTime(new Date());
                        orderMapper.insertSelective(costOrder);
                        payTheFeesResponeRecord.setOrderId(orderId);
                        payTheFeesResponeRecord.setCreateTime(simpleDateFormat.format(costOrder.getCreateTime()));

                        //生成明细；
                        for (PayTheFeesResponeRecordOrderDetailInfo orderDetailInf : orderDetailInfo) {
                            OrderDetail orderDetail = new OrderDetail();
                            long id = redisIncrementGenerator.increment(Constants.M_ORDERDETAIL_SEQ, 1);
                            orderDetail.setId(id);
                            orderDetail.setOrderId(costOrder.getId());
                            orderDetail.setDetailFee(orderDetailInf.getDetailFee());
                            orderDetail.setDetailID(orderDetailInf.getDetailID());
                            orderDetail.setDetailName(orderDetailInf.getDetailName());
                            orderDetail.setDetailCount(orderDetailInf.getDetailCount());
                            orderDetail.setDetailUnit(orderDetailInf.getDetailUnit());
                            orderDetail.setDetailAmout(orderDetailInf.getDetailAmout());
                            orderDetailMapper.insertSelective(orderDetail);
                        }
                    }

                    if (costorders != null) {
                        String createTime = simpleDateFormat.format(costorders.getCreateTime());
                        payTheFeesResponeRecord.setCreateTime(createTime);
                        payTheFeesResponeRecord.setOrderId(costorders.getId());
                    }

                    record.add(payTheFeesResponeRecord);
                }

            }
            if ("-1".equals(payTheFeesResponeRecord.getResultCode())) {
                logger.info(payTheFeesResponeRecord.getAccountAmount());
                System.out.println(payTheFeesResponeRecord.toString());
                record.add(payTheFeesResponeRecord);

            }
        }
        if ("0".equals(orderStatus)) {
            HashMap<String, Object> registrationOrders = registrationOrder(infoSeq);
            Order order = (Order) registrationOrders.get("order");
            Registration registrationNo = (Registration) registrationOrders.get("registration");

            //未缴费的；
            PayTheFeesResponeRecordOrderDetailInfo rod = new PayTheFeesResponeRecordOrderDetailInfo();
            rod.setDetailFee(order.getOrderName());
            rod.setDetailAmout(order.getTotalFee());
            ArrayList<PayTheFeesResponeRecordOrderDetailInfo> rodList = new ArrayList<>();
            rodList.add(rod);
            PayTheFeesResponeRecord rrod = new PayTheFeesResponeRecord();
            rrod.setInfoSeq(infoSeq);
            rrod.setSelfPayAmout(order.getPersonalFee());
            rrod.setOrderDetailInfo(rodList);
            rrod.setUserName(patientList.get(0).getName());
            rrod.setOrderId(order.getId());
            rrod.setVisitTime(registrationNo.getVisitTime());
            rrod.setVisitDate(simpleDateFormat.format(registrationNo.getVisitDate()));
            String createTime = simpleDateFormat.format(order.getCreateTime());
            rrod.setCreateTime(createTime);

            //获取医生名字和科室；
            HashMap<String, Object> stringObjectHashMap = doctorOrg(registrationNo.getDoctorId(), registrationNo.getDeptId());
            DoctorInfo doctorInfo = (DoctorInfo) stringObjectHashMap.get("doctorQueryRecord");
            OrgInfo orgInfo = (OrgInfo) stringObjectHashMap.get("orgInfo");

            rrod.setDoctorName(doctorInfo.getChName());
            rrod.setOrgName(orgInfo.getOrgName());
            record.add(rrod);
        }
        long t4 = System.currentTimeMillis();
        logger.info("=====响应时间t3=" + (t3) + "---响应时长---" + (t3 - t2));
        logger.info("=====运行时间t4=" + (t4) + "---运行时间---" + ((t4 - t1) - (t3 - t2)));
        payTheFeesRespone.setRecord(record);
        if (record != null && !record.isEmpty() && "-1".equals(record.get(0).getResultCode())) {
            String accountType = AccountType.getSpecialControlFlag(record.get(0).getResultDesc());
            if ("1".equals(accountType)) {
                return new ResponseData(StatusCode.ACCOUNT_ERROR_ONE.getCode(), StatusCode.ACCOUNT_ERROR_ONE.getCode(), record.get(0)
                        .getResultDesc
                                ());
            } else if ("2".equals(accountType)) {
                return new ResponseData(StatusCode.ACCOUNT_ERROR_TWO.getCode(), StatusCode.ACCOUNT_ERROR_TWO.getCode(), record.get(0)
                        .getResultDesc
                                ());
            } else {
                return new ResponseData(StatusCode.ACCOUNT_ERROR_THREE.getCode(), StatusCode.ACCOUNT_ERROR_THREE.getCode(), record.get(0)
                        .getResultDesc
                                ());
            }
        } else {
            return getSuccessResultBean(payTheFeesRespone);
        }
    }

    @Override
    public void autoUpdatePatientToken() {
        try {
            //List<HashMap<String,String>> list= patientMapper.selectNoTokenList();
            List<Patient> list = patientMapper.selectNoTokenPatientList();
            for (Patient p : list) {
                String token = ("p" + StringUtils.rightPad(String.valueOf(p.getEsbPatientId()), 6, "0"));
                netEaseCloudLetterService.registerOrUpdateToken("p" + p.getEsbPatientId(), token, p.getName());
                patientMapper.updateByESBPatientId(String.valueOf(p.getEsbPatientId()), token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //订单表和挂号表；
    private HashMap<String, Object> registrationOrder(String registrationOrder) throws ESBException, SystemException {
        Registration registrationNos = new Registration();
        registrationNos.setHisOrderId(registrationOrder);
        List<Registration> registrationNoList = registrationMapper.select(registrationNos);
        Order order = orderMapper.selectAndRegid(registrationNoList.get(0).getId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("registration", registrationNoList.get(0));
        map.put("order", order);
        return map;

    }


    //查询医生名字和科室；
    private HashMap<String, Object> doctorOrg(String doctorId, String orgId) throws ESBException, SystemException {
       /* DoctorQueryRequest doctorQueryRequest = new DoctorQueryRequest();
        doctorQueryRequest.setDoctorId(doctorId);

        String doctorDeptKey = String.format(RedisKeyConstants.DOCTORQUERY_DEPT_DOCTORID, "null", doctorId);
        DoctorQueryResponse doctorInfoResponse = (DoctorQueryResponse) redisTemplate.opsForValue().get(doctorDeptKey);
        if (doctorInfoResponse == null || doctorInfoResponse.getDoctorQueryRecord() == null
                || doctorInfoResponse.getDoctorQueryRecord().size() <= 0) {
            ResponseData responseData = publicService.query(ESBServiceCode.DOCTORINFOQUERY, doctorQueryRequest, DoctorQueryResponse
                    .class);
            doctorInfoResponse = (DoctorQueryResponse) responseData.getData();

            redisTemplate.opsForValue().set(doctorDeptKey, doctorInfoResponse, 2, TimeUnit.HOURS);
        }
        List<DoctorQueryRecord> doctorQueryRecord = doctorInfoResponse.getDoctorQueryRecord();
        DoctorQueryRecord doctorQueryRecord1 = new DoctorQueryRecord();

        if (doctorQueryRecord != null && doctorQueryRecord.size() != 0) {
            doctorQueryRecord1 = doctorQueryRecord.get(0);
        }*/

       //获取医生名字；
        DoctorInfo doctorInfos = new DoctorInfo();
        doctorInfos.setDoctorId(Long.valueOf(doctorId));
        List<DoctorInfo> doctorInfoList = doctorMapper.select(doctorInfos);
        DoctorInfo doctorInfo = new DoctorInfo();
        if (doctorInfoList != null && doctorInfoList.size() > 0) {
            doctorInfo = doctorInfoList.get(0);
        }


        //获取科室名称；
        OrgInfo orgInfos = new OrgInfo();
        orgInfos.setOrgId(Long.parseLong(orgId));
        List<OrgInfo> orgInfoList = orgInfoMapper.select(orgInfos);
        HashMap<String, Object> map = new HashMap<>();
        OrgInfo orgInfo = new OrgInfo();
        if (orgInfoList != null && orgInfoList.size() > 0) {
            orgInfo = orgInfoList.get(0);
        }
        map.put("doctorQueryRecord", doctorInfo);
        map.put("orgInfo", orgInfo);
        return map;
    }

    private void saveOrgInfo(Integer parentId, Integer deptId, String deptName) {
        List<Long> ids = orgInfoMapper.selectByOrgId(deptId);
        if (ids != null && ids.size() > 0) {
            logger.info("插入科室失败！数据库已经存在此ID：{}", deptId);
            return;
        }

        Long id = redisIncrementGenerator.increment(Constants.M_ORG_SEQ, 1);
        if (id != 0L) {
            orgInfoMapper.insertOrg(id, parentId, deptId, new Date(), deptName);
        }
    }
}
