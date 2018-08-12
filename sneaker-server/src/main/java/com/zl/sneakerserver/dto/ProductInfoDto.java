package com.zl.sneakerserver.dto;

import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerentity.enums.ProductInfoStateEnum;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 13:49
 * @Description:
 */
public class ProductInfoDto {

    private Integer state;

    private String stateInfo;

    private Integer count;

    private ProductInfo productInfo;

    private List<ProductInfo> productInfoList;

   public ProductInfoDto(){}

   //失败的构造器
   public ProductInfoDto(ProductInfoStateEnum productInfoStateEnum){
       this.state = productInfoStateEnum.getState();
       this.stateInfo = productInfoStateEnum.getStateInfo();
   }

   public ProductInfoDto(ProductInfoStateEnum productInfoStateEnum,ProductInfo productInfo){
       this.state = productInfoStateEnum.getState();
       this.stateInfo = productInfoStateEnum.getStateInfo();
       this.productInfo = productInfo;
   }
   public ProductInfoDto(ProductInfoStateEnum productInfoStateEnum,List<ProductInfo> productInfoList){
       this.state = productInfoStateEnum.getState();
       this.stateInfo = productInfoStateEnum.getStateInfo();
       this.productInfoList = productInfoList;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
}
