package com.zl.sneakerserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zl.sneakerentity.model.ProductCategory;
import com.zl.sneakerentity.enums.ProductCategoryStateEnum;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 10:40
 * @Description:
 */
public class ProductCategoryDto {

    //状态码
    @JsonProperty("state")
    private Integer state;

    //状态信息
    @JsonProperty("state_info")
    private String stateInfo;

    //分类
    @JsonProperty("category_list")
    private List<ProductCategory> productCategoryList;


    public ProductCategoryDto(){}

    //失败的构造器
    public ProductCategoryDto(ProductCategoryStateEnum productCategoryStateEnum) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
    }

    //查询成功的构造器
    public ProductCategoryDto(ProductCategoryStateEnum productCategoryStateEnum, List<ProductCategory> productCategoryList) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
