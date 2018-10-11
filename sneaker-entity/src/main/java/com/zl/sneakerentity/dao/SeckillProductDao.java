package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.SeckillProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/10/8 16:44
 * @Description:
 */
public interface SeckillProductDao {
    //秒杀商品列表
    List<SeckillProduct> listSeckillProduct();

    //秒杀商品详情
    SeckillProduct selectByProductId(@Param("productId")String productId);

    //stock_count > 0 和 版本号实现乐观锁 防止超卖
    int reduceStockByVersion(@Param("seckillProduct")SeckillProduct seckillProduct);
}
