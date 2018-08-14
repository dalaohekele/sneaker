package com.zl.sneakerserver.rabbitmq;

import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerserver.dto.OrderDto;
import com.zl.sneakerserver.exceptions.OrderException;
import com.zl.sneakerserver.server.OrderServer;
import com.zl.sneakerserver.server.ProductInfoServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: le
 * @Date: 2018/8/13 16:41
 * @Description:
 */
@Service
@Slf4j
public class MQReceiver {


    @Autowired
    ProductInfoServer productInfoServer;

    @Autowired
    OrderServer orderServer;

    //创建订单的mq的方法，对应的队列是MQConfig.QUEUE，
    //amqpTemplate.convertAndSend通过这个发送mq消息，-------此处为消费者
    @RabbitListener(queues = MQConfig.QUEUE)
    public void receiveOrder(String message) throws OrderException {
        log.info("receive message:" + message);
        OrderMQMessage m = RedisService.stringToBean(message, OrderMQMessage.class);

        OrderDto orderDto = m.getOrderDto(); // 订单数据
//        String productId = m.getProductId();  //商品信息，用来判断商品库存

        //创建订单，存入数据库
        orderServer.createOrder(orderDto);

    }


}
