package com.ry.fu.esb.common.utils;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RestTemplateMethodParamUtil {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(RestTemplateMethodParamUtil.class);

    @Value("${fua.appKey}")
    private String appKey;
    public Header getPostHeader(Map<String, Object> params) {
        long timestamp = System.currentTimeMillis();
        params.put("timeStamp",timestamp);
        String appToken = SecurityUtils.getMD5(appKey + timestamp).toLowerCase();
        Header header = new BasicHeader("appToken", appToken);
        return header;
    }
    public HttpEntity<String> getPostRequestParams(String jsonStr){

        Map postData = JsonUtils.readValue(jsonStr, Map.class);
        String appToken = null;
        String timeStamp = null;
        Object objTime = postData.get("timeStamp");
        if (objTime!=null){

            timeStamp = objTime.toString();
            appToken = SecurityUtils.getMD5(appKey + timeStamp).toLowerCase();
            logger.info("==>appKey:"+appKey);
            logger.info("==>timeStamp:"+timeStamp);
            logger.info("==>appToken:"+appToken);
        }

        //todo 用restmplate发起请求
        HttpHeaders headers = new HttpHeaders();
        headers.set("appToken",appToken);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntitys = new HttpEntity<String>(jsonStr, headers);

        return formEntitys;
    }


}
