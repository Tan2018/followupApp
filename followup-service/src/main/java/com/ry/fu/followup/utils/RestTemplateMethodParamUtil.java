package com.ry.fu.followup.utils;

import com.ry.fu.followup.base.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class RestTemplateMethodParamUtil {

    @Value("${system.app.appKey}")
    private  String appKey;

    public  HttpEntity<String> getPostRequestParams(String jsonStr){
        //todo 用restmplate发起请求
        Map postData = JsonUtils.readValue(jsonStr, Map.class);
        String appToken = null;
        String timeStamp = null;
        Object objTime = postData.get("timeStamp");
       if (objTime!=null){

           timeStamp = objTime.toString();
        appToken = SecurityUtils.getMD5(appKey + timeStamp).toLowerCase();

       }

        HttpHeaders headers = new HttpHeaders();
        headers.set("appToken",appToken);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntitys = new HttpEntity<String>(jsonStr, headers);

        return formEntitys;
    }


}
