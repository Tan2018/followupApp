package com.ry.fu.followup.doctorbook.service.impl;
import com.ry.fu.followup.base.model.Pagenation;
import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.config.UrlConfig;
import com.ry.fu.followup.doctorbook.mapper.PushHistoryMapper;
import com.ry.fu.followup.doctorbook.model.Group;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.NotificationService;
import com.ry.fu.followup.utils.JsonUtils;
import com.ry.fu.followup.utils.ResponseMapUtil;
import com.ry.fu.followup.utils.RestTemplateMethodParamUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jackson on 2018/5/5.
 */
@Service
public class NotificationServiceImpl implements NotificationService{

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    @Autowired
    private Environment env;


    @Value("${esbServerHostAndPort}")
    private String esbServerHostAndPort;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;

    @Autowired
    private PushHistoryMapper pushHistoryMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  UrlConfig urlConfig;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public ResponseData queryApplicationList(ReqInfo reqInfo) throws ParseException {
        Long pageNumber = reqInfo.getPageNumber();
        Long pageSize = reqInfo.getPageSize();
        Long doctoryId = reqInfo.getDoctorId();
        Integer totalCount = pushHistoryMapper.getPushHistoryCountByDoctorId(doctoryId.toString());

        //Integer totalCount = 1;
        Pagenation pagenation = new Pagenation(pageNumber.intValue(),totalCount,pageSize.intValue());
        List<Map<String, Object>> lists = pushHistoryMapper.queryPushHistoryCountByDoctorIdByPage(reqInfo.getDoctorId().toString(), pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> innerDataMap = new HashMap<>();
        if (totalCount == 0 && false) {
            innerDataMap.put("total",0);
            innerDataMap.put("list",list);
            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
        }
        System.out.println(lists);
        for(Map m:lists){
            String str = m.get("EXTRAS").toString();

            Group group = JsonUtils.readValue(str,Group.class);
            Map<String,Object> map  = new HashMap<>();
            map.put("id",group.getId());
            map.put("project",group.getProject());
            map.put("status",group.getStatus());

            //pushTime判空
            String pushTime = m.get("PUSH_TIME").toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateString = sdf.format(sdf.parse(pushTime));

            if(StringUtils.isNotEmpty(pushTime)){
                map.put("pushTime",dateString);
            }else{
                map.put("pushTime","未设置推送时间");
            }
                       list.add(map);
        }
        System.out.println("pagenation.getTotalPage()的值为"+pagenation.getTotalPage());
        innerDataMap.put("total",pagenation.getTotalPage());
        innerDataMap.put("list",list);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    @Override
    public ResponseData sendGroupAppliction( Map<String, Object> retMap) {

        String title = "";
        String notification_title = "";
        title = "随访项目提醒";
        notification_title = "您有项目审核成功";
        String alias =(String)retMap.get("id");//如果推送给医生手册app,accountid=alias
        Integer noticeType = 3;//推送给就一日志
        String timeStamp = System.currentTimeMillis()+"";
        StringBuilder json = JsonUtils.mapToJson(retMap,null);
        Map<String,String> extras = new HashMap<>();
        extras.put("data", String.valueOf(json));
        extras.put("noticeType","3");//推送给就一日志

        Map<String, Object> params = new HashMap<>();
        params.put("doctorbook","1");//推送的给医生手册志app
        params.put("alias",alias);
        params.put("notification_title",notification_title);
        params.put("title",title);
        params.put("extras",extras);
        params.put("timeStamp",timeStamp);//安全校验
        extras.put("noticeType","3");//随访通知提醒
        if(!"pro".equals(env.getActiveProfiles()[0])) {
            params.put("pushTo", "1");
        }
        String postData = JsonUtils.toJSon(params);

        //todo 用restmplate发起请求

       // HttpEntity<String> formEntity = new HttpEntity<String>(postData, headers);
        HttpEntity<String> formEntity = restTemplateMethodParamUtil.getPostRequestParams(postData);
        String result = restTemplate.postForObject(netServerHostAndPort+urlConfig.getNET_SEND_PUSH_URL(), formEntity, String.class);

        logger.info("{}",result);
        String jsonExtras = String.valueOf(json);
        jsonExtras = StringEscapeUtils.unescapeJava(jsonExtras);
        //解析推送结果,如果结果返回为1，则代表推送成功
        ResponseData responseData1 = JsonUtils.readValue(result, ResponseData.class);
        if (responseData1.getData().toString().equals("1")) {
            Random random = new Random();
            Integer id =random.nextInt();
            logger.info("**************推送成功**************");
            Integer integer = pushHistoryMapper.insertPushHistory(id,alias,title,notification_title, jsonExtras);

            //保存推送条数记录
            Map<String,Object> postDatas = new HashMap<>();
            postDatas.put("personId",alias);
            postDatas.put("noticeType",noticeType);
            postDatas.put("timeStamp",timeStamp);
            String postJson = JsonUtils.toJSon(postDatas);

            HttpEntity<String> formEntitys = restTemplateMethodParamUtil.getPostRequestParams(postJson);
            String results = restTemplate.postForObject(esbServerHostAndPort+urlConfig.getESB_SAVE_PUSH_BARNUM_URL(), formEntitys, String.class);

            }else{
                logger.info("**************推送失败**************");
                Integer id =new Random().nextInt();
                pushHistoryMapper.insertPushHistory(id,alias,title,notification_title, jsonExtras);

                return new ResponseData(500,"推送失败",null);
            }
            return new ResponseData(200,"推送成功",null);
    }


}
