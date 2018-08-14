package com.zl.sneakerserver.rabbitmq;

import com.zl.sneakerserver.dto.OrderDto;

/**
 * @Auther: le
 * @Date: 2018/8/13 17:38
 * @Description:
 */
//消息体
public class OrderMQMessage {
    private OrderDto orderDto;
    private String productId;


    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
