package com.booth.service.impl;

import com.booth.dao.WechatUserDao;
import com.booth.pojo.WechatUser;
import com.booth.service.WechatUserService;
import com.booth.util.RtCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @date 2020-12-22 22:25
 */
@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private WechatUserDao wechatUserDao;

    public String loginWechatUserInfo(WechatUser wechatUser) {
        try{
            WechatUser wechatUserDB = wechatUserDao.selcectWechatUserByOpenId(wechatUser);
            int result = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            wechatUser.setDate(sdf.format(new Date()));
            if(wechatUserDB != null){
                result = wechatUserDao.updateWechatUser(wechatUser);
            }else{
                result = wechatUserDao.insertWechatUser(wechatUser);
            }
            if(result == 1){
                return RtCode.SUCCESS;
            }else{
                return RtCode.composeRtMsg(RtCode.FAILED);
            }
        }catch(Exception e){
            return RtCode.composeRtMsg(RtCode.DBDOWN);
        }
    }
}
