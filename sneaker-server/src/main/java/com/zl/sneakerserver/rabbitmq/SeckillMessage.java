package com.zl.sneakerserver.rabbitmq;

/**
 * @Auther: le
 * @Date: 2018/10/9 20:35
 * @Description:
 */

import com.zl.sneakerentity.model.SeckillProduct;
import com.zl.sneakerentity.model.User;

/**
 * 秒杀消息体
 */
public class SeckillMessage {

    private SeckillProduct seckillProduct;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SeckillProduct getSeckillProduct() {
        return seckillProduct;
    }

    public void setSeckillProduct(SeckillProduct seckillProduct) {
        this.seckillProduct = seckillProduct;
    }
}
