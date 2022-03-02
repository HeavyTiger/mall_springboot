package com.heavytiger.mall.service.impl;

import com.heavytiger.mall.dto.SeckillExposer;
import com.heavytiger.mall.mapper.SeckillProductMapper;
import com.heavytiger.mall.pojo.Customer;
import com.heavytiger.mall.pojo.SeckillProduct;
import com.heavytiger.mall.service.SeckillProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author heavytiger
 * @version 1.0
 * @description 秒杀商品服务实现类
 * @date 2022/2/14 20:00
 */
@Service
public class SeckillProductServiceImpl implements SeckillProductService {

    @Autowired
    private SeckillProductMapper seckillProductMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过商品的ID获取秒杀的物品信息
     *
     * @param id 秒杀的商品信息
     * @return 查询到的可以秒杀的商品
     */
    @Override
    public SeckillProduct querySeckillProductById(Integer id) {
        return seckillProductMapper.querySeckillProductById(id);
    }

    @Override
    public SeckillProduct querySeckillProductByProductId(Integer pid) {
        return seckillProductMapper.querySeckillProductByProductId(pid);
    }

    @Override
    public List<SeckillProduct> querySeckillProducts() {
        return seckillProductMapper.querySeckillProducts();
    }

    @Override
    public SeckillExposer getDetail(Integer id) {
        return null;
    }

    @Override
    public Integer deleteSeckillProduct(Integer id) {
        return seckillProductMapper.deleteSeckillProduct(id);
    }

    @Override
    public String createPath(Integer userId, Integer seckillId) {
        String pathMD5 = UUID.randomUUID().toString().replace("-", "");
        // 设置30s内允许访问路径进行秒杀
        redisTemplate.opsForValue().set("seckillPath:" + seckillId + ":" + userId, pathMD5,
                30, TimeUnit.SECONDS);
        return pathMD5;
    }

    @Override
    public Integer updateSeckillProduct(SeckillProduct seckillProduct) {
        return seckillProductMapper.updateSeckillProduct(seckillProduct);
    }

    /**
     * 进行秒杀某个物品.
     * 秒杀的实现流程：
     * 1. 首先在商品第一次秒杀时，可能缓存中没有数据，缓存击穿，若此时放大量请求进入，会导致
     * 数据库可能直接被干掉，因此加分布式锁，以保证不同时存在大量请求打到数据库上
     * 2. 将获取到的秒杀物品信息放置到Redis中，之后请求使用Redis加Lua脚本做减库存。
     *
     * @param id      秒杀的商品ID
     * @param userId  顾客的ID
     * @param pathMD5 校验秒杀接口地址，防止接口暴露
     * @return 是否秒杀成功
     */
    @Override
    public Integer doSeckill(Integer id, Integer userId, String pathMD5) {
        return null;
    }
}
