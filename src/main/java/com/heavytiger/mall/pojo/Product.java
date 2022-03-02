package com.heavytiger.mall.pojo;

/**
 * @author heavytiger
 * @version 1.0
 * @description TODO
 * @date 2022/2/22 22:23
 */
public class Product {
    private Integer pid;

    private String pName;

    private Double price;

    private Integer stock;

    private Integer tid;

    private String pDescription;

    private Integer pStatus;

    private Integer sid;

    public Product() {
    }

    public Product(Integer pid, String pName, Double price, Integer stock, Integer tid, String pDescription, Integer pStatus, Integer sid) {
        this.pid = pid;
        this.pName = pName;
        this.price = price;
        this.stock = stock;
        this.tid = tid;
        this.pDescription = pDescription;
        this.pStatus = pStatus;
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pName='" + pName + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", tid=" + tid +
                ", pDescription='" + pDescription + '\'' +
                ", pStatus=" + pStatus +
                ", sid=" + sid +
                '}';
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public Integer getpStatus() {
        return pStatus;
    }

    public void setpStatus(Integer pStatus) {
        this.pStatus = pStatus;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }
}
