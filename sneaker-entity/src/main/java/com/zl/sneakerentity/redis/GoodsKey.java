package com.zl.sneakerentity.redis;

/**
 * @Auther: le
 * @Date: 2018/8/9 16:28
 * @Description:
 */
public class GoodsKey extends BasePrefix{
    private GoodsKey(int expireSeconds,String prefix){
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(600,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getGoodsStock = new GoodsKey(60,"gs");
}
