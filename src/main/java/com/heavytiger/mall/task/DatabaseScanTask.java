package com.heavytiger.mall.task;

import com.heavytiger.mall.pojo.SeckillProduct;
import com.heavytiger.mall.service.SeckillProductService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author heavytiger
 * @version 1.0
 * @description 扫描数据库，将秒杀信息储存到Redis中
 * @date 2022/2/16 11:59
 */

@Component
public class DatabaseScanTask implements InitializingBean {

    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HashMap<String, Boolean> seckillOverMap;

    @Scheduled(cron = "30 */1 * * * ?")
    @Transactional
    public void databaseScanTask() {
        // 商品预热，每隔5分钟从将未添加的秒杀商品数据添加到Redis中，例如12:00:30, 12:05:30, 12:10:30
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<SeckillProduct> products = seckillProductService.querySeckillProducts();
        for (SeckillProduct product : products) {
            Integer curStock = (Integer) valueOperations.get("seckillStock:" + product.getId());
            if (curStock == null) {
                // 表示redis中的该秒杀商品为空，应该将秒杀商品添加进去
                long curTimestamp = System.currentTimeMillis();         // 当前时间
                long endTimestamp = product.getEndDate().getTime();     // 结束时间

                // 如果当前时间大于秒杀时间，说明秒杀已经结束了，将秒杀商品表中的商品删除，商品售罄标记删除，直接返回
                if (endTimestamp < curTimestamp) {
                    seckillProductService.deleteSeckillProduct(product.getId());
                    seckillOverMap.remove("seckillProduct:" + product.getId());
                    return;
                }
                // 设置秒杀商品的库存
                valueOperations.setIfAbsent("seckillStock:" + product.getId(), product.getStock(),
                        endTimestamp - curTimestamp, TimeUnit.MILLISECONDS);
                // 将秒杀商品信息缓存
                valueOperations.setIfAbsent("seckillProduct:" + product.getId(), product,
                        endTimestamp - curTimestamp, TimeUnit.MILLISECONDS);
            }
            // 设置秒杀商品的售罄状态为False，表示未售罄
            seckillOverMap.putIfAbsent("seckillProduct:" + product.getId(), Boolean.FALSE);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 在项目启动初始化所有的Bean时进行初始化系统，加载所有的秒杀商品
        databaseScanTask();
    }
}
