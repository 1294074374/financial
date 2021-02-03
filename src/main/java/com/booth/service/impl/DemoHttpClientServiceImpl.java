package com.booth.service.impl;

import com.booth.service.CommunicationService;


import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.logging.Logger;

/**
 * 
 * @date 2020-12-24 11:01
 */
public class DemoHttpClientServiceImpl implements CommunicationService {
    /**
     * 目标地址
     */
    private String url;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 通信连接超时时间
     */
    private int connectionTimeout;

    /**
     * 通信读超时时间
     */
    private int readTimeOut;

    /**
     * 通信结果
     */
    private String result;

    /**
     * 获取通信结果
     * @return
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置通信结果
     * @param result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 构造函数
     * @param url 目标地址
     */
    public DemoHttpClientServiceImpl(String method, String url) {
        this.url = url;
        this.method = method;
        //TODO 超时时间参数化
        int timeout = 20000;
        this.connectionTimeout = timeout;
        this.readTimeOut = timeout;
    }
    /**
     * 构造函数
     * @param method
     * @param url
     * @param timeout HTTP读写超时时间
     */
    public DemoHttpClientServiceImpl(String method, String url, int timeout) {
        this.url = url;
        this.method = method;
        this.connectionTimeout = timeout;
        this.readTimeOut = timeout;
    }

    public Object sendNReceive(Object msg) throws Exception {
        String encoding = "UTF-8";
        try {
            //组装请求参数key=value&key=value
            String sendData = (String) msg;
            HttpURLConnection httpURLConnection = null;
            //创建连接，发送请求
            System.out.println((new StringBuilder("请求地址:[")
                    .append(this.url)
                    .append("] 请求报文:[")
                    .append(sendData)
                    .toString()));
            //	MyLoggerUtils.logInfo("请求报文:[" + sendData + "]");
            if("get".equals(method)){
                String getUrl = this.url + sendData;
                httpURLConnection = createConnectionGet(new URL(getUrl), encoding);
            }else{
                httpURLConnection = createConnection(new URL(this.url), encoding);
                this.requestServer(httpURLConnection, sendData, encoding);
            }
            if(null == httpURLConnection){
                throw new Exception("创建联接失败");
            }
            this.result = this.response(httpURLConnection, encoding);
            System.out.println((new StringBuilder("同步返回报文:[")
                    .append(result))
                    .append("]").toString());
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 创建连接
     *
     * @return
     * @throws ProtocolException
     */
    private HttpURLConnection createConnection(URL url, String encoding) throws ProtocolException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //LogUtils.writeErrorLog(e.getMessage(), e);
            return null;
        }
        httpURLConnection.setConnectTimeout(this.connectionTimeout);// 连接超时时间
        httpURLConnection.setReadTimeout(this.readTimeOut);// 读取结果超时时间
        httpURLConnection.setDoInput(true); // 可读
        httpURLConnection.setDoOutput(true); // 可写
        httpURLConnection.setUseCaches(false);// 取消缓存
        httpURLConnection.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded;charset=" + encoding);
        httpURLConnection.setRequestMethod("POST");
        if ("https".equalsIgnoreCase(url.getProtocol())) {
            HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
            //husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
            //husn.setHostnameVerifier(new TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
            return husn;
        }
        return httpURLConnection;
    }

    /**
     * 读取返回数据
     * @param connection
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private String response(final HttpURLConnection connection, String encoding)
            throws URISyntaxException, IOException, Exception {
        InputStream in = null;
        StringBuilder sb = new StringBuilder(1024);
        BufferedReader br = null;
        try {
            if (200 == connection.getResponseCode()) {
                in = connection.getInputStream();
                sb.append(new String(read(in), encoding));
            } else {
                in = connection.getErrorStream();
                sb.append(new String(read(in), encoding));
                throw new Exception("请求出错:"+connection.getResponseCode());
            }
            //LogUtils.writeLog("HTTP Return Status-Code:["
            //+ connection.getResponseCode() + "]");
            return sb.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != br) {
                br.close();
            }
            if (null != in) {
                in.close();
            }
            if (null != connection) {
                connection.disconnect();
            }
        }
    }

    /**
     * HTTP Post发送消息
     *
     * @param connection
     * @param message
     * @throws IOException
     */
    private void requestServer(final URLConnection connection, String message, String encoder)
            throws Exception {
        PrintStream out = null;
        try {
            connection.connect();
            out = new PrintStream(connection.getOutputStream(), false, encoder);
            if(message!=null){
                out.print(message);
            }
            out.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 创建连接
     *
     * @return
     * @throws ProtocolException
     */
    private HttpURLConnection createConnectionGet(URL url, String encoding) throws ProtocolException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            //LogUtils.writeErrorLog(e.getMessage(), e);
            return null;
        }
        httpURLConnection.setConnectTimeout(this.connectionTimeout);// 连接超时时间
        httpURLConnection.setReadTimeout(this.readTimeOut);// 读取结果超时时间
        httpURLConnection.setUseCaches(false);// 取消缓存
        httpURLConnection.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded;charset=" + encoding);
        httpURLConnection.setRequestMethod("GET");
        if ("https".equalsIgnoreCase(url.getProtocol())) {
            HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
            //husn.setHostnameVerifier(new TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
            return husn;
        }
        return httpURLConnection;
    }

    public static byte[] read(InputStream in) throws IOException {
        byte[] buf = new byte[1024];
        int length = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            bout.write(buf, 0, length);
        }
        bout.flush();
        return bout.toByteArray();
    }
}
