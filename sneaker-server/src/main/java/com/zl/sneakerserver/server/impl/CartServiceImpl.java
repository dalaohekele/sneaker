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

import java.util.LinkedList;
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
                //转换为java实体类
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

    /**
     * 展示购物车
     * @param userId
     * @return
     */
    @Override
    public List<CartDto> getCartList(String userId) {
        List<String> jsonList = redisService.hvals(CartPrefix.getCartList,userId);
        List<CartDto> cartDtoList = new LinkedList<>();
        for (String json:jsonList){
            CartDto cartDto = JSON.toJavaObject(JSONObject.parseObject(json),CartDto.class);
            cartDtoList.add(cartDto);
        }
        return cartDtoList;
    }

    /**
     * 更新数量
     * @param userId
     * @param productId
     * @param num
     * @return
     */
    @Override
    public int updateCartNum(String userId, String productId, int num) {
        String json = redisService.hget(CartPrefix.getCartList,userId,productId);
        if (json==null){
            return 0;
        }
        CartDto cartDto = JSON.toJavaObject(JSONObject.parseObject(json),CartDto.class);
        cartDto.setProductNum(num);
        redisService.hset(CartPrefix.getCartList,userId,productId,JSON.toJSON(cartDto).toString());
        return 1;
    }

    /**
     * 全选商品
     * @param userId
     * @param checked
     * @return
     */
    @Override
    public int checkAll(String userId, String checked) {
        //获取商品列表
        List<String> jsonList = redisService.hvals(CartPrefix.getCartList,userId);
        for (String json:jsonList){
            CartDto cartDto = JSON.toJavaObject(JSONObject.parseObject(json),CartDto.class);
            if ("true".equals(checked)){
                cartDto.setCheck("1");
            }else if ("false".equals(checked)){
                cartDto.setCheck("0");
            }else {
                return 0;
            }
            redisService.hset(CartPrefix.getCartList,userId,cartDto.getProductId(),JSON.toJSON(cartDto).toString());
        }
        return 1;
    }

    /**
     * 删除商品
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public int delCartProduct(String userId, String productId) {
        redisService.hdel(CartPrefix.getCartList,userId,productId);
        return 1;
    }

    /**
     * 清空购物车
     * @param userId
     * @return
     */
    @Override
    public int delCart(String userId) {
        redisService.delete(CartPrefix.getCartList,userId);
        return 1;
    }
}
