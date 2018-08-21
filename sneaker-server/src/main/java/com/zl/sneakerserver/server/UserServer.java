package com.zl.sneakerserver.server;

import com.zl.sneakerentity.model.User;
import com.zl.sneakerserver.dto.UserDto;

/**
 * @Auther: le
 * @Date: 2018/8/15 13:55
 * @Description:
 */
public interface UserServer {

    /**
     * 注册
     * @param user
     * @return
     */
    UserDto registerUser(User user);

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    User getUser(String userName);

    /**
     * 根据id获取信息
     * @param userId
     * @return
     */
    User getUserById(String userId);

    /**
     * 更新密码
     * @param user
     * @return
     */
    UserDto updateUserPassword(User user);

}
