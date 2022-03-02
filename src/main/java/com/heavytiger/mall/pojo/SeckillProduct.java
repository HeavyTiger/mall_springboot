package com.heavytiger.mall.pojo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author heavytiger
 * @version 1.0
 * @description 秒杀商品实体类
 * @date 2022/2/11 22:26
 */
public class SeckillProduct {
    private Integer id;

    private Integer productId;

    private Double seckillPrice;

    private Integer stock;

    private Date startDate;

    private Date endDate;

    public SeckillProduct() {
    }

    public SeckillProduct(Integer id, Integer productId, Double seckillPrice, Integer stock, Date startDate, Date endDate) {
        this.id = id;
        this.productId = productId;
        this.seckillPrice = seckillPrice;
        this.stock = stock;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SeckillProduct{" +
                "id=" + id +
                ", productId=" + productId +
                ", seckillPrice=" + seckillPrice +
                ", stock=" + stock +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
