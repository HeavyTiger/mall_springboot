package com.heavytiger.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heavytiger.mall.pojo.Customer;
import com.heavytiger.mall.service.CustomerService;
import com.heavytiger.mall.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;


import java.util.List;



/**
 * @author heavytiger
 * @version 1.0
 * @description 测试customerService中的方法是否工作正常
 * @date 2021/12/23 17:48
 */
@SpringBootTest
@Component
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void addCustomer() {
        for (int i = 0; i < 10; i++) {
            Customer temp = new Customer(null, "test" + i, "123456", null, null,
                    null, null);
            System.out.println(customerService.addCustomer(temp));
            System.out.println("插入的customer主键id为：" + temp.getUserId());
        }
    }

    /**
     * 添加10000个秒杀用户，用于测试秒杀效果
     */
    @Test
    public void addSeckillCustomer() {
        for (int i = 0; i < 10000; i++) {
            Customer temp = new Customer(null, "seckill" + i, "123456", null, null,
                    null, "秒杀模块测试用户" + i);
            customerService.addCustomer(temp);
            System.out.println("插入的customer主键id为：" + temp.getUserId());
        }
    }

    @Test
    public void deleteCustomer() {
        System.out.println(customerService.deleteCustomer(14));
    }

    @Test
    public void updateCustomer() {
        for (int i = 0; i < 9; i++) {
            Customer customer = new Customer(i + 5, "update" + i, "12345678", "1",
                    "1234567890", "123456@qq.com", "测试数据" + (i + 5));
            System.out.println(customer);
            System.out.println(customerService.updateCustomer(customer));
        }
    }

    @Test
    public void queryCustomerById() {
        System.out.println(customerService.queryCustomerById(5));
        System.out.println(customerService.queryCustomerById(500));
    }

    @Test
    public void queryCustomers() {
        // 查询全部
        for (Customer customer : customerService.queryCustomers()) {
            System.out.println(customer);
        }
    }

    @Test
    public void queryCustomerByName() {
        System.out.println(customerService.queryCustomerByName("update7"));
        System.out.println(customerService.queryCustomerByName("不存在的！"));
    }

    @Test
    public void queryCustomerByPage() {
        PageHelper.startPage(2, 4);     // 请求第二页，一页4条记录
        List<Customer> customers = customerService.queryCustomers();
        PageInfo<Customer> pageInfo = new PageInfo<>(customers);
        System.out.println(JsonUtil.objToJson(pageInfo));
    }

    @Test
    public void customerLogin() {
        System.out.println("登录1：" + customerService.customerLogin("heavytiger", "123456"));
        System.out.println("登录2：" + customerService.customerLogin("Anthony001", "123456"));
        System.out.println("错误登录1：" + customerService.customerLogin("Anthony00", "123456"));
        System.out.println("错误登录2：" + customerService.customerLogin("Anthony001", "123456812"));
        System.out.println("错误登录3：" + customerService.customerLogin("", ""));
    }
}