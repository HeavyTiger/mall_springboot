package com.heavytiger.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heavytiger.mall.pojo.Supplier;
import com.heavytiger.mall.service.SupplierService;
import com.heavytiger.mall.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author AnthonyCJ
 * @version 1.0
 * @description 测试SupplierService中的方法是否工作正常
 * @date 2021/12/24 22:37
 */
@SpringBootTest
@Component
public class SupplierServiceImplTest {

    @Autowired
    private SupplierService supplierService;

    @Test
    public void testAddSupplier() {
        for (int i = 0; i < 5; i++) {
            Supplier tempSupplier = new Supplier(null, "test" + i, "123456", null, null, null);
            System.out.println(supplierService.addSupplier(tempSupplier));
            System.out.println("插入的supplier主键id为：" + tempSupplier.getSupplierId());
        }
    }

    @Test
    public void testDeleteSupplier() {
        System.out.println(supplierService.deleteSupplier(20));
    }

    @Test
    public void testUpdateSupplier() {
        for (int i = 0; i < 4; i++) {
            Supplier supplier = new Supplier(i + 16, "update" + i, "12345678", "12345678900", "123456@qq.com", "测试数据" + (i + 16));
            System.out.println(supplier);
            System.out.println(supplierService.updateSupplier(supplier));
        }
    }

    @Test
    public void testQuerySuppliers() {
        // 查询全部供应商
        for (Supplier supplier : supplierService.querySuppliers()) {
            System.out.println(supplier);
        }
    }

    @Test
    public void testQuerySupplierById() {
        System.out.println(supplierService.querySupplierById(10));
        System.out.println(supplierService.querySupplierById(100));
    }

    @Test
    public void testQuerySupplierByName() {
        System.out.println(supplierService.querySupplierByName("Philips"));
        System.out.println(supplierService.querySupplierByName("不存在的供应商"));
    }

    @Test
    public void testQuerySupplierByPage() {
        PageHelper.startPage(2, 4); // 请求第2页，每页4条记录
        List<Supplier> suppliers = supplierService.querySuppliers();
        PageInfo<Supplier> pageInfo = new PageInfo<>(suppliers);
        System.out.println(JsonUtil.objToJson(pageInfo));
    }
}
