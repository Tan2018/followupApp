package com.ry.fu.esb.test;


import com.ry.fu.esb.EsbServiceApplication;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.service.RequestService;
import com.ry.fu.esb.common.transformer.Transformer;
import com.ry.fu.esb.doctorbook.controller.IPServiceController;
import com.ry.fu.esb.doctorbook.model.request.HospitalizationRecordRequest;
import com.ry.fu.esb.medicaljournal.controller.CommonServiceController;
import com.ry.fu.esb.medicaljournal.controller.OpServiceController;
import com.ry.fu.esb.medicaljournal.model.request.*;
//import com.ry.fu.esb.medicaljournal.util.OrderIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/1/8 11:53
 * @description description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EsbServiceApplication.class)
public class MainTest {

    private static Logger logger = LoggerFactory.getLogger(MainTest.class);

    @Resource
    private Transformer transformer;

    @Autowired
    private RequestService requestService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CommonServiceController controller;

    @Autowired
    private IPServiceController ipServiceController;

    @Resource
    private ValueOperations<String,Object> valueOperations;

    @Autowired
    private PublicService publicService;

    @Autowired
    private OpServiceController opServiceController;



//    @Test
//    public void testXmlTransformer() throws Exception{
//        TokenRequest tokenRequest = new TokenRequest();
//        tokenRequest.setRequestId("1234");
//        tokenRequest.setRequestIp("127.0.0.1");
//        tokenRequest.setSystemCode("system");
//        tokenRequest.setSystemPassword("0");
//        String xml = transformer.writeAsXML(tokenRequest);
//        logger.info(xml);
//    }

//    @Test
//    public void testGetToken(){
//        final CountDownLatch begin = new CountDownLatch(1); //为0时开始执行
//        final ExecutorService exec = Executors.newFixedThreadPool(100);
//        for (int i = 0; i < 100; i++) {
//            final int NO = i + 1;
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        logger.info("第 " + NO + "个线程" );
//                        begin.await(); //等待直到 CountDownLatch减到1
//                        String token = requestService.requestToken("FUM_SYSTEM","0");
//
//                        String uuid = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
//                        GetTokenRequest getTokenRequest = new GetTokenRequest();
//                        getTokenRequest.setRequestId(uuid);
//                        getTokenRequest.setRequestIp("127.0.0.1");
//                        getTokenRequest.setSystemCode("FUM_SYSTEM");
//                        getTokenRequest.setSystemPassword("0");
//                        String tokenBody = transformer.writeAsXML(getTokenRequest);
//                        String tokenContent = restTemplate.postForObject("http://172.18.9.188:9090/hip/hipService/getToken", tokenBody, String.class);
//                        String accessToken = StringUtils.substringBetween(tokenContent, "<ACCESSTOKEN>", "</ACCESSTOKEN>");
//
//                        logger.info("第 " + NO + "个线程获取到的token:" + accessToken);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            exec.submit(runnable);
//        }
//        logger.info("开始执行");
//        begin.countDown(); // begin减一，开始并发执行
//        exec.shutdown();
//    }

