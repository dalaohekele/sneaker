package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.User;

/**
 * @Auther: le
 * @Date: 2018/8/14 16:43
 * @Description:
 */
public interface UserDao {

    /**
     * 添加用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 更新密码
     * @param user
     * @return
     */
    int updateUserPassword(User user);

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    User selectUserByName(String userName);

    /**
     * 通过id查找用户
     * @param userId
     * @return
     */
    User selectUserById(String userId);

    /**
     * 判断用户名是否重复
     * @param userName
     * @return
     */
    int requirmentUserByName(String userName);

    /**
     * 修改用户头像
     * @param user
     * @return
     */
    int updateUserHeadById(User user);

    /**
     * 通过openId查询用户
     * @param openId
     * @return
     */
    User selectUserByOpenId(String openId);


}
