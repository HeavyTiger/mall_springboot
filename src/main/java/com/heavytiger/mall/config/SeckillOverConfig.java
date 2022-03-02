package com.heavytiger.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author heavytiger
 * @version 1.0
 * @description 标记秒杀结束
 * @date 2022/2/21 16:31
 */
@Configuration
public class SeckillOverConfig {
    /**
     * @return 获得全局标记hashmap，标记商品是否卖完
     */
    @Bean
    public HashMap<String, Boolean> seckillOverMap() {
        return new HashMap<>();
    }
}