    /**
     * 并发测试获取验证码
     */
//    @Test
//    public void testVerifyCode(){
//        String[] numbers = {
//            "13682284454","18512005258","15812830317","15627864562","13124919432","13247134858","18802007610","18620289370","13560362357",
//            "18998325653","18664880738","15521091516","17620087599","18319125232","18126788840","13826273344","15915875807","18026241353",
//            "15889643651","15088131981","18122020814","13650756801","18819259392","13929537318","18819259409","15322382608","13539760541",
//            "18621602435","15777775117","18579125785","18924115802","13714323733","13926040925","13526494537","17817329701","13560354289",
//            "15290710133","18926145043","13760862793","18520538227","18899752897","18826233795","13417262845","15814442836","13970137765",
//            "13825498197","15812457841","17051202451","13640660016","18508495952","18520152249","13016017952","13049353492","15986429341",
//            "13420018437","15802021754","15013027768","13428756709","13560357573","15915870759","13560195291"
//        };
//        int len = numbers.length;
//        final CountDownLatch begin = new CountDownLatch(1); //为0时开始执行
//        final ExecutorService exec = Executors.newFixedThreadPool(len);
//        for (int i = 0; i < len; i++) {
//            final Map<String, Object> params = new HashMap<String,Object>();
//            params.put("phoneNumber",numbers[i]);
//            logger.info("========第{}个号码:{}",i,params.get("phoneNumber"));
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        controller.verifyCode(params);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            exec.submit(runnable);
//        }
//        logger.info("开始执行");
//        begin.countDown(); // begin减一，开始并发执行
//        exec.shutdown();
//    }

//    @Test
//    public void testRegistration(){
//        String[] numbers = {
//                "13682284454","18512005258","15812830317","15627864562","13124919432","13247134858","18802007610","18620289370","13560362357",
//                "18998325653","18664880738","15521091516","17620087599","18319125232","18126788840","13826273344","15915875807","18026241353",
//                "15889643651","15088131981","18122020814","13650756801","18819259392","13929537318","18819259409","15322382608","13539760541",
//                "18621602435","15777775117","18579125785","18924115802","13714323733","13926040925","13526494537","17817329701","13560354289",
//                "15290710133","18926145043","13760862793","18520538227","18899752897","18826233795","13417262845","15814442836","13970137765",
//                "13825498197","15812457841","17051202451","13640660016","18508495952","18520152249","13016017952","13049353492","15986429341",
//                "13420018437","15802021754","13428756709","13560357573","15915870759","13560195291"
//        };
//        int len = numbers.length;
//        final CountDownLatch begin = new CountDownLatch(1); //为0时开始执行
//        final ExecutorService exec = Executors.newFixedThreadPool(len);
//        for (int i = 0; i < len; i++) {
//            final Map<String, Object> params = new HashMap<String,Object>();
//            String key = Constants.VERIFY_CODE_PREFIX + numbers[i];
//            params.put("accountMobile",numbers[i]);
//            params.put("verifyCode",valueOperations.get(key));
//            params.put("accountPassword","1257899");
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        publicService.handleRegistration(params);
//                        logger.info("执行注册完毕！");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            exec.submit(runnable);
//        }
//        logger.info("开始执行");
//        begin.countDown(); // begin减一，开始并发执行
//        exec.shutdown();
//    }

    @Test
    public void testGetDeptInfo() throws Exception{
        DeptInfoRequest request = new DeptInfoRequest();
        request.setFlag(2535);
        opServiceController.queryDeptInfo(request);
    }

//    @Test
//    public void testGetDoctorInfo() throws Exception{
//        DoctorInfoRequest request = new DoctorInfoRequest();
//        request.setDeptId(1677);
////        request.setDoctorId(9142);
////        ESBResponseData result = opServiceController.queryDoctorInfo(request);
////        logger.info("result:{}",result);
//    }

//    @Test
//    public void testQueryRegInfo() throws Exception{
//        DoctorRegisterSourceRequest request = new DoctorRegisterSourceRequest();
//        request.setDeptId(1687);
//        request.setDoctorId(9142);
//        request.setStartDate("2018-01-01");
//        request.setEndDate("2018-02-28");
//        request.setType(0);
//        ESBResponseData result = opServiceController.queryRegInfo(request);
//        logger.info("result:{}",result);
//    }

    @Test
    public void testAddNewRegistration() throws Exception{
        RegisterInfoRequest request = new RegisterInfoRequest();
        request.setOrderType("8");
        request.setDeptId("2463");
        request.setDoctorId("15559");
        request.setRegDate("2018-03-06");
        request.setTimeId("2");
        request.setStartTime("08:00");
        request.setEndTime("09:00");
        request.setUserHisPatientId("50690");
        request.setUserIdCard("440101750101001");
        request.setUserJkk("6000164896");
        request.setUserName("test");
        request.setUserGender("M");
        request.setUserMobile("02081332305");
        request.setUserBirthday("1975-01-01");
        request.setFee("0");
        request.setTreatFee("1000");
        request.setAddFlag("0");
        ResponseData result = opServiceController.addNewRegistration(request);
        logger.info("result:{}",result);
    }

    @Test
    public void testGetRegisteredDoctor() throws Exception{
        RegisteredDoctorRequest request = new RegisteredDoctorRequest();
        request.setPatientId(911337);
        ResponseData result = opServiceController.getRegisteredDoctor(request);
    }

    @Test
    public void testhospitalizationRecord() throws Exception{
        HospitalizationRecordRequest request = new HospitalizationRecordRequest();
        request.setIpTimes("1");
        request.setIpseqNoText("1067847");
        request.setPatientId("1006895176");
        ipServiceController.hospitalizationRecord(request);
    }

