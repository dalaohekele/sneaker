package com.zl.sneakerserver.dto;

import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.User;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/8/14 17:59
 * @Description:
 */
public class UserDto {
    private Integer state;
    private String stateInfo;

    private User user;

    private List<User> userList;

    public UserDto(){}

    public UserDto(ResultEnum resultEnum){
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getMessage();
    }

    public UserDto(ResultEnum resultEnum,User user){
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getMessage();
        this.user = user;
    }

    public UserDto(ResultEnum resultEnum,List<User> userList){
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getMessage();
        this.userList = userList;
    }



    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
