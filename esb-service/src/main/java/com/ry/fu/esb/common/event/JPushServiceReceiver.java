package com.ry.fu.esb.common.event;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.http.HttpRequest;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.RestTemplateMethodParamUtil;
import com.ry.fu.esb.medicaljournal.mapper.PushListMapper;
import com.ry.fu.esb.medicaljournal.model.PushList;
import com.ry.fu.esb.medicaljournal.service.impl.JPushNoticeServiceImpl;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JPushServiceReceiver implements ApplicationListener<JPushEvent> {

    private static Logger logger = LoggerFactory.getLogger(JPushNoticeServiceImpl.class);

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private PushListMapper pushListMapper;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;

    @Autowired
    private Environment env;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    @Override
    public void onApplicationEvent(JPushEvent jPushEvent) {
        //System.out.println(jPushEvent.getSource());
        //根据传参调用极光推送
        Map map = (Map) jPushEvent.getSource();
        List<String> openId = (List<String>) map.get("openId");
        String title = (String) map.get("title");
        String notification_title = (String) map.get("notification_title");
        Integer pushType = (Integer) map.get("pushType");
        String patientId = (String) map.get("patientId");
        String orderId = (String) map.get("orderId");

        Map<String, String> extras=null;
        Map<String, String> extrasParam=new HashMap<>(2);
        for (int i=0;i<openId.size();i++){
            PushList pushList = new PushList();
            String pushListKey = BeanMapUtils.getTableSeqKey(pushList);
            Long pushListId = redisIncrementGenerator.increment(pushListKey, 1);
            pushList.setId(pushListId);
            pushList.setAlias(openId.get(i));
            pushList.setTitle(title);
            pushList.setContent(notification_title);
            pushList.setPushTime(new Date());
            pushList.setPushType(pushType.toString());
            extras = new HashMap<>();
            extrasParam.put("patientId", patientId );
            extrasParam.put("orderId", orderId);
            extras.put("pushType", String.valueOf(pushType));
            extras.put("data", JsonUtils.toJSon(extrasParam));
            pushList.setExtras(JsonUtils.toJSon(extras));
            pushListMapper.insertSelective(pushList);
        }
        Map params = new HashMap<>();
        params.put("doctorbook","0");//推送的app
        params.put("aliaes", openId);
        params.put("title", title);
        params.put("notification_title", notification_title);
        params.put("extras", extras);
        if(!"pro".equals(env.getActiveProfiles()[0])) {
            params.put("pushTo", "1");
        }
        Header header = restTemplateMethodParamUtil.getPostHeader(params);
        String result = HttpRequest.post(netServerHostAndPort + Constants.JPUSH_ADDRESS, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON,header);
        //ResponseData responseData = JsonUtils.readValue(result, ResponseData.class);
        logger.info("推送结果"+result);

    }
}
