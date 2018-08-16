package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther: le
 * @Date: 2018/8/14 17:16
 * @Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    UserDao userDao;

    @Test
    @Ignore
    public void insertUserTest(){
        User user = new User();
        user.setId("1111");
        user.setPassWord("asdalas");
        user.setRole(1);
        user.setHeadImage("/image");
        user.setLoginCount(10);

        int effectNum = userDao.insertUser(user);
        System.out.println(effectNum);

    }

//    @Test
//    @Ignore
//    public void selectUserByIdTest(){
//        User user = userDao.selectUserById("1111");
//        System.out.println(user.getUserName());
//    }

    @Test
    public void updateUserPasswordTest(){
        User user = new User();
        user.setId("1111");
        user.setPassWord("new pwd");
        int effectNum = userDao.updateUserPassword(user);
        System.out.println(effectNum);
    }

}
