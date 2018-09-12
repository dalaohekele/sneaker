package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.OrderDetail;
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
public class OrderDetailTest {

    @Autowired
    OrderDetailDao orderDetailDao;

    @Test
    @Ignore
    public void insertOrderDetail(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("2");
        orderDetail.setProductIcon("/image");
        orderDetail.setDetailId("12367");
        orderDetail.setProductId("157875196366160022");
        orderDetail.setProductName("shangp");
        orderDetail.setProductPrice(new BigDecimal(0.02));
        orderDetail.setProductQuantity(2);

        int effectNum = orderDetailDao.insertOrderDetail(orderDetail);
        System.out.println(effectNum);
    }

    @Test
    @Ignore
    public void selectOrderDetailListTest(){
        List<OrderDetail> orderDetailList = orderDetailDao.selectOrderDetailList("1533276908006684900");
        System.out.println(orderDetailList.get(0).getProductName());
    }
}
