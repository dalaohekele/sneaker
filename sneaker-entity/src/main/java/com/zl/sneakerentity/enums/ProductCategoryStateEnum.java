package com.zl.sneakerentity.enums;

/**
 * @Auther: le
 * @Date: 2018/7/27 11:21
 * @Description:
 */
public enum ProductCategoryStateEnum {
    SUCCESS(0,"请求成功"),ERROR(-1001,"操作失败"),INNER_ERROE(-1002,"添加失败");

    private Integer state;

    private String stateInfo;

    ProductCategoryStateEnum(Integer state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductCategoryStateEnum stateOf(int index){
        for (ProductCategoryStateEnum state:values()){
            if (state.getState()==index){
                return state;
            }
        }
        return null;
    }


    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
