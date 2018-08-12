package com.zl.sneakerentity.enums;

/**
 * @Auther: le
 * @Date: 2018/7/27 13:58
 * @Description:
 */
public enum ProductInfoStateEnum {
    SUCCESS(0,"success"),ERROR(1,"false"),INNER_ERROE(-1001,"添加失败"),EMPTY(-1002,"商品为空"),
    UP(0, "上架"), DOWN(1, "下架");

    private Integer state;

    private String stateInfo;

    ProductInfoStateEnum(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductInfoStateEnum stateOf(int index){
        for (ProductInfoStateEnum state:values()){
            if (state.getState()==index){
                return state;
            }
        }
        return null;
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
