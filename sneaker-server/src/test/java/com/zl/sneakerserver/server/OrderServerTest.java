package com.zl.sneakerserver.server;

import com.zl.sneakerentity.model.OrderDetail;
import com.zl.sneakerserver.SneakerServerApplication;
import com.zl.sneakerserver.dto.OrderDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 19:02
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SneakerServerApplication.class)
public class OrderServerTest {
    @Autowired
    OrderServer orderServer;

    @Test
    @Ignore
    public void createOrderTest(){
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("zl");
        orderDto.setBuyerAddress("深圳 宝安");
        orderDto.setBuyerPhone("138757575");
        orderDto.setBuyerOpenid("qwe123");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1");
        orderDetail.setProductQuantity(2);

        orderDetailList.add(orderDetail);

        orderDto.setOrderDetailList(orderDetailList);

        OrderDto result = orderServer.createOrder(orderDto);
        System.out.println(result);
        System.out.println(result.getBuyerAddress());

    }

    @Test
    public void findByOpenIdTest(){
        OrderDto orderDto= orderServer.findListByOpenId("111zzzz",1,10);
        System.out.println(orderDto.getOrderMasterList().get(0).getBuyerAddress());
    }
}
