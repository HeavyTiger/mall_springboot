package com.heavytiger.mall.mapper;

import com.heavytiger.mall.pojo.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author heavytiger
 * @version 1.0
 * @description seckill_order秒杀订单表的相关接口
 * @date 2022/2/11 22:26
 */
@Mapper
public interface SeckillOrderMapper {
    /**
     * @param seckillOrder 需要添加的秒杀订单
     * @return 影响的行数
     */
    public Integer addSeckillOrder(SeckillOrder seckillOrder);

    /**
     * @param id 需要删除的订单id
     * @return 影响的行数
     */
    public Integer deleteSeckillOrder(Integer id);

    /**
     * @param  seckillOrder 需要更新的秒杀订单
     * @return 影响的行数
     */
    public Integer updateSeckillOrder(SeckillOrder seckillOrder);

    /**
     * @return 查询到的所有订单
     */
    public List<SeckillOrder> querySeckillOrders();

    /**
     * @param id 查询的id
     * @return 根据id查询到的秒杀产品
     */
    public SeckillOrder querySeckillOrderById(Integer id);

    /**
     * 根据用户的id获取其所有秒杀成功订单
     * @param customerId 需要查询的用户的id
     * @return 当前用户的所有订单记录
     */
    public List<SeckillOrder> querySeckillOrdersByCustomerId(Integer customerId);
}
