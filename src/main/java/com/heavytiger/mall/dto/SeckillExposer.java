package com.heavytiger.mall.dto;

/**
 * @author heavytiger
 * @version 1.0
 * @description 秒杀商品的详情页面获取服务器的时间，秒杀的相关状态及秒杀的请求地址
 * @date 2022/2/16 17:11
 */
public class SeckillExposer {
    // 当前是否允许秒杀 true允许，false不允许
    private Integer status;
    // 开始秒杀时间
    private Long start;
    // 秒杀结束时间
    private Long end;
    // 当前服务器时间
    private Long now;

    public SeckillExposer() {
    }

    public SeckillExposer(Integer status, Long start, Long end, Long now) {
        this.status = status;
        this.start = start;
        this.end = end;
        this.now = now;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
        this.now = now;
    }
}
