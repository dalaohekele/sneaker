package com.zl.sneakerserver.rabbitmq;

import com.zl.sneakerentity.model.SeckillOrder;
import com.zl.sneakerentity.model.SeckillProduct;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerserver.dto.OrderDto;
import com.zl.sneakerserver.dto.SeckillDto;
import com.zl.sneakerserver.exceptions.OrderException;
import com.zl.sneakerserver.server.OrderServer;
import com.zl.sneakerserver.server.ProductInfoServer;
import com.zl.sneakerserver.server.SeckillService;
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

    @Autowired
    SeckillService seckillService;

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

    /**
     *  秒杀MQ
     * @param message
     * @throws OrderException
     */
    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receiveSeckillOrder(String message) throws OrderException {
        log.info("receive SeckillOrder message:" + message);
        SeckillMessage m = RedisService.stringToBean(message, SeckillMessage.class);

        SeckillProduct seckillProduct = m.getSeckillProduct(); // 订单数据
        User user = m.getUser();

        SeckillDto goodsVo = seckillService.getSeckillByProdcutID(seckillProduct.getProductId());
        int stock = goodsVo.getSeckillProduct().getProductStock();
        if(stock <= 0){
            return;
        }
        //判断重复秒杀
        SeckillOrder order = orderServer.getOrderByUserIdGoodsId(user.getId(), seckillProduct.getProductId());
        if(order != null) {
            return;
        }

        seckillService.seckill(user,seckillProduct);
        //创建订单，存入数据库
//        orderServer.seckillOrder(user,seckillProduct);

    }

}
