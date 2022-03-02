package com.heavytiger.mall.service.impl;

import com.heavytiger.mall.pojo.OrderCart;
import com.heavytiger.mall.service.OrderCartService;
import com.heavytiger.mall.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

/**
 * @author AnthonyCJ
 * @version 1.0
 * @description 测试OrderCartService中的方法是否工作正常
 * @date 2021/12/25 17:37
 */
@SpringBootTest
@Component
public class OrderCartServiceImplTest {

    @Autowired
    private OrderCartService orderCartService;
    @Autowired
    private ProductService productService;

    @Test
    public void testAddOrderCart() {
/*        // 从浏览products页面添加购物车
        // 获取productId
        Integer productId = 1;
        ProductDetail productDetail = productService.queryProductDetailById(productId);
        // 封装数据
        OrderCart orderCart = new OrderCart();

        orderCart.setCustomerId(1);
        orderCart.setProductId(productDetail.getPid());
        orderCart.setProductAmount(1);
        orderCart.setProductPrice(productDetail.getPrice());
        orderCart.setStatus(1);
        orderCart.setOrderId(null);
        orderCartService.addOrderCart(orderCart);*/

        // OrderCart orderCart1 = new OrderCart(null, 1, 1, 1, 10000.00, 1, 1, null);
        //OrderCart orderCart2 = new OrderCart(null, 1, 1, 1, 10000.00, 1, 1, null, "商品名称1");
        //orderCartService.addOrderCart(orderCart1);
        System.out.println(orderCartService.addOrderCart(4, 1));
        //orderCartService.addOrderCart(orderCart2);
    }

    @Test
    public void testDeleteOrderCartById() {
        System.out.println(orderCartService.deleteOrderCartById(2));
        System.out.println(orderCartService.deleteOrderCartById(20));
    }

    @Test
    public void testUpdateOrderCart() {
        //OrderCart orderCart = new OrderCart();
        //orderCart.setStatus(0);
        //orderCart.setOrderCartId(3);
        System.out.println(orderCartService.updateOrderCart(6,10));
    }

    @Test
    public void testQueryOrderCarts() {
        for (OrderCart orderCart : orderCartService.queryOrderCarts()) {
            System.out.println(orderCart);
        }

    }

    @Test
    public void testQueryOrderCartsByCustomerId() {
        for (OrderCart orderCart : orderCartService.queryOrderCartsByCustomerId(1)) {
            System.out.println(orderCart);
        }
    }

    @Test
    public void testQueryNewOrderCartsByCustomerId() {
        for (OrderCart orderCart : orderCartService.queryNewOrderCartsByCustomerId(1)) {
            System.out.println(orderCart);
        }
    }

    @Test
    public void testQueryOrderCartsByOrderId() {
        for (OrderCart orderCart : orderCartService.queryOrderCartsByOrderId(1)) {
            System.out.println(orderCart);
        }

    }
}
