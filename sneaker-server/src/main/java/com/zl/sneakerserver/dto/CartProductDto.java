package com.zl.sneakerserver.dto;

import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.ProductInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/8/28 16:00
 * @Description:
 */
public class CartProductDto implements Serializable {
    private Integer state;
    private String stateInfo;

    /* 商品ID*/
    private String producId;

    private BigDecimal salePrice;
    /* 商品数量*/
    private int productNum;

    private ProductInfo productInfo;

    private List<ProductInfo> productInfoList;

    public CartProductDto(){}

    public CartProductDto(ResultEnum resultEnum){
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getMessage();
    }
    public CartProductDto(ResultEnum resultEnum,ProductInfo productInfo){
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getMessage();
        this.productInfo = productInfo;
    }
    public CartProductDto(ResultEnum resultEnum,List<ProductInfo> productInfoList){
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getMessage();
        this.productInfoList = productInfoList;
    }


    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public String getProducId() {
        return producId;
    }

    public void setProducId(String producId) {
        this.producId = producId;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
