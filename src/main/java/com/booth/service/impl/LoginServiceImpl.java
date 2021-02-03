package com.booth.service.impl;

import com.booth.pojo.WechatUser;
import com.booth.service.CommunicationService;
import com.booth.service.LoginService;
import com.booth.util.RtCode;
import com.booth.util.SystemConstantUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @date 2020-12-24 14:48
 */
@Service
public class LoginServiceImpl implements LoginService {
    public String getOpenId(WechatUser wechatUser) {
        if(wechatUser.getCode() == null || "".equals(wechatUser.getCode())){
            return RtCode.composeRtMsg(RtCode.ABSTKEYPARAMS);
        }
        final String openId = "openid";
        String url = SystemConstantUtil.GET_OPEN_ID_URL;
        url = url.replace("#{appId}",SystemConstantUtil.APP_ID);
        url = url.replace("#{secret}",SystemConstantUtil.APP_SECRET);
        url = url.replace("#{jsCode}",wechatUser.getCode());
        try{
            CommunicationService httpClientService = new HttpClientServiceImpl(url);
            String result = (String) httpClientService.sendNReceive(null);
            JSONObject json = JSONObject.fromObject(result);
            if(json.has(openId)){
                wechatUser.setOpenId((String) json.get(openId));
                return RtCode.SUCCESS;
            }else{
                return result;
            }
        }catch(JSONException e){
            System.out.println("获取openId,json格式转换异常");
            return RtCode.composeRtMsg(RtCode.JSONCHANGEDOWN);
        }catch(Exception e){
            System.out.println("获取openIdy异常");
            return RtCode.composeRtMsg(RtCode.UNKNOWN);
        }
    }
}
