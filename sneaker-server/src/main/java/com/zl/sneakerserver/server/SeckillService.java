package com.zl.sneakerserver.server;

import com.zl.sneakerentity.model.SeckillOrder;
import com.zl.sneakerentity.model.SeckillProduct;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerserver.dto.SeckillDto;

/**
 * @Auther: le
 * @Date: 2018/10/8 17:25
 * @Description:
 */
public interface SeckillService {

    SeckillDto getAllSeckill();

    SeckillDto getSeckillByProdcutID(String productId);

    /**
     * 秒杀减库存
     * @return
     */
    boolean reduceStock(SeckillProduct seckillProduct);

    SeckillOrder seckill(User user, SeckillProduct seckillProduct);

    Object getSeckillResult(String userId,String productId);
}
