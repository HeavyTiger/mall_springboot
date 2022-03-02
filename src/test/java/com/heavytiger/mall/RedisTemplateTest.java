package com.heavytiger.mall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author heavytiger
 * @version 1.0
 * @description 测试redis的功能是否正常
 * @date 2022/2/11 15:17
 */
@SpringBootTest
@Component
public class RedisTemplateTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void redisTemplateTest() {
        Map<String, Object> temp, map = new HashMap<>();
        map.put("hash1", "value1");
        map.put("hash2", "123");
        map.put("hash3", Arrays.asList("1", 2, 3, "abc"));
        redisTemplate.opsForValue().set("key_hash", map);
        temp = (Map<String, Object>) redisTemplate.opsForValue().get("key_hash");
        System.out.println("if value set == get" + map.equals(temp));
        for (Map.Entry<String, Object> entry : temp.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
