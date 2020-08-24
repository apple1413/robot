package com.zty.robot.KnowBasePort;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zty.robot.base.ServerConfigure;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KnowBaseAbstract {

    private static Logger logger = Logger.getLogger(KnowBaseAbstract.class);

    @Autowired
    private ServerConfigure serverConfigure;

    private static RequestConfig requestConfig;

    public RequestConfig getRequestConfig() {
        if (null == requestConfig)
            requestConfig = RequestConfig.custom()
                .setSocketTimeout(serverConfigure.getTimeout())
                .setConnectTimeout(serverConfigure.getTimeout())
                .setConnectionRequestTimeout(serverConfigure.getTimeout())
                .build();
        return requestConfig;
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param params 参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param params 参数(格式:json)
     */
    public String sendHttpPostJson(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }


    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     */
    public String sendHttpPost(String httpUrl, Map<String, Object> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key).toString()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            if (logger.isTraceEnabled())
                logger.error("不支持的编码格式：" + e.getMessage());
        }
        return sendHttpPost(httpPost);
    }


    /**
     * 发送Post请求
     * @param httpPost
     * @return
     */
    private String sendHttpPost(HttpPost httpPost) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(getRequestConfig());
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            if (logger.isTraceEnabled())
                logger.error("不支持的客户端协议：" + e.getLocalizedMessage());
        } catch (IOException e) {
            if (logger.isTraceEnabled())
                logger.error("连接知识库服务器错误：" + e.getLocalizedMessage());
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                if (logger.isTraceEnabled())
                    logger.error("关闭知识库服务器连接时错误：" + e.getLocalizedMessage());;
            }
        }
        return responseContent;
    }

    /**
     * 发送Get请求
     * @param httpGet
     * @return
     */
    private String sendHttpGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(getRequestConfig());
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

}

