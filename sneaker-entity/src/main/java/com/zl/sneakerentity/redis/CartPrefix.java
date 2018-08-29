package com.zl.sneakerentity.redis;

/**
 * @Auther: le
 * @Date: 2018/8/28 15:37
 * @Description:
 */
public class CartPrefix extends BasePrefix{

    public CartPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 设置购物车缓存
     */
    public static CartPrefix getCartList= new CartPrefix(600,"cart");
}
