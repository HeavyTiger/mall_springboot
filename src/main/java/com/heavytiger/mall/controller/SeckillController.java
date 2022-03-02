package com.heavytiger.mall.controller;

import com.heavytiger.mall.dto.SeckillExposer;
import com.heavytiger.mall.mq.MQSender;
import com.heavytiger.mall.pojo.EnumResult;
import com.heavytiger.mall.pojo.ResultBean;
import com.heavytiger.mall.pojo.SeckillProduct;
import com.heavytiger.mall.service.SeckillProductService;
import com.heavytiger.mall.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author heavytiger
 * @version 1.0
 * @description 秒杀业务的相关接口
 * @date 2022/2/16 18:00
 */
@RestController
public class SeckillController {

    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisScript<Long> seckillScript;

    @Autowired
    private HashMap<String, Boolean> seckillOverMap;

    @Autowired
    private MQSender mqSender;

    @GetMapping("/getSeckillDetail/{seckillId}")
    public ResultBean<Object> getSeckillDetail(@PathVariable Integer seckillId) {
        // 获取当前的产品秒杀状态
        // 修改，因为可能被很多人请求，不应该请求数据库，防止业务崩溃，故将该数据缓存到redis中
        SeckillProduct seckillProduct =
                (SeckillProduct) redisTemplate.opsForValue().get("seckillProduct:" + seckillId);
        SeckillExposer seckillExposer = new SeckillExposer();
        if (seckillProduct == null) {
            // 说明该商品无法被秒杀
            seckillExposer.setStatus(2);
        } else {
            // 获取当前服务器时间
            seckillExposer.setNow(System.currentTimeMillis());
            // 获取秒杀商品开始时间
            seckillExposer.setStart(seckillProduct.getStartDate().getTime());
            // 获取秒杀商品借书时间
            seckillExposer.setEnd(seckillProduct.getEndDate().getTime());
            if (seckillExposer.getNow() < seckillExposer.getStart()) {
                // 表示秒杀未开始
                seckillExposer.setStatus(0);
            } else {
                // 表示秒杀开始
                seckillExposer.setStatus(1);
            }
        }
        return new ResultBean<>(EnumResult.SUCCESS, seckillExposer);
    }

    @GetMapping("/getSeckillPath/{seckillId}")
    public ResultBean<Object> getSeckillPath(HttpServletRequest request, @PathVariable Integer seckillId) {
        Boolean seckillOver = seckillOverMap.get("seckillProduct:" + seckillId);
        if (seckillOver == null) {
            // 迭代为先进行内存访问，用于提升QPS，避免不必要的Redis访问
            // 如果没有获取到值，直接返回不存在商品
            return new ResultBean<>(EnumResult.SECKILL_NO_PRODUCT);
        } else if (seckillOver) {
            // 若售罄标记为真，表示已经售罄
            return new ResultBean<>(EnumResult.SECKILL_STOCK_EMPTY);
        }
        String token = request.getHeader("Authorization");
        Integer userId = JwtUtil.getUserId(token);
        // 判断是否存在该商品的秒杀信息
        SeckillProduct seckillProduct =
                (SeckillProduct) redisTemplate.opsForValue().get("seckillProduct:" + seckillId);
        if (seckillProduct == null) {
            // 说明该商品不存在
            return new ResultBean<>(EnumResult.SECKILL_NO_PRODUCT);
        }
        if (System.currentTimeMillis() < seckillProduct.getStartDate().getTime()) {
            // 说明还没有到抢购时间，不允许进行抢购，不暴露秒杀地址
            return new ResultBean<>(EnumResult.SECKILL_NO_START);
        }
        String path = seckillProductService.createPath(userId, seckillId);
        return new ResultBean<>(EnumResult.SUCCESS, path);
    }

