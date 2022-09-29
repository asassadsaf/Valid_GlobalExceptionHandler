package com.fkp.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HttpUtils {
    private static final PoolingHttpClientConnectionManager cm;
    private static final RequestConfig requestConfig;

    static {

        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("创建SSL连接失败");
        }
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        if (sslsf != null){
            registryBuilder.register("https", sslsf);
        }
        registryBuilder.register("http", new PlainConnectionSocketFactory());
        Registry<ConnectionSocketFactory> socketFactoryRegistry = registryBuilder.build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        //多线程调用注意配置，根据线程数设定
        cm.setMaxTotal(200);
        //多线程调用注意配置，根据线程数设定
        cm.setDefaultMaxPerRoute(300);
        //重点参数，请求前以及在多少毫秒后重新校验链接是否有效
        cm.setValidateAfterInactivity(10000);
        requestConfig = RequestConfig.custom()
                //数据传输过程中数据包之间间隔的最大时间
                .setSocketTimeout(20000)
                //连接建立时间，三次握手完成时间
                .setConnectTimeout(20000)
                //重点参数
                .setExpectContinueEnabled(true)
                .setConnectionRequestTimeout(10000)
                .build();
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    private static void closeResponse(CloseableHttpResponse closeableHttpResponse) {
        try {
            EntityUtils.consume(closeableHttpResponse.getEntity());
            closeableHttpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("httpClient CloseResponse error: {}", e.getMessage());
        }
    }

    /**
     * get请求,params可为null,headers可为null
     *
     * @param headers 请求头
     * @param url 请求url
     * @return 响应结果
     * @throws IOException 抛出IO异常
     */
    public static String get(JSONObject headers, String url, JSONObject params) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        // 创建get请求
        HttpGet httpGet = null;
        List<BasicNameValuePair> paramList = new ArrayList<>();
        if (params != null) {
            for (String paramName : params.keySet()) {
                paramList.add(new BasicNameValuePair(paramName, params.get(paramName).toString()));
            }
        }
        if (url.contains("?")) {
            httpGet = new HttpGet(url + "&" + EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8)));
        } else {
            httpGet = new HttpGet(url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(paramList, Consts.UTF_8)));
        }

        if (headers != null) {
            for (String headerName : headers.keySet()) {
                httpGet.addHeader(headerName, headers.get(headerName).toString());
            }
        }
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("lastOperaTime", String.valueOf(System.currentTimeMillis()));
        String response = StringUtils.EMPTY;
        try {
            closeableHttpResponse = httpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();
            response = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
            log.error("httpClient execute error: {}", e.getMessage());
        }finally {
            if (closeableHttpResponse != null) {
                closeResponse(closeableHttpResponse);
            }
        }
        return response;
    }

    /**
     * post请求,params可为null,headers可为null
     *
     * @param headers 请求头
     * @param url 请求url
     * @param params 请求参数
     * @return 响应结果
     * @throws IOException 抛出IO异常
     */
    public static String post(JSONObject headers, String url, JSONObject params) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (String headerName : headers.keySet()) {
                httpPost.addHeader(headerName, headers.get(headerName).toString());
            }
        }
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("lastOperaTime", String.valueOf(System.currentTimeMillis()));
        if (params != null) {
            StringEntity stringEntity = new StringEntity(params.toJSONString(), "UTF-8");
            httpPost.setEntity(stringEntity);
        }
        String response = StringUtils.EMPTY;
        try {
            closeableHttpResponse = httpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            response = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
            log.error("httpClient execute error: {}", e.getMessage());
        }finally {
            if (closeableHttpResponse != null) {
                closeResponse(closeableHttpResponse);
            }
        }
        return response;
    }

    /**
     * delete,params可为null,headers可为null
     *
     * @param url 请求url
     * @param params 请求参数
     * @return 响应结果
     * @throws IOException 抛出IO异常
     */
    public static String delete(JSONObject headers, String url, JSONObject params) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        // 创建delete请求，HttpDeleteWithBody 为内部类，类在下面
        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
        if (headers != null) {
            for (String headerName : headers.keySet()) {
                httpDelete.addHeader(headerName, headers.get(headerName).toString());
            }
        }
        httpDelete.setConfig(requestConfig);
        httpDelete.addHeader("Content-Type", "application/json");
        httpDelete.addHeader("lastOperaTime", String.valueOf(System.currentTimeMillis()));
        if (params != null) {
            StringEntity stringEntity = new StringEntity(params.toJSONString(), "UTF-8");
            httpDelete.setEntity(stringEntity);
        }
        String response = StringUtils.EMPTY;
        try {
            closeableHttpResponse = httpClient.execute(httpDelete);
            HttpEntity entity = closeableHttpResponse.getEntity();
            response = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
            log.error("httpClient execute error: {}", e.getMessage());
        }finally {
            if (closeableHttpResponse != null) {
                closeResponse(closeableHttpResponse);
            }
        }
        return response;
    }

    /**
     * put,params可为null,headers可为null
     *
     * @param url 请求url
     * @param params 请求参数
     * @return 响应结果
     * @throws IOException 抛出IO异常
     */
    public static String put(JSONObject headers, String url, JSONObject params) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        // 创建put请求
        HttpPut httpPut = new HttpPut(url);
        if (headers != null) {
            for (String headerName : headers.keySet()) {
                httpPut.addHeader(headerName, headers.get(headerName).toString());
            }
        }
        httpPut.setConfig(requestConfig);
        httpPut.addHeader("Content-Type", "application/json");
        httpPut.addHeader("lastOperaTime", String.valueOf(System.currentTimeMillis()));
        if (params != null) {
            StringEntity stringEntity = new StringEntity(params.toJSONString(), "UTF-8");
            httpPut.setEntity(stringEntity);
        }
        // 从响应模型中获得具体的实体
        String response = StringUtils.EMPTY;
        try {
            closeableHttpResponse = httpClient.execute(httpPut);
            HttpEntity entity = closeableHttpResponse.getEntity();
            response = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
            log.error("httpClient execute error: {}", e.getMessage());
        }finally {
            if(closeableHttpResponse != null){
                closeResponse(closeableHttpResponse);
            }
        }
        return response;
    }

    private static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }

        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }

        public HttpDeleteWithBody() {
            super();
        }
    }

    public static void main(String[] args) throws IOException {
        JSONObject heads = new JSONObject();
        heads.put("t","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTQ1OTE0MzEsIm5iZiI6MTY2MzQ4OTIzMSwiaWF0IjoxNjYzNDg3NDMxLCJqdGkiOiJDTTpjYXRfbWF0Y2g6bHQxMjM0NTYiLCJvcGVuX2lkIjoiIiwidWlkIjoyMjA1NjMyMTgsImRlYnVnIjoiIiwibGFuZyI6IiJ9.zil2REb3jXcwM_5YNw6NpAsZHiUc3IEN77t1Ituk73o");
        JSONObject params = new JSONObject();
        params.put("rank_time",1);
        params.put("rank_score",1);
        params.put("rank_state",1);
        params.put("rank_role",1);
        params.put("skin",1);
        String s = get(heads, "https://cat-match.easygame2021.com/sheep/v1/game/game_over", params);
        System.out.println(s);
    }
}

