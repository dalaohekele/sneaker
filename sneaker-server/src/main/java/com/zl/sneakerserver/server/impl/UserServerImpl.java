package com.zl.sneakerserver.server.impl;

import com.zl.sneakerentity.dao.UserDao;
import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerserver.dto.UserDto;
import com.zl.sneakerserver.exceptions.UserException;
import com.zl.sneakerserver.server.UserServer;
import com.zl.sneakerserver.utils.KeyUtils;
import com.zl.sneakerserver.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: le
 * @Date: 2018/8/15 14:03
 * @Description:
 */
@Service
@Slf4j
public class UserServerImpl implements UserServer {
    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;


    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public UserDto registerUser(User user) throws UserException {
        try {
            //判断userName是否重复
            int requirmentName = userDao.requirmentUserByName(user.getUserName());
            if (requirmentName > 0) {
                return new UserDto(ResultEnum.REQUIRMENT_USERNAME);
            }
            //生成userid
            String userId = KeyUtils.genUniqueKey();
            //密码加密
            String password = MD5Util.inputPassToDbPass(user.getPassWord());

            user.setId(userId);
            user.setPassWord(password);
            user.setCreateTime(new Date());
            //添加用户
            int effectNum = userDao.insertUser(user);
            if (effectNum <= 0) {
                return new UserDto(ResultEnum.REGISTER_FAIL);
            } else {
                //注册成功
                return new UserDto(ResultEnum.REGISTER_SUCESS);
            }

        } catch (Exception e) {
            log.error(ResultEnum.REGISTER_FAIL.getMessage());
            throw new UserException(ResultEnum.REGISTER_FAIL.getMessage() + ":" + e.getMessage());
        }
    }

    /**
     * 通过userName查询用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public User getUser(String userName) {
//        UserDto userDto = new UserDto();
        try {
            User user = userDao.selectUserByName(userName);
//            userDto.setUser(user);
            return user;
        } catch (Exception e) {
            throw new UserException(ResultEnum.FINDUSER_FAIL.getMessage());
        }

    }

    @Override
    public User getUserById(String userId) {
        try {
            User user = userDao.selectUserById(userId);
            return user;
        } catch (Exception e) {
            throw new UserException(ResultEnum.FINDUSER_FAIL.getMessage());
        }
    }

    /**
     * 更新用户密码
     *
     * @param user
     * @return
     */
    @Override
    public UserDto updateUserPassword(User user) {
        try {
            //加密
            String password = MD5Util.inputPassToDbPass(user.getPassWord());
            user.setPassWord(password);

            int effectNum = userDao.updateUserPassword(user);
            if (effectNum <= 0) {
                return new UserDto(ResultEnum.REQUIRMENT_USERNAME);
            }
            return new UserDto(ResultEnum.PASSWORD_UPDATE_SUCCESS);
        } catch (Exception e) {
            throw new UserException("密码更新失败 :" + e.getMessage());
        }
    }

}
