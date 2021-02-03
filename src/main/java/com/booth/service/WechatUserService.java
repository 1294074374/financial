package com.booth.service;

import com.booth.pojo.WechatUser;

/**
 *
 * @date 2020-12-22 22:26
 */
public interface WechatUserService {

    /***
     * 添加一个新的登陆信息
     * @param wechatUser
     * @return
     */
    String loginWechatUserInfo(WechatUser wechatUser);
}
