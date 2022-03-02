package com.heavytiger.mall.mq;

import com.heavytiger.mall.config.RabbitMQConfig;
import com.heavytiger.mall.util.JsonUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heavytiger
 * @version 1.0
 * @description 发送消息
 * @date 2022/2/21 20:30
 */
@Service
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void sendSeckillMessage(Integer userId, Integer seckillId) {
        Map<String, Integer> message = new HashMap<>();
        message.put("userId", userId);
        message.put("seckillId", seckillId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "seckill.message", JsonUtil.objToJson(message));
    }
}
