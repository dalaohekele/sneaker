package com.zl.sneakerserver.dto;

import com.zl.sneakerentity.enums.ProductInfoStateEnum;
import com.zl.sneakerentity.model.SeckillProduct;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/10/8 17:30
 * @Description:
 */
public class SeckillDto {
    private Integer state;
    private String stateInfo;

    private SeckillProduct seckillProduct;

    private List<SeckillProduct> seckillProducts;

    public SeckillDto(){}


    public SeckillDto(ProductInfoStateEnum productInfoStateEnum){
        this.state = productInfoStateEnum.getState();
        this.stateInfo = productInfoStateEnum.getStateInfo();
    }


    public SeckillDto(List<SeckillProduct> seckillProducts){
        this.seckillProducts = seckillProducts;
    }

    public SeckillDto(SeckillProduct seckillProduct){
        this.seckillProduct = seckillProduct;
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

    public SeckillProduct getSeckillProduct() {
        return seckillProduct;
    }

    public void setSeckillProduct(SeckillProduct seckillProduct) {
        this.seckillProduct = seckillProduct;
    }

    public List<SeckillProduct> getSeckillProducts() {
        return seckillProducts;
    }

    public void setSeckillProducts(List<SeckillProduct> seckillProducts) {
        this.seckillProducts = seckillProducts;
    }

    @Override
    public String toString() {
        return "SeckillDto{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", seckillProduct=" + seckillProduct +
                ", seckillProducts=" + seckillProducts +
                '}';
    }
}
