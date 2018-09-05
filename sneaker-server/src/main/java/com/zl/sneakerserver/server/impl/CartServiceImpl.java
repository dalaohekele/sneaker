package com.zl.sneakerserver.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zl.sneakerentity.dao.ProductInfoDao;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerentity.redis.CartPrefix;
import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerserver.dto.CartDto;
import com.zl.sneakerserver.server.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/9/5 17:48
 * @Description:
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    RedisService redisService;

    @Autowired
    ProductInfoDao productInfoDao;

    @Override
    public int addCart(String userId, String productId, int num) {
        //key为 userId_cart,校验是否已存在
        Boolean exists = redisService.existsValue(CartPrefix.getCartList,userId,productId);
        if (exists){
            //获取现有的购物车中的数据
            String json = redisService.hget(CartPrefix.getCartList,userId,productId);
            if (json !=null){
                CartDto cartDto = JSON.toJavaObject(JSONObject.parseObject(json),CartDto.class);
                cartDto.setProductNum(cartDto.getProductNum()+num);
                redisService.hset(CartPrefix.getCartList,userId,productId,JSON.toJSON(cartDto).toString());
            }else {
                return 0;
            }
            return 1;
        }
        //根据商品id获取商品
        ProductInfo productInfo = productInfoDao.findProductById(productId);
        if (productInfo==null){
            return 0;
        }
        //设置购物车值
        CartDto cartDto = new CartDto();
        cartDto.setProductId(productId);
        cartDto.setProductName(productInfo.getProductName());
        cartDto.setProductIcon(productInfo.getProductIcon());
        cartDto.setProductPrice(productInfo.getProductPrice());
        cartDto.setProductStatus(productInfo.getProductStatus());
        cartDto.setProductNum(num);
        cartDto.setCheck("1");
        redisService.hset(CartPrefix.getCartList,userId,productId,JSON.toJSON(cartDto).toString());
        return 1;
    }

    @Override
    public List<CartDto> getCartList(String userId) {
        return null;
    }

    @Override
    public int updateCartNum(String userId, String productId, int num, String checked) {
        return 0;
    }

    @Override
    public int checkAll(String userId, String checked) {
        return 0;
    }

    @Override
    public int delCartProduct(String userId, String productId) {
        return 0;
    }

    @Override
    public int delCart(String userId) {
        return 0;
    }
}
