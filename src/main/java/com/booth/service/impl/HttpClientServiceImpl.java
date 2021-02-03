package com.booth.service.impl;

import com.booth.service.CommunicationService;
import com.booth.util.RtCode;
import com.booth.util.SystemConstantUtil;
import jdk.internal.util.xml.impl.Input;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @date 2020-12-24 13:30
 */
public class HttpClientServiceImpl implements CommunicationService {

    /** 请求地址 **/
    private String url;
    /** 请求方式 **/
    private String method;
    /** 通信连接超时时间 */
    private int connectionTimeout;
    /** 通信读超时时间 */
    private int readTimeOut;
    /** 通信结果 */
    private String result;

    public HttpClientServiceImpl(String url){
        this.url = url;
        this.method = "GET";
        this.connectionTimeout = 120000;
        this.readTimeOut = 120000;
    }


    public Object sendNReceive(Object msg) throws Exception {
        String encoding = "UTF-8";
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        StringBuilder sb = new StringBuilder(1024);
        try {
            // 1.构建http连接
            String sendData = (String) msg;
            URL urlObject = new URL(url);
            httpURLConnection = (HttpURLConnection) urlObject.openConnection();
            // 设置超时时间
            httpURLConnection.setConnectTimeout(this.connectionTimeout);
            // 设置读取超时时间
            httpURLConnection.setReadTimeout(this.readTimeOut);
            // 取消缓存
            httpURLConnection.setUseCaches(false);
            // 设置请求头
            httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + encoding);
            // 设置请求方法
            httpURLConnection.setRequestMethod(this.method);

            // 2.发送请求
            if (httpURLConnection == null) {
                System.out.println("创建联接失败");
            }
            in = httpURLConnection.getInputStream();
            sb.append(new String(read(in), encoding));
            System.out.println(sb.toString());
            return sb.toString();
        } catch (MalformedURLException e) {
            System.out.println("构建http连接异常");
            return RtCode.composeRtMsg(RtCode.COMMFAIL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return RtCode.composeRtMsg(RtCode.UNKNOWN);
        }
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
