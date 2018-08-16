package com.zl.sneakerweb.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Auther: le
 * @Date: 2018/8/16 22:03
 * @Description:
 */
public class CustomerAuthenticationToken extends UsernamePasswordToken {
    private String token;

    /** 验证码，预留字段*/
    private String captcha;

    public CustomerAuthenticationToken(String username,String password,boolean rememberMe){
        super(username,password,rememberMe);
    }

    public CustomerAuthenticationToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
