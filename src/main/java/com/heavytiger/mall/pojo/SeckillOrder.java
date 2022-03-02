package com.heavytiger.mall.pojo;

/**
 * @author heavytiger
 * @version 1.0
 * @description 秒杀订单实体类
 * @date 2022/2/11 22:26
 */
public class SeckillOrder {
    private Integer id;
    private Integer customerId;
    private Integer orderId;
    private Integer productId;

    public SeckillOrder() {
    }

    public SeckillOrder(Integer id, Integer customerId, Integer orderId, Integer productId) {
        this.id = id;
        this.customerId = customerId;
        this.orderId = orderId;
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "SeckillOrder{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
