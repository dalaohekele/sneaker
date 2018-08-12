package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.OrderDetail;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 15:33
 * @Description:
 */
public interface OrderDetailDao {

    /**
     * 添加订单概述
     * @param orderDetail
     * @return
     */
    Integer insertOrderDetail(OrderDetail orderDetail);

    /**
     * 通过orderid获取订单概述
     * @param orderId
     * @return
     */
    List<OrderDetail> selectOrderDetailList(String orderId);
}
