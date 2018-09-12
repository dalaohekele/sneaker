package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.OrderMaster;
import com.zl.sneakerentity.enums.OrderStatusEnum;
import com.zl.sneakerentity.enums.PayStatusEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 15:52
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderMasterTest {
    @Autowired
    OrderMasterDao orderMasterDao;

    @Test
    @Ignore
    public void insertOrderMasterTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("可乐");
        orderMaster.setBuyerPhone("123123123");
        orderMaster.setBuyerAddress("软件");
        orderMaster.setBuyerOpenid("1101110");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getState());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getState());

//        Integer effectnum = orderMasterDao.insertOrderMaster(orderMaster);
//        System.out.println(effectnum);
    }

    @Test
    @Ignore
    public void selectOrderMaster(){
        List<OrderMaster> orderMaster = orderMasterDao.selectOrderMasterByOpenid("111zzzz",1,1);
        System.out.println(orderMaster.get(0).getBuyerAddress());
    }

    @Test
    public void selectOrderDetailByOpenidTest(){
        List<OrderMaster> orderMaster = orderMasterDao.selectOrderDetailByOpenid("1534325856245904736",1,1);
        System.out.println(orderMaster.get(0));
    }

    @Test
    @Ignore
    public void selectOpenIdandOrderIdTest(){
        Integer effectNum = orderMasterDao.selectOpenIdandOrderId("111zzzz","1533020341704620021");
        System.out.println(effectNum);
    }

    @Test
    @Ignore
    public void updateOrderByIdTest(){
        Integer effectNum = orderMasterDao.updateOrderById("111zzzz","1533020341704620021");
        System.out.println(effectNum);
    }

}
