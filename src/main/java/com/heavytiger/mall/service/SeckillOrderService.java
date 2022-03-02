package com.heavytiger.mall.service;

import com.heavytiger.mall.pojo.SeckillOrder;

/**
 * @author heavytiger
 * @version 1.0
 * @description 秒杀商品订单的Service层接口
 * @date 2022/2/13 23:18
 */
public interface SeckillOrderService {

    /**
     * 添加秒杀订单
     *
     * @param userId 用户的id
     * @param seckillId 需要添加的秒杀订单
     * @return 返回影响的行数
     */
    public Integer addSeckillOrder(Integer seckillId, Integer userId);

    /**
     * 根据订单号查询订单
     * @param id 需要查询的订单号
     * @return 返回根据订单号查询到的订单
     */
    public SeckillOrder querySeckillOrderById(Integer id);
}
