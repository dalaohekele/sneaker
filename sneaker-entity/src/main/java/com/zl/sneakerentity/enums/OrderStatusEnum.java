package com.zl.sneakerentity.enums;

/**
 * @Auther: le
 * @Date: 2018/7/27 15:55
 * @Description:
 */
public enum  OrderStatusEnum {
    NEW(0, "新订单"), FINISHED(1, "完结"), CANCEL(2, "取消"), FIALCREATE(3,"订单创建失败"),
    FAIL(-1001,"获取数据出错");

    private Integer state;

    private String stateInfo;

    OrderStatusEnum(Integer state,String stateInfo){
        this.state =  state;
        this.stateInfo = stateInfo;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static OrderStatusEnum stateOf(int index){
        for (OrderStatusEnum state:values()){
            if (state.getState()==index){
                return state;
            }
        }
        return null;
    }
}
