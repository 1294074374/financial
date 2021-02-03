package com.booth.dao;

import com.booth.pojo.WechatUser;

/**
 *
 * @date 2020-12-22 22:23
 */
public interface WechatUserDao {
    /***
     * 更新用户登录信息
     * @param wechatUser 需要更新的用户信息
     * @return 成功插入条数
     */
    Integer updateWechatUser(WechatUser wechatUser);

    /**
     * 通过openId 查询用户
     * @param wechatUser 包含用户的openId信息
     * @return
     */
    WechatUser selcectWechatUserByOpenId(WechatUser wechatUser);

    /**
     * 新增微信用户信息
     * @param wechatUser
     * @return
     */
    Integer insertWechatUser(WechatUser wechatUser);
}
