package com.zl.sneakerserver.server;

import com.zl.sneakerserver.dto.CartDto;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/9/5 17:01
 * @Description:
 */
public interface CartService {

    /**
     * 加入购物车
     * @param userId
     * @param productId
     * @param num
     * @return
     */
    int addCart(String userId,String productId,int num);

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    List<CartDto> getCartList(String userId);

    /**
     * 修改数量
     * @param userId
     * @param productId
     * @param num
     * @return
     */
    int updateCartNum(String userId,String productId,int num);

    /**
     * 全选
     * @param userId
     * @param checked
     * @return
     */
    int checkAll(String userId,String checked);

    /**
     * 删除勾选商品
     * @param userId
     * @param productId
     * @return
     */
    int delCartProduct(String userId,String productId);

    /**
     * 删除购物车
     * @param userId
     * @return
     */
    int delCart(String userId);
}
