package com.zl.sneakerserver.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther: le
 * @Date: 2018/8/13 16:41
 * @Description:
 */
@Configuration
public class MQConfig {
    public static final String SECKILL_QUEUE = "seckill.queue";
    public static final String QUEUE="queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicExchange";

    /**
     * direct 模式交换机exchange
     * 发送者先发送到交换机上，然后交换机作为路由再将信息发到队列
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE,true);
    }

    /**
     *
     * topic模式，exchange交换机
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1,true);
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2,true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    //绑定
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }
}
