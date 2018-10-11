package com.zl.sneakerentity.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: le
 * @Date: 2018/10/8 16:11
 * @Description:
 */
public class SeckillProduct {
    /*秒杀id*/
    private Long id;
    /*秒杀商品id*/
    private String productId;
    /** 秒杀单价. */
    private BigDecimal productPrice;
    /*数量*/
    private Integer productStock;
    /*开始时间*/
    private Date startDate;
    /*结束时间*/
    private Date endDate;
    /*并发版本控制*/
    private int version;


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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }



    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SeckillProduct{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", productPrice=" + productPrice +
                ", productStock=" + productStock +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", version=" + version +
                '}';
    }
}
