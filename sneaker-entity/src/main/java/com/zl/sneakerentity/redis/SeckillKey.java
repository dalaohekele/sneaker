package com.zl.sneakerentity.redis;

/**
 * @Auther: le
 * @Date: 2018/8/9 17:00
 * @Description:
 */
public class SeckillKey extends BasePrefix {
    private SeckillKey(String prefix){
        super(prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey("go");
}
