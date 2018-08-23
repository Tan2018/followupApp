package com.ry.fu.esb.common.http;

import com.ry.fu.esb.common.constants.Constants;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.Header;
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
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            .setConnectionRequestTimeout(30000).setConnectTimeout(40000)
            .setSocketTimeout(40000).build();

    public static String post(String uri, String content) {
        return post(uri, content, Constants.DEFAULT_CONTENTTYPE);
    }

    public static String post(String uri, String content, ContentType contentType) {
        return post(uri, content, contentType, null);
    }

    public static String post(String uri, String content, ContentType contentType, Header header) {
        logger.info("HttpRequest ready to send post request for url:{}", uri);
        String result = null;
        HttpPost httpPost = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            httpPost = new HttpPost(uri);
            HttpEntity requestEntity = new StringEntity(content, contentType);
            httpPost.setEntity(requestEntity);

            if (header != null) {
                httpPost.addHeader(header);
            }
            httpPost.setConfig(RequestConfig.copy(DEFAULT_REQUEST_CONFIG).build());
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == Constants.HTTP_RESPONSE_SUCCESS) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity, Constants.UTF_8);
                HttpClientUtils.closeQuietly(httpResponse);
                logger.info("HttpRequest send post request finished!");
            } else {
                httpPost.abort();
                throw new ClientProtocolException("Unexpected response status: " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            logger.error("HttpRequest Exception： " + ExceptionUtils.getStackTrace(e));
        } finally {
            httpPost.abort();
            httpPost = null;
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
