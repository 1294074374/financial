package com.booth.service;

import com.booth.pojo.BillVO;

/**
 * @author laijunlin
 * @date 2021-01-31 14:31
 */
public interface IBillFileService {

    /**
     * 加载账单信息入数据库
     * @return 加载标志
     */
    String loadBillFile();

    /**
     * 根据用户信息 加载我的页面中的数据
     * @param wechatUser 用户信息
     * @return 压面数据
     */
    String loadMyPagesDate(BillVO billVO);
}
