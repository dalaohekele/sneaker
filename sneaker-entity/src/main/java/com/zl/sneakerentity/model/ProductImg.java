package com.zl.sneakerentity.model;

import java.util.Date;

/**
 * @Auther: le
 * @Date: 2018/8/30 19:32
 * @Description:
 */
public class ProductImg {
    private Integer imgId;
    private String imgAddr;
    private String imgDesc;
    private String productId;
    private Date createTime;


    public Integer getProductImgId() {
        return imgId;
    }

    public void setProductImgId(Integer productImgId) {
        this.imgId = productImgId;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
