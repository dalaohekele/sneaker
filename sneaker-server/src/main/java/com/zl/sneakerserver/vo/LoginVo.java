package com.zl.sneakerserver.vo;

import javax.validation.constraints.NotNull;

/**
 * @Auther: le
 * @Date: 2018/8/15 20:48
 * @Description:
 */
public class LoginVo {

    @NotNull(message = "请填写用户名")
    private String userName;

    @NotNull(message = "请填写密码")
    private String password;

    @Override
    public String toString(){
        return "LoginVo{" +
                "user_name='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
