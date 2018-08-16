package com.zl.sneakerentity.model;

import java.util.Date;

/**
 * @Auther: le
 * @Date: 2018/8/14 16:29
 * @Description:
 */

/**
 * 用户实体类
 */
public class User {
    private String Id;
    /* 用户名*/
    private String userName;
    /* 密码*/
    private String passWord;
    /* 用户角色*/
    private int role;
    /* 头像*/
    private String headImage;
    /* 登陆次数*/
    private int loginCount;
    private Date createTime;

    private Date updateTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