    /**
     * 医生号源
     * @throws Exception
     */
    @Test
    public void testGetDoctorRegisterSource() throws Exception{
        DoctorRegisterSourceRequestExtend request = new DoctorRegisterSourceRequestExtend();
        List<String> deptIdList = new ArrayList<String>(5);
        deptIdList.add("3070");
        request.setDeptIdList(deptIdList);
        request.setDoctorId("9142");
        request.setStartDate("2018-03-21");
        request.setEndDate("2018-03-21");
        request.setType("0");
        opServiceController.queryRegInfo(request);
    }

    /**
     * 医生分时号源
     * @throws Exception
     */
    @Test
    public void testGetDoctorTimeRegisterSource() throws Exception{
        DoctorRegisterSourceByTimeRequest request = new DoctorRegisterSourceByTimeRequest();
        List<DeptIdAndTimeId> deptIdAndTimeIdList = new ArrayList<DeptIdAndTimeId>(2);
        request.setDoctorId("10734");
        request.setRegDate("2018-03-19");

        DeptIdAndTimeId deptIdAndTimeId1 = new DeptIdAndTimeId();
        deptIdAndTimeId1.setDeptId("2308");
        List<String> timeIdList1 = new ArrayList<String>(2);
        timeIdList1.add("3");
        timeIdList1.add("4");
        deptIdAndTimeId1.setTimeId(timeIdList1);
        deptIdAndTimeIdList.add(deptIdAndTimeId1);


        DeptIdAndTimeId deptIdAndTimeId2 = new DeptIdAndTimeId();
        deptIdAndTimeId2.setDeptId("2309");
        List<String> timeIdList2 = new ArrayList<String>(2);
        timeIdList2.add("2");
        timeIdList2.add("4");
        deptIdAndTimeId2.setTimeId(timeIdList2);
        deptIdAndTimeIdList.add(deptIdAndTimeId2);
        request.setDeptIdAndTimeIdList(deptIdAndTimeIdList);
        request.setType("0");
        opServiceController.queryTimeRegInfo(request);
    }

    /**
     *科室号源
     * @throws Exception
     */
    @Test
    public void testDeptRegInfo() throws Exception{
        DeptRegInfoRequest request = new DeptRegInfoRequest();
        List<String> deptIdList = new ArrayList<String>(5);
        deptIdList.add("1687");
        deptIdList.add("1688");
        deptIdList.add("1689");
        deptIdList.add("1690");
        request.setDeptIdList(deptIdList);
        request.setStartDate("2018-03-15");
        request.setEndDate("2018-03-15");
        request.setType("0");
        opServiceController.queryDeptRegInfo(request);
    }

    @Test
    public void testGetDoctorInfo() throws Exception{
        DoctorInfoRequest request = new DoctorInfoRequest();
        request.setDeptId(1687);
//        request.setDoctorId(9142);
        ResponseData result = opServiceController.queryDoctorInfo(request);
        logger.info("result:{}",result);
    }

    @Test
    public void testDeptSwitch() throws Exception{
        DeptSwitchRequest request = new DeptSwitchRequest();
        request.setDoctorId("9142");
        request.setStartDate("2018-03-15");
        request.setEndDate("2018-03-27");
        ResponseData result = opServiceController.deptSwitch(request);
    }

    @Test
    public void testRegisteredRecord() throws Exception{
//        RegisteredRecordRequest request = new RegisteredRecordRequest();
//        StringBuffer buffer = new StringBuffer();
//        buffer.append("50690");
//        buffer.append(",");
//        buffer.append("911337");
//        buffer.append(",");
//        buffer.append("911567");
//        buffer.append(",");
//        buffer.append("911785");
//        buffer.append(",");
//        buffer.append("803245");
//        buffer.append(",");
//        buffer.append("430456");
//        buffer.append(",");
//        buffer.append("884616");
//        request.setPatientIds(buffer.toString());
//        request.setPageNum("1");
//        request.setPageSize("10");
//        ResponseData result = opServiceController.registeredRecord(request);
    }

    @Test
    public void testAddOrgInfo() throws Exception{
        DeptInfoRequest request = new DeptInfoRequest();
        request.setFlag(2986L);
        opServiceController.addOpOrgInfo(request);
    }

    @Test
    public void testPatientInfo() throws Exception{
        PatientInfoRequest request = new PatientInfoRequest();
        request.setUserCardId("6000164896");
        request.setUserCardType("1");
        request.setUserIdCard("440101750101001");
        request.setUserName("test");
        opServiceController.patientInfo(request);
    }

    @Test
    public void testGenerateOrderId() throws Exception{
//        OrderIdGenerator orderIdGenerator = new OrderIdGenerator();
//        orderIdGenerator.generateOrderId("83834");
    }
}
