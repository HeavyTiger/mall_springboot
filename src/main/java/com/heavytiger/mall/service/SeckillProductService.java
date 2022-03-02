package com.heavytiger.mall.service;

import com.heavytiger.mall.dto.SeckillExposer;
import com.heavytiger.mall.pojo.SeckillProduct;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author heavytiger
 * @version 1.0
 * @description 秒杀商品的Service层接口
 * @date 2022/2/13 23:17
 */
@Service
public interface SeckillProductService {
    /**
     * 通过商品的ID获取秒杀的物品信息
     * @param id 秒杀的商品信息
     * @return 查询到的可以秒杀的商品
     */
    public SeckillProduct querySeckillProductById(Integer id);

    /**
     * 查询秒杀商品表中的商品编号，在秒杀结束后删除秒杀商品表中的记录，因此商品编号有唯一性
     * @param pid 需要查询的秒杀商品
     * @return 获得当前商品的秒杀信息
     */
    public SeckillProduct querySeckillProductByProductId(Integer pid);

    /**
     * 进行秒杀某个物品.
     * 秒杀的实现流程：
     * 1. 首先在商品第一次秒杀时，可能缓存中没有数据，缓存击穿，若此时放大量请求进入，会导致
     *    数据库可能直接被干掉，因此加分布式锁，以保证不同时存在大量请求打到数据库上
     * 2. 将获取到的秒杀物品信息放置到Redis中，之后请求使用Redis加Lua脚本做减库存。
     *
     * @param id 秒杀的商品ID
     * @param userId 顾客的ID
     * @param pathMD5 校验秒杀接口地址，防止接口暴露
     * @return 是否秒杀成功
     */
    public Integer doSeckill(Integer id, Integer userId, String pathMD5);


    /**
     * 查询全部秒杀商品
     * @return 返回查询到的所有秒杀商品
     */
    public List<SeckillProduct> querySeckillProducts();

    /**
     * 获取商品详情信息
     * @param id
     * @return
     */
    public SeckillExposer getDetail(Integer id);

    /**
     * 删除秒杀商品
     * @param id
     * @return
     */
    public Integer deleteSeckillProduct(Integer id);

    /**
     * 创建唯一的秒杀地址，防止地址提前暴露
     * @param userId
     * @param seckillId
     * @return
     */
    public String createPath(Integer userId, Integer seckillId);

    /**
     * 修改秒杀商品表
     * @param seckillProduct
     * @return
     */
    public Integer updateSeckillProduct(SeckillProduct seckillProduct);
}
