package com.zl.sneakerentity.enums;

/**
 * @Auther: le
 * @Date: 2018/7/27 15:55
 * @Description:
 */
public enum  PayStatusEnum {
    WAIT(0, "等待支付"), SUCCESS(1, "支付成功");

    private Integer state;

    private String stateInfo;

    PayStatusEnum(Integer state,String stateInfo){
        this.state =  state;
        this.stateInfo = stateInfo;
    }

    public static PayStatusEnum stateOf(int index){
        for (PayStatusEnum state:values()){
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
