package com.booth.service;

import com.booth.pojo.WechatUser;

/**
 *
 * @date 2020-12-24 14:46
 */
public interface LoginService {

    /***
     * 获取用户登录的openId
     * @param wechatUser 用户登录的信息
     *                   code
     * @return
     */
    String getOpenId(WechatUser wechatUser);
}
