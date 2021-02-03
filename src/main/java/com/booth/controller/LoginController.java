package com.booth.controller;

import com.booth.pojo.WechatUser;
import com.booth.service.LoginService;
import com.booth.service.WechatUserService;
import com.booth.util.RtCode;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @date 2020-12-22 22:43
 */
@Controller
public class LoginController {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value= "/getOpenId",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getOpenId(WechatUser wechatUser){
        String getOpenIdResult = loginService.getOpenId(wechatUser);
        if(RtCode.SUCCESS.equals(getOpenIdResult)){
            String insertRt = wechatUserService.loginWechatUserInfo(wechatUser);
            if(RtCode.SUCCESS.equals(insertRt)){
                JSONObject result = new JSONObject();
                result.put("openid",wechatUser.getOpenId());
                return RtCode.composeRtMsg(RtCode.SUCCESS,result);
            }else{
                return insertRt;
            }
        }else{
            return getOpenIdResult;
        }
    }
}
