package com.ry.fu.net.common.http;

import com.ry.fu.net.im.controller.CheckSumBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Http请求客户端工具
 */
public class HttpRequest {

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    private HttpRequest() {
    }

    protected final static Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    protected final static RequestConfig DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
            .setConnectionRequestTimeout(3000).setConnectTimeout(30000)
            .setSocketTimeout(30000).build();

    public static String post(String uri, String content) {
        return post(uri, content, ContentType.TEXT_PLAIN);
    }

    public static String post(String uri, String content, ContentType contentType) {
        logger.info("HttpRequest ready to send post SendMsg for url:{}", uri);
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(uri);
            HttpEntity requestEntity = new StringEntity(content, contentType);
            httpPost.setEntity(requestEntity);

            httpPost.setConfig(RequestConfig.copy(DEFAULT_REQUEST_CONFIG).build());
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity, "UTF-8");
                HttpClientUtils.closeQuietly(httpResponse);
                logger.info("HttpRequest send post SendMsg finished!");
            } else {
                throw new ClientProtocolException("Unexpected response status: " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            logger.error("HttpRequest Exception： " + ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e);
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
        }

        return result;
    }

    /**
     * 网易云请求
     * @param uri 请求地址
     * @param nvp 请求参数
     * @return
     * @throws Exception
     */
    public static String postRequest(String uri, List<NameValuePair> nvp,String appKey,String appSecret) throws IOException {
        logger.info("HttpRequest ready to send post SendMsg for url:{}", uri);
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(uri);
        String nonce = "12345";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPost.setEntity(new UrlEncodedFormEntity(nvp, "utf-8"));

        httpPost.setConfig(RequestConfig.copy(DEFAULT_REQUEST_CONFIG).build());
        httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity resEntity = httpResponse.getEntity();
            result = EntityUtils.toString(resEntity, "UTF-8");
        } else {
            throw new ClientProtocolException("Unexpected response status: " + httpResponse.getStatusLine().getStatusCode());
        }
        return result;
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramList 提交<参数，值>BasicNameValuePair
     * @return 提交响应
     */

    public static String post(String url, List<BasicNameValuePair> paramList) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            method.setEntity(new UrlEncodedFormEntity(paramList,
                    ENCODING));
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, ENCODING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
