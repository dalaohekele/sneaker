package com.zl.sneakerentity.enums;

/**
 * @Auther: le
 * @Date: 2018/8/17 14:23
 * @Description:
 */
public enum LoginEnum {
    ADMIN("1"),CUSTOMER("2"),TOKEN("3");

    private String type;

    private LoginEnum(String type){
        this.type = type;
    }

    @Override
    public  String toString(){
        return this.type.toString();
    }
}
