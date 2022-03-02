package com.heavytiger.mall.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author heavytiger
 * @version 1.0
 * @description RabbitMQ配置类
 * @date 2022/2/21 20:10
 */
@Configuration
public class RabbitMQConfig {
    // 配置队列名，使用Topic模式
    public static final String QUEUE = "seckillQueue";
    // 配置交换器名
    public static final String EXCHANGE = "seckillExchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding() {
        // 匹配路径为seckill.#的队列
        return BindingBuilder.bind(queue()).to(topicExchange()).with("seckill.#");
    }
}
