package com.booth.util;

/**
 * 
 * @date 2020-12-24 13:23
 */
public class SystemConstantUtil {

    /** 微信小程序的AppId **/
    public final static String APP_ID = "wx1914b2b4819f5938";
    /** 微信小程序开发者密码 **/
    public final static String APP_SECRET = "e1d4f36cca29700415575972391ea07b";
    /** 获取openId的接口 **/
    public final static String GET_OPEN_ID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=#{appId}&secret=#{secret}&js_code=#{jsCode}&grant_type=authorization_code";
}
