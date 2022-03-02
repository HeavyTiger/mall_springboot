package com.heavytiger.mall.service.impl;

import com.heavytiger.mall.pojo.Order;
import com.heavytiger.mall.pojo.OrderCart;
import com.heavytiger.mall.service.OrderCartService;
import com.heavytiger.mall.service.OrderService;
import com.heavytiger.mall.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

/**
 * @author heavytiger
 * @version 1.0
 * @description 测试订单业务是否正常
 * @date 2021/12/26 18:58
 */
@SpringBootTest
@Component
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderCartService orderCartService;

    @Test
    public void addOrder() {
        Order order1 = new Order(null, null, 3, "邓晟淼", "武汉理工",
                "15327618268", null, null);
        Order order2 = new Order(null, null, 2, "邢淳杰", "武汉理工",
                "11234567890", null, null);
        try {
            orderService.addOrder(order1);
        } catch (RuntimeException r){
            r.printStackTrace();
        }
    }

    @Test
    public void queryOrders() {
        for (Order order : orderService.queryOrders()) {
            System.out.println(order);
            for (OrderCart orderCart : orderCartService.queryOrderCartsByOrderId(order.getOrderId())) {
                System.out.println(orderCart);
            }
        }
    }

    @Test
    public void queryOrderByOrderId() {
        System.out.println(orderService.queryOrderByOrderId(8));
    }

    @Test
    public void queryOrdersByCustomerId() {
        System.out.println(JsonUtil.objToJson(orderService.queryOrdersByCustomerId(3, 1, 10)));
    }
}