package com.booth.util;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @date 2020-12-22 22:28
 */
public class RtCode {
    public static final String SUCCESS = "0000";

    public static final String TRYMOREINFO = "0001";

    public static final String FAILED = "1000";

    public static final String UNCERTAIN = "2000";

    public static final String OPOFTEN = "4010";

    public static final String PW = "4013";

    public static final String USERLOCK = "4014";

    public static final String AUTHCODERRORTOMUCH = "4015";

    public static final String ACCESSRESTRICT = "4200";

    public static final String COMMFAIL = "5006";

    public static final String ABSTKEYPARAMS = "5007";
    public static final String JSONCHANGEDOWN = "5008";

    public static final String DBDOWN = "5010";

    public static final String UNKNOWN = "9999";


    public static Map<String, String> rtCodeDict = new HashMap<String, String>();

    static {
        rtCodeDict.put(RtCode.SUCCESS, "成功");
        rtCodeDict.put(RtCode.FAILED, "失败");
        rtCodeDict.put(RtCode.UNCERTAIN, "不确定");
        rtCodeDict.put(RtCode.OPOFTEN, "操作太频繁");
        rtCodeDict.put(RtCode.PW, "密码错误");
        rtCodeDict.put(RtCode.USERLOCK, "用户锁定");
        rtCodeDict.put(RtCode.AUTHCODERRORTOMUCH, "验证码错误次数超限");
        rtCodeDict.put(RtCode.ACCESSRESTRICT, "访问无权限");
        rtCodeDict.put(RtCode.ABSTKEYPARAMS, "缺少关键参数");
        rtCodeDict.put(RtCode.DBDOWN, "数据库服务异常");
        rtCodeDict.put(RtCode.JSONCHANGEDOWN, "JSON类型转换异常");
        rtCodeDict.put(RtCode.COMMFAIL, "通讯异常");
        rtCodeDict.put(RtCode.UNKNOWN, "未知错误");
    }
    public static String composeRtMsg(String rtCode)
    {
        JSONObject jsonObj = new JSONObject();
        if (rtCodeDict.containsKey(rtCode))
        {
            jsonObj.put("rtCode", rtCode);
            jsonObj.put("rtMsg", rtCodeDict.get(rtCode));
        }
        else
        {
            jsonObj.put("rtCode", RtCode.UNKNOWN);
            jsonObj.put("rtMsg", rtCodeDict.get(RtCode.UNKNOWN));
        }
        return jsonObj.toString();
    }

    public static String composeRtMsg (String rtCode, String desc)
    {
        JSONObject jsonObj = new JSONObject();
        if (rtCodeDict.containsKey(rtCode))
        {
            jsonObj.put("rtCode", rtCode);
            jsonObj.put("rtMsg", desc);
        }
        else
        {
            jsonObj.put("rtCode", RtCode.UNKNOWN);
            jsonObj.put("rtMsg", desc);
        }
        return jsonObj.toString();
    }

    public static String composeRtMsg (String rtCode, JSONObject jobj)
    {
        JSONObject jsonObj = new JSONObject();
        if (rtCodeDict.containsKey(rtCode))
        {
            jsonObj.put("rtCode", rtCode);
            jsonObj.put("rtMsg", rtCodeDict.get(rtCode));
            jsonObj.put("data", jobj);
        }
        else
        {
            jsonObj.put("rtCode", RtCode.UNKNOWN);
            jsonObj.put("rtMsg", rtCodeDict.get(RtCode.UNKNOWN));
            jsonObj.put("data", jobj);
        }
        return jsonObj.toString();
    }

    public static String composeRtMsg (String rtCode, JSONObject jobj, String desc)
    {
        JSONObject jsonObj = new JSONObject();
        if (rtCodeDict.containsKey(rtCode))
        {
            jsonObj.put("rtCode", rtCode);
            jsonObj.put("rtMsg", rtCodeDict.get(rtCode)+"-"+desc);
            jsonObj.put("data", jobj);
        }
        else
        {
            jsonObj.put("rtCode", RtCode.UNKNOWN);
            jsonObj.put("rtMsg", rtCodeDict.get(RtCode.UNKNOWN));
            jsonObj.put("data", jobj);
        }
        return jsonObj.toString();
    }
}
