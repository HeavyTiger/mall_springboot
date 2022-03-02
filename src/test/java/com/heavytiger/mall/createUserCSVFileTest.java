package com.heavytiger.mall;

import com.heavytiger.mall.pojo.Customer;
import com.heavytiger.mall.service.CustomerService;
import com.heavytiger.mall.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author heavytiger
 * @version 1.0
 * @description 创建user_config.txt文件，保存用户的登录名以及密码
 * @date 2022/2/20 19:46
 */
@SpringBootTest
@Component
public class createUserCSVFileTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void createFile() {
        File file = new File("C:\\Users\\DCM\\Desktop\\user_config.txt");
        try(FileWriter fileWriter = new FileWriter(file)) {
            for(int i = 0; i < 10000; i++) {
                fileWriter.write("seckill" + i + ",123456\n");
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录秒杀测试用户并获得用户的token
     * 将测试的10000个用户的token值放入txt文件中
     */
    @Test
    public void createTokens(){
        File file = new File("C:\\Users\\DCM\\Desktop\\user_tokens.txt");
        if(file.exists()) {
            // 如果文件存在则直接删除
            file.delete();
        }
        try(FileWriter fileWriter = new FileWriter(file)) {
            for(int i = 0; i < 10000; i++) {
                Customer customer = customerService.customerLogin("seckill" + i, "123456");
                String token = JwtUtil.sign(customer.getUserId(), customer.getUsername());
                fileWriter.write(token + "\n");
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
