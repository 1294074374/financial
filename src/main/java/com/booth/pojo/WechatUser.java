package com.booth.pojo;

/**
 * 
 * @date 2020-12-22 22:12
 */
public class WechatUser {
    /** 用户头像链接 **/
    private String avatarUrl;
    /** 用户登录城市 **/
    private String city;
    /** 用户登录省份 **/
    private String province;
    /** 用户登录国家 **/
    private String country;
    /** 语言 **/
    private String language;
    /** 用户名 **/
    private String nickName;
    /** code **/
    private String code;
    /** 回话ID **/
    private String openId;
    /** 日期 **/
    private String date;

    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WechatUser{");
        sb.append("avatarUrl='").append(avatarUrl).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", openId='").append(openId).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

