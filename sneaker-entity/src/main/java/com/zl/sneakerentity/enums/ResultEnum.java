package com.zl.sneakerentity.enums;

/**
 * @Auther: le
 * @Date: 2018/8/1 10:07
 * @Description:
 */

public enum ResultEnum {

    PARAM_ERROR(1, "参数不正确"),

    PRODUCT_NOT_EXI(10, "商品不存在"),

    PRODUC_STOCK_ERROR(11, "缺少库存"),

    ORDER_NOT_EX(12, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_UPDATE_FAIL(15, "订单更新失败"),

    ORDER_DETAIL_EMPTY(16, "订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),

    CART_EMPTY(18, "购物车为空"),

    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),

    WECHAT_MP_ERROR(20, "微信公众账号方面错误"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付异步通知金额校验不通过"),

    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),

    ORDER_CANCEL_FAIL(-1001, "订单取消失败"),

    ORDER_FINISH_SUCCESS(22, "订单完结成功"),

    PRODUCT_STATUS_ERROR(23, "商品状态不正确"),

    LOGIN_FAIL(25, "登录失败, 用户不存在"),

    LOGIN_FAIL_PASS(10025,"密码错误"),

    LOGOUT_SUCCESS(26, "登出成功"),

    REGISTER_FAIL(27,"用户注册失败"),

    REGISTER_SUCESS(0,"用户注册成功"),

    REQUIRMENT_USERNAME(28,"用户名已存在"),

    FINDUSER_FAIL(30,"用户查询失败"),

    USER_EXISTS(10030,"用户已存在"),

    PASSWORD_UPDATE_SUCCESS(0,"密码更新成功"),

    HEAD_UPDATE_SUCCESS(0,"头像更新成功"),

    HEAD_UPDATE_Fail(10020,"头像更新失败"),

    SESSION_ERROR(10021,"Session不存在或者已经失效"),
    ;

    private Integer state;
    private String message;

    ResultEnum(Integer code, String message) {
        this.state = code;
        this.message = message;
    }

    public static ResultEnum stateOf(int index){
        for (ResultEnum state:values()){
            if (state.getState()==index){
                return state;
            }
        }
        return null;
    }
    public Integer getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }
}
