package com.zl.sneakerentity.redis;

/**
 * @Auther: le
 * @Date: 2018/8/9 16:36
 * @Description:
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix){
        super(prefix);
    }

    public static OrderKey getSeckillOrderByUidGid = new OrderKey("seckill");
}
