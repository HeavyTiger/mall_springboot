package com.heavytiger.mall.mq;

import com.heavytiger.mall.config.RabbitMQConfig;
import com.heavytiger.mall.service.SeckillOrderService;
import com.heavytiger.mall.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * @author heavytiger
 * @version 1.0
 * @description 接收消息
 * @date 2022/2/21 20:30
 */
@Service
public class MQReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void receiveSeckillMessage(String message) {
        HashMap<String, Integer> map = JsonUtil.jsonToObj(message, HashMap.class);
        Integer userId = map.get("userId");
        Integer seckillId = map.get("seckillId");
        // 将秒杀成功的订单与Redis中的记录进行比较，如果存在说明该记录正确，并将Redis中的订单数据删除
        Long result = redisTemplate.opsForSet().remove("seckillOrder:" + seckillId, userId);
        if (result == null) {
            // 说明此时集合不存在，其中的元素已经全被删除
            logger.info("Redis中秒杀商品{}的订单集合已被全部删除！", seckillId);
            throw new RuntimeException("Redis中" + seckillId + "商品的订单集合已被全部删除！");
        } else if (result == 1) {
            // 说明只删除了一个，进行记录
            logger.info("用户{}秒杀商品{}成功!", userId, seckillId);
        } else {
            // 说明没有删除，没有删除则证明订单已经被创建了，直接返回以免出现重复消费的情况
            logger.warn("用户{}已经秒杀商品{}！请勿重复创建订单！", userId, seckillId);
        }
        // 添加秒杀订单，此操作会先将商品加入购物车，再将购物车中的商品结算，结算完成后生成订单，再将订单数据回填到秒杀订单中
        if(seckillOrderService.addSeckillOrder(seckillId, userId) == 1) {
            // 说明订单创建成功
            logger.info("用户{}秒杀商品{}号的订单被成功创建!", userId, seckillId);
        }
    }
}
