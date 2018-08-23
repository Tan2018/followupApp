package com.ry.fu.esb.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    /**
     * 转换map
     * @param requestMap
     * @return
     */
    public static Map<String, String> parseRequestMap(Map<String, String[]> requestMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        for (Iterator iter = requestMap.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = valueStr + values[i];
                if (i < values.length - 1) {
                    valueStr = valueStr + ",";
                }
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            resultMap.put(name, valueStr);
        }
        return resultMap;
    }

    public static void main(String[] args) {
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("tradeNo", new String[]{"3010208201803239999954"});
        map.put("payTime", new String[]{"2018-03-23 15:58:49"});
        map.put("agtTradeNo", new String[]{"2018032321001004230582621890"});
        map.put("signMode", new String[]{"RSA"});
        map.put("outOrderNo", new String[]{"test10010"});
        map.put("nonceStr", new String[]{"51fbba70dde3434586b00faff314b7a1"});

        System.out.println(map.get("tradeNo")[0]);
        System.out.println(JsonUtils.toJSon(map));

        Map<String, String> transferMap = parseRequestMap(map);
        System.out.println(transferMap.get("tradeNo"));
        System.out.println(JsonUtils.toJSon(transferMap));
    }


}
