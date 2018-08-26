package com.zl.sneakerserver.server;

import com.zl.sneakerentity.model.User;
import com.zl.sneakerserver.SneakerServerApplication;
import com.zl.sneakerserver.dto.UserDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther: le
 * @Date: 2018/8/15 17:12
 * @Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SneakerServerApplication.class)
public class UserTest {

    @Autowired
    UserServer userServer;

    @Test
    @Ignore
    public void registerUserTest(){
        User user = new User();
        user.setUserName("qwe");
        user.setPassWord("123456");
        user.setRole(1);
        user.setHeadImage("/image");

        UserDto userDto = userServer.registerUser(user);
        System.out.println(userDto.getStateInfo());
    }

    @Test
    @Ignore
    public void getUserTest(){
        User user = userServer.getUser("zzl");
        System.out.println(user.getId());
    }

    @Test
    @Ignore
    public void updateUserPassword(){
        User user = new User();
        user.setId("1111");
        user.setPassWord("123456");
        UserDto userDto = userServer.updateUserPassword(user);
        System.out.println(userDto.getStateInfo());
    }

    @Test
    public void updateUserHeadTest(){
        User user = new User();
        user.setId("1111");
        user.setHeadImage("/image/new");
        UserDto userDto = userServer.updateUserHead(user);
        System.out.println(userDto.getUser().getUpdateTime());
    }
}
