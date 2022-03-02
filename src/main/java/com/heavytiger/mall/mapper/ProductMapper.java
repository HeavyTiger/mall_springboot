package com.heavytiger.mall.mapper;

import com.heavytiger.mall.pojo.Product;
import com.heavytiger.mall.pojo.ProductDetail;
import com.heavytiger.mall.pojo.ProductSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author heavytiger
 * @version 1.0
 * @description Product产品类的相关接口
 * @date 2021/12/24 18:36
 */
@Mapper
public interface ProductMapper {
    /**
     * 增加products表中的产品，将不使用product_detail视图，不做接口，仅在Junit中测试插入功能
     * @param productDetail 需要插入的产品，请注意，此处只能使用products表中的相关字段插入，其他表中字段无效
     * @return 影响的行数
     */
    public Integer addProduct(ProductDetail productDetail);

    /**
     * 删除products表中的产品，将不使用product_detail视图，不做接口，仅在Junit中共测试删除功能
     * @param pid 需要删除的产品的id
     * @return 影响的行数
     */
    public Integer deleteProduct(Integer pid);

    /**
     * 更新Products表中的产品，将不使用product_detail视图，不做接口，仅在Junit中共测试更新功能
     * @param productDetail 需要修改的产品信息
     * @return 影响的行数
     */
    public Integer updateProduct(Product productDetail);

    /**
     * 乐观锁，用于比较库存
     * @param pid 需要修改的商品id
     * @param curStock 当前查询到的库存
     * @param updateStock 减去购买数量后的库存
     * @return 如果成功返回
     */
    public Integer updateProductStock(@Param("pid") Integer pid, @Param("curStock") Integer curStock, @Param("updateStock") Integer updateStock);

    /**
     * 获取查询到的产品信息列表
     * @return 返回查询到的产品信息列表
     */
    public List<ProductDetail> queryProductDetails();

    /**
     * 通过pid获取产品的所有详情信息
     * @param pid 需要查询的产品id
     * @return 返回查询到的产品信息
     */
    public ProductDetail queryProductDetailById(Integer pid);

    public Product queryProductById(Integer pid);

    /**
     * 通过模糊搜索，获得需要的产品信息列表
     * @param productSearch 搜索类，使用其中的字段进行搜索
     * @return 返回获得的产品信息列表
     */
    public List<ProductDetail> queryProductDetailBySearch(ProductSearch productSearch);

    /**
     * 使用for update加入悲观锁，其他获取锁的机制提交前，无法再次获得锁
     * @param pid 需要查询的产品id
     * @return 返回查询到的产品信息
     */
    public Product queryProductDetailForUpdate(Integer pid);
}
