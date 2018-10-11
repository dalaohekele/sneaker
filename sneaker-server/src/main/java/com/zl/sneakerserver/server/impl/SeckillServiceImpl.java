package com.zl.sneakerserver.server.impl;

import com.zl.sneakerentity.dao.SeckillProductDao;
import com.zl.sneakerentity.model.SeckillOrder;
import com.zl.sneakerentity.model.SeckillProduct;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerentity.redis.SeckillKey;
import com.zl.sneakerserver.dto.SeckillDto;
import com.zl.sneakerserver.exceptions.ProductInfoException;
import com.zl.sneakerserver.server.OrderServer;
import com.zl.sneakerserver.server.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/10/8 17:37
 * @Description:
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    //乐观锁冲突最大重试次数
    private static final int DEFAULT_MAX_RETRIES = 5;

    @Autowired
    SeckillProductDao seckillProductDao;

    @Autowired
    OrderServer orderServer;

    @Autowired
    RedisService redisService;

    /**
     * 获取所有秒杀商品
     * @return
     */
    @Override
    public SeckillDto getAllSeckill() {
        try {
            List<SeckillProduct> seckillProducts = seckillProductDao.listSeckillProduct();
            return new SeckillDto(seckillProducts);
        } catch (Exception e) {
            throw new ProductInfoException("获取所有getAllSeckill error:" + e.getMessage());
        }

    }

    /**
     * 通过id获取商品
     * @param productId
     * @return
     */
    @Override
    public SeckillDto getSeckillByProdcutID(String productId) {
        try {
            SeckillProduct seckillProduct = seckillProductDao.selectByProductId(productId);
            return new SeckillDto(seckillProduct);
        } catch (Exception e) {
            throw new ProductInfoException("获取getSeckillByProdcutID error:" + e.getMessage());
        }
    }

    /**
     * 秒杀减库存
     * @param seckillProduct
     * @return
     */
    @Override
    public boolean reduceStock(SeckillProduct seckillProduct) {
        int numAttempts=0;
        int ret =0;
        SeckillProduct product = new SeckillProduct();
        product.setProductId(seckillProduct.getProductId());
        product.setVersion(seckillProduct.getVersion());
        do {
            numAttempts++;
            try {
                ret = seckillProductDao.reduceStockByVersion(product);
            }catch (Exception e){
                throw new ProductInfoException("reduceStockByVersion 失败:"+e.getMessage());
            }
            if (ret !=0)
                break;
        }while (numAttempts <DEFAULT_MAX_RETRIES);
        return ret>0;
    }

    /**
     * 秒杀
     * @param user
     * @param seckillProduct
     * @return
     */
    @Override
    @Transactional
    public SeckillOrder seckill(User user, SeckillProduct seckillProduct) {
        //减库存是否成功
        boolean success = reduceStock(seckillProduct);
        if (success){
            return orderServer.seckillOrder(user,seckillProduct);
        }else {
            setProduct(seckillProduct.getProductId());
            return null;
        }
    }

    @Override
    public Object getSeckillResult(String userId, String productId) {
        SeckillOrder seckillOrder = orderServer.getOrderByUserIdGoodsId(userId,productId);
        if (seckillOrder != null){
            return seckillOrder.getSeckillProductId();
        }else{
            boolean isOver = getGoodsOver(productId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    //存入redis
    public void setProduct(String productId){
        redisService.set(SeckillKey.isGoodsOver,""+productId,true);
    }

    private boolean getGoodsOver(String productId) {
        return redisService.exists(SeckillKey.isGoodsOver, ""+productId);
    }

}
