package com.ry.fu.followup.base.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/2/11 8:56
 * @description Http的Body的请求的工具类
 */
public class HttpDataUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpDataUtils.class);


    /**
     * 获取HTTP请求数据
     * @param request
     * @return
     */
    public static String getReqData(ServletRequest request) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(), Charset.forName("UTF-8")));

            String line = null;
            while((line = br.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取Http中Body流出错", e);
        }
        return sb.toString();
    }


}
