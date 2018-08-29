package com.zl.sneakerserver.server;

import com.zl.sneakerserver.dto.CartProductDto;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/8/28 15:40
 * @Description:
 */
public interface CartServer {
    /**
     * 加入购物车
     * @param userId
     * @param productId
     * @param num
     * @return
     */
    CartProductDto addCart(String userId,String productId,int num);

    /**
     * 获取购物车列表
     * @param userId
     * @return
     */
    List<CartProductDto> getCartList(String userId);

    /**
     * 更新购物车数量
     * @param userId
     * @param productId
     * @param num
     * @return
     */
    int updateCartNum(String userId,String productId,int num);

    /**
     * 删除单个商品
     * @param userId
     * @param productId
     * @return
     */
    int deleteCartItem(String userId,String productId);

    /**
     * 删除所有购物车中商品
     * @param userId
     * @return
     */
    int deleteCartAll(String userId);
}
