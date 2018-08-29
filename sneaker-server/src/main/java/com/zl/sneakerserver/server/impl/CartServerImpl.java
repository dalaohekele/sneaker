package com.zl.sneakerserver.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerentity.redis.CartPrefix;
import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerserver.dto.CartProductDto;
import com.zl.sneakerserver.dto.ProductInfoDto;
import com.zl.sneakerserver.exceptions.CartException;
import com.zl.sneakerserver.server.CartServer;
import com.zl.sneakerserver.server.ProductInfoServer;
import com.zl.sneakerserver.server.UserServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/8/28 16:20
 * @Description:
 */
@Service
@Slf4j
public class CartServerImpl implements CartServer {
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserServer userServer;

    @Autowired
    private ProductInfoServer productInfoServer;

    /**
     * 加入购物车
     *
     * @param userId
     * @param productId
     * @param num
     * @return
     */
    @Override
    public CartProductDto addCart(String userId, String productId, int num) {
        try {
            //获取用户信息
            User user = userServer.getUserById(userId);
            if (user ==null){
                return new CartProductDto(ResultEnum.FINDUSER_FAIL);
            }
            //获取商品
            ProductInfo productInfo = productInfoServer.findById(productId);
            //判断库存
            int productStock = productInfo.getProductStock();
            if (productStock - num <0) {
                throw new  CartException("商品库存不够");
            }
            ProductInfoDto productInfoDto = new ProductInfoDto();
            //读取缓存中数据
            String cacheProductList = redisService.get(CartPrefix.getCartList,userId,String.class);
            if (cacheProductList!=null) {
                JSONObject cacheProductListObject = JSONObject.parseObject(cacheProductList);
                ProductInfoDto cacheProductInfoDto = JSON.toJavaObject(cacheProductListObject, ProductInfoDto.class);
                //获取缓存商品列表
                List<ProductInfo> productInfoList = cacheProductInfoDto.getProductInfoList();
                //添加新增商品至购物车
                productInfoList.add(productInfo);
                productInfoDto.setProductInfoList(productInfoList);
                redisService.set(CartPrefix.getCartList, userId, productInfoDto);
                return new CartProductDto(ResultEnum.ADD_CART_SUCCESS);
            }
            List<ProductInfo> productInfoListNew = new ArrayList<>();
            productInfoListNew.add(productInfo);
            productInfoDto.setProductInfoList(productInfoListNew);
            //存入redis
            redisService.set(CartPrefix.getCartList, userId, productInfoDto);
            return new CartProductDto(ResultEnum.ADD_CART_SUCCESS);
        } catch (Exception e) {
            throw new CartException("添加购物车失败："+e.getMessage());
        }
    }

    @Override
    public List<CartProductDto> getCartList(String userId) {
        return null;
    }

    @Override
    public int updateCartNum(String userId, String productId, int num) {
        return 0;
    }

    @Override
    public int deleteCartItem(String userId, String productId) {
        return 0;
    }

    @Override
    public int deleteCartAll(String userId) {
        return 0;
    }
}