    @GetMapping("/getSeckillResult/{seckillId}")
    public ResultBean<Object> getSeckillResult(HttpServletRequest request, @PathVariable Integer seckillId) {
        String token = request.getHeader("Authorization");
        Integer userId = JwtUtil.getUserId(token);
        // 从Redis中查找当前的顾客是否存在记录
        Boolean hasKey = redisTemplate.opsForSet().isMember("seckillOrder:" + seckillId, userId);
        if (hasKey != null && hasKey) {
            // 表示存在当前用户的秒杀记录
            return new ResultBean<>(EnumResult.SUCCESS);
        }
        return new ResultBean<>(EnumResult.SECKILL_NO_ORDER);
    }

    /**
     * 秒杀业务.
     *
     * @param request   获取userid
     * @param pathMD5   秒杀地址，防止接口提前暴露
     * @param seckillId 秒杀id
     * @return 返回秒杀状态
     */
    @PostMapping("/doSeckill/{seckillId}")
    public ResultBean<Object> doSeckill(HttpServletRequest request,
                                        @RequestBody(required = false) String pathMD5,
                                        @PathVariable Integer seckillId) {
        // 用于判断库存是否已经卖完
        Boolean seckillOver = seckillOverMap.get("seckillProduct:" + seckillId);
        if (seckillOver == null) {
            // 迭代为先进行内存访问，用于提升QPS，避免不必要的Redis访问
            // 如果没有获取到值，直接返回不存在商品
            // 迭代后，获取秒杀地址的QPS提升到1294，执行秒杀的QPS提升到1202
            return new ResultBean<>(EnumResult.SECKILL_NO_PRODUCT);
        } else if (seckillOver) {
            // 若售罄标记为真，表示已经售罄
            return new ResultBean<>(EnumResult.SECKILL_STOCK_EMPTY);
        }
        // 获取userId
        String token = request.getHeader("Authorization");
        Integer userId = JwtUtil.getUserId(token);
        // 判断抢购地址是否一致
        String pathInRedis = (String) redisTemplate.opsForValue().get("seckillPath:" + seckillId + ":" + userId);
        if (!pathMD5.equals(pathInRedis)) {
            // 说明抢购的地址不一致，不允许继续抢购
            return new ResultBean<>(EnumResult.SECKILL_PATH_ERROR);
        }
        // 判断是否重复抢购
        Boolean hasKey = redisTemplate.opsForSet().isMember("seckillOrder:" + seckillId, userId);
        if (hasKey != null && hasKey) {
            // 说明该用户存在重复秒杀
            return new ResultBean<>(EnumResult.SECKILL_ORDER_REPEAT);
        }
        // 使用lua脚本进行减库存以及存入订单的操作，必须使用Long，Integer会产生报错
        Long execute = redisTemplate.execute(seckillScript, Collections.emptyList(), userId, seckillId);
        if (execute == null) {
            // 避免提示空指针
            return new ResultBean<>(EnumResult.INTERNAL_SERVER_ERROR);
        } else if (execute == 0) {
            // 说明此时库存为空
            // 此时测试秒杀服务的QPS为740，获取MD5秒杀路径的QPS为686
            // 之后可以再次改进，在内存中设置库存清零标记，当标记后，访问秒杀及路径接口直接返回库存为空，可以提高QPS
            seckillOverMap.replace("seckillProduct:" + seckillId, Boolean.TRUE);
            return new ResultBean<>(EnumResult.SECKILL_STOCK_EMPTY);
        } else if (execute == 1) {
            // 说明此时秒杀成功，使用消息队列异步下订单
            mqSender.sendSeckillMessage(userId, seckillId);
            // System.out.println(userId + "：创建订单成功！");
            return new ResultBean<>(EnumResult.SUCCESS);
        } else {
            // 说明已经秒杀成功，提示禁止重复秒杀
            // System.out.println("禁止重复秒杀！");
            return new ResultBean<>(EnumResult.SECKILL_ORDER_REPEAT);
        }
    }
}