package com.zl.sneakerserver.rabbitmq;

import com.zl.sneakerentity.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: le
 * @Date: 2018/8/13 16:41
 * @Description:
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendTopic(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send topic message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1",msg+"1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key2",msg+"2");

    }

    //调用receiveOrder的receiveOrder方法 ------ 此处为生产者
    public void sendOrderMessage(OrderMQMessage message){
        String msg = RedisService.beanToString(message);
        log.info("order send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
    }
}
