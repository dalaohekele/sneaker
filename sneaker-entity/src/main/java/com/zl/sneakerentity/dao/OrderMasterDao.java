package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.OrderMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 15:33
 * @Description:
 */
public interface OrderMasterDao {
    /**
     * 生成订单详情
     * @param order
     * @return
     */
    Integer insertOrderMaster(OrderMaster order);

    /**
     * 通过openId查询
     * @param buyerOpenid
     * @return
     */
    List<OrderMaster> selectOrderMasterByOpenid(@Param("openid") String buyerOpenid,
                                                @Param("rowIndex")Integer rowIndex,
                                                @Param("pageSize")Integer pageSize);


    /**
     * 通过Id 查找详情
     * @param orderId
     * @return
     */
    OrderMaster selectByOrderId(@Param("orderId") String orderId,
                                @Param("buyerOpenid") String buyerOpenid);

    /**
     * 取消订单
     * @param openId
     * @param orderId
     * @return
     */
    Integer updateOrderById(@Param("openId")String openId,
                            @Param("orderId")String orderId);

    /**
     * openid是否对应orderId
     * @param openId
     * @param orderId
     * @return
     */
    Integer selectOpenIdandOrderId(@Param("openId")String openId,
                                   @Param("orderId")String orderId);
}
