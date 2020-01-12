package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.SeckillOrder;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther: le
 * @Date: 2018/10/9 17:44
 * @Description:
 */
public interface SeckillOrderDao {

    int insertSeckillOrder(SeckillOrder seckillOrder);

    SeckillOrder selectByOrderIdUserId(@Param("productId") String productId, @Param("userId") String userId);

}
