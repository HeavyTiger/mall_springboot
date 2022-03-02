package com.heavytiger.mall.mapper;

import com.heavytiger.mall.pojo.SeckillProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author heavytiger
 * @version 1.0
 * @description seckill_product秒杀商品表的相关接口
 * @date 2022/2/11 22:25
 */
@Mapper
public interface SeckillProductMapper {
    /**
     * @param seckillProduct 需要添加的秒杀商品
     * @return 影响的行数
     */
    public Integer addSeckillProduct(SeckillProduct seckillProduct);

    /**
     * @param id 需要删除的id
     * @return 影响的行数
     */
    public Integer deleteSeckillProduct(Integer id);

    /**
     * @param seckillProduct 需要更新的秒杀商品
     * @return 影响的行数
     */
    public Integer updateSeckillProduct(SeckillProduct seckillProduct);

    /**
     * @return 查询到的所有产品
     */
    public List<SeckillProduct> querySeckillProducts();

    /**
     * @param id 查询的id
     * @return 根据id查询到的秒杀产品
     */
    public SeckillProduct querySeckillProductById(Integer id);

    public SeckillProduct querySeckillProductByProductId(Integer pid);
}
