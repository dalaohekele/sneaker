package com.zl.sneakerserver.server;

import com.zl.sneakerserver.SneakerServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther: le
 * @Date: 2018/8/28 17:32
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SneakerServerApplication.class)
public class CartServerTest {
    @Autowired
    CartServer cartServer;

    @Test
    public void addCartTest(){
        cartServer.addCart("1534325821745303440","223",1);
    }
}
