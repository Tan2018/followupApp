package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.response.ResponseData;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/26 16:51
 **/
public interface AppSetService {

    ResponseData findNewAppVersion(String useType,String appId);

    ResponseData insertQuesFeedbackInfo(Map<String,Object> map);

    ResponseData findAboutAppInfo(String useType);

}
