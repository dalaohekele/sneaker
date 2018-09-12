package com.zl.sneakerserver.server;

import com.zl.sneakerserver.dto.OrderDto;

/**
 * @Auther: le
 * @Date: 2018/7/27 16:38
 * @Description:
 */

public interface OrderServer {

    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    OrderDto createOrder(OrderDto orderDto);


    /**
     * 通过用户id查找
     * @param buyerOpenId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    OrderDto findListByOpenId(String buyerOpenId, Integer pageIndex, Integer pageSize);

    /**
     * 通过id查询订单中商品详情
     * @param buyerOpenId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    OrderDto findDetailsByOpenId(String buyerOpenId, Integer pageIndex, Integer pageSize);

    /**
     * 通过Id查找运单
     * @param orderId
     * @return
     */
    OrderDto findByOrderId(String orderId,String userId);


    /**
     * 取消订单
     * @param openId
     * @param orderId
     * @return
     */
    OrderDto cancelOrder(String openId,String orderId);

    /**
     * orderid是否对应openId
     * @param openId
     * @param orderId
     * @return
     */
    OrderDto witherOrderIdEqualsOpenid(String openId,String orderId);
}
