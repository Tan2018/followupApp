package com.ry.fu.esb.token.interceptor;

import com.ry.fu.esb.common.constants.CommonConstant;
import com.ry.fu.esb.common.properties.SystemProperties;
import com.ry.fu.esb.common.utils.HttpDataUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/2/5 8:18
 * @description 自定义Spring拦截器，用来拦截Controller里面带有@Token注解的方法
 *      作用：校验token参数
 */
public class TokenAuthInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(TokenAuthInterceptor.class);

    @Autowired
    private SystemProperties systemProperties;

    /**
     * pre拦截，如果类注解里面有APPToken，则类的全部方法需要拦截；若只是方法拦截，则只拦截方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");

        String bodyData = HttpDataUtils.getReqData(request);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        logger.info(bodyData);
        if(StringUtils.isBlank(bodyData)) {
            responseMap.put("status", "500");
            responseMap.put("msg", "请求体数据为空");
            responseMap.put("data", null);
            logger.warn("请求体数据为空，请求路径为：{}", request.getRequestURL());
            response.getWriter().print(JsonUtils.toJSon(responseMap));
            return false;
        }

        String appToken = null;
        appToken = request.getHeader(CommonConstant.APP_TOKEN);
        response.setContentType("application/json; charset=utf-8");
        if(StringUtils.isBlank(appToken)) {
            responseMap.put("status", "500");
            responseMap.put("msg", "Token不能为空");
            responseMap.put("data", null);
            logger.warn("Token不能为空，请求路径为：{}", request.getRequestURI());
            response.getWriter().print(JsonUtils.toJSon(responseMap));
            return false;
        }

        String appKey = systemProperties.getAppKey();
        Map<String, String> dataMap = JsonUtils.readValue(bodyData, HashMap.class);
        if(dataMap == null || StringUtils.isBlank(String.valueOf(dataMap.get("timeStamp")))) {
            responseMap.put("status", "500");
            responseMap.put("msg", "timeStamp不能为空");
            responseMap.put("data", null);
            logger.warn("timeStamp不能为空，请求路径为：{}", request.getRequestURI());
            response.getWriter().print(JsonUtils.toJSon(responseMap));
            return false;
        }
        String timestamp = "";
        if(StringUtils.isNotBlank(String.valueOf(dataMap.get("timeStamp")))) {
            timestamp = String.valueOf(dataMap.get("timeStamp"));
        }
        logger.info(appKey + timestamp);
        String md5AppToken = SecurityUtils.getMD5(appKey + timestamp).toLowerCase();
        if(StringUtils.isNotBlank(md5AppToken) &&
                (appToken.toLowerCase().equals(md5AppToken) || appToken.toUpperCase().equals(md5AppToken))) {
            logger.info("校验成功--appToken:{}", appToken);
            //两个值相同，放开权限，验证成功
            return true;
        }
        logger.info("Token校验错误--appToken:{},md5AppToken:{}", appToken, md5AppToken);
        responseMap.put("status", "500");
        responseMap.put("msg", "Token校验错误");
        responseMap.put("data", null);
        logger.warn("Token校验错误，请求路径为：{}", request.getRequestURI());
        response.getWriter().print(JsonUtils.toJSon(responseMap));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
