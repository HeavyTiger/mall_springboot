package com.heavytiger.mall.service.impl;

import com.heavytiger.mall.mapper.SeckillOrderMapper;
import com.heavytiger.mall.pojo.Order;
import com.heavytiger.mall.pojo.OrderCart;
import com.heavytiger.mall.pojo.SeckillOrder;
import com.heavytiger.mall.pojo.SeckillProduct;
import com.heavytiger.mall.service.OrderCartService;
import com.heavytiger.mall.service.OrderService;
import com.heavytiger.mall.service.SeckillOrderService;
import com.heavytiger.mall.service.SeckillProductService;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author heavytiger
 * @version 1.0
 * @description 秒杀订单接口业务实现类
 * @date 2022/2/21 20:54
 */
@Service("seckillOrderService")
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private OrderCartService orderCartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加秒杀订单
     *
     * @param userId 用户的id
     * @param seckillId 需要添加的秒杀订单
     * @return 返回影响的行数
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Integer addSeckillOrder(Integer seckillId, Integer userId) {
        // 1. 首先需要将商品添加到购物车中
        // 获取Redis中存储的商品的秒杀价格
        SeckillProduct seckillProduct =
                (SeckillProduct) redisTemplate.opsForValue().get("seckillProduct:" + seckillId);
        if(seckillProduct == null) {
            throw new RuntimeException("Redis中不存在相关商品!");
        }
        OrderCart tempOrderCart = new OrderCart(null, userId,
                seckillProduct.getProductId(), 1, seckillProduct.getSeckillPrice(), 1,
                null, null, null);
        orderCartService.addOrderCart(tempOrderCart);
        // 2. 将购物车中的商品进行结算
        Order tempOrder =  new Order(null, null, userId, null,
                null, null, null, null);
        orderService.addOrder(tempOrder);
        // 3. 将数据回写到秒杀订单表
        SeckillOrder seckillOrder = new SeckillOrder(null, userId, tempOrder.getOrderId(),
                seckillProduct.getProductId());
        seckillOrder.setOrderId(tempOrder.getOrderId());
        return seckillOrderMapper.addSeckillOrder(seckillOrder);
    }

    /**
     * 根据订单号查询订单
     *
     * @param id 需要查询的订单号
     * @return 返回根据订单号查询到的订单
     */
    @Override
    public SeckillOrder querySeckillOrderById(Integer id) {
        return seckillOrderMapper.querySeckillOrderById(id);
    }
}
