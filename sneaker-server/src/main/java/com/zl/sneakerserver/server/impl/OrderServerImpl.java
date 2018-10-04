package com.zl.sneakerserver.server.impl;

import com.zl.sneakerentity.dao.OrderDetailDao;
import com.zl.sneakerentity.dao.OrderMasterDao;
import com.zl.sneakerentity.dao.ProductInfoDao;
import com.zl.sneakerentity.enums.OrderStatusEnum;
import com.zl.sneakerentity.enums.PayStatusEnum;
import com.zl.sneakerentity.enums.ProductInfoStateEnum;
import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.OrderDetail;
import com.zl.sneakerentity.model.OrderMaster;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerserver.dto.OrderDetailDto;
import com.zl.sneakerserver.dto.OrderDto;
import com.zl.sneakerserver.exceptions.OrderException;
import com.zl.sneakerserver.server.OrderServer;
import com.zl.sneakerserver.server.ProductInfoServer;
import com.zl.sneakerserver.utils.KeyUtils;
import com.zl.sneakerserver.utils.PageCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 16:45
 * @Description:
 */
@Service
@Slf4j
public class OrderServerImpl implements OrderServer {
    @Autowired
    OrderMasterDao orderMasterDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    ProductInfoDao productInfoDao;

    @Autowired
    ProductInfoServer productInfoServer;

    /**
     * 创建订单
     *
     * @param orderDto
     * @return
     * @throws OrderException
     */
    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) throws OrderException {
        if (orderDto.getOrderDetailList().get(0) != null) {
            String orderId = KeyUtils.genUniqueKey();
            //订单价格
            BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
            try {
                //获取订单详情中商品数量，然后计算总价
                for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
                    //通过订单id查询商品
                    ProductInfo productInfo = productInfoServer.findById(orderDetail.getProductId());
                    int productQuantity = Math.abs(orderDetail.getProductQuantity());
                    //判断商品库存,Math.abs()转换负数
                    if(productInfo.getProductStock() - productQuantity<=-1){
                        throw new OrderException("商品库存不够");
                    } else {
                        //计算总价
                        orderAmount = productInfo.getProductPrice()
                                .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                                .add(orderAmount);
                        orderDetail.setDetailId(KeyUtils.genUniqueKey());
                        orderDetail.setOrderId(orderId);
                        //BeanUtils 的拷贝，需要对应的实体的set方法名相同
                        BeanUtils.copyProperties(productInfo, orderDetail);
                        //添加订单概述
                        Integer ord = orderDetailDao.insertOrderDetail(orderDetail);
                        if (ord <=0){
                            return new OrderDto(ResultEnum.ORDERDETAIL_NOT_EXIST);
                        }else{
                            //减库存
                            productInfoDao.updateProductQuantity(orderDetail.getProductId(),
                                    productQuantity);
                        }
                    }
                }
                //订单数据
                OrderMaster orderMaster = new OrderMaster();
                orderDto.setOrderId(orderId);
                BeanUtils.copyProperties(orderDto, orderMaster);
                orderMaster.setOrderId(orderId);
                orderMaster.setOrderStatus(OrderStatusEnum.NEW.getState());
                orderMaster.setPayStatus(PayStatusEnum.WAIT.getState());
                orderMaster.setOrderAmount(orderAmount);

                //创建订单
                Integer effectnum = orderMasterDao.insertOrderMaster(orderMaster);
                if (effectnum <= 0) {
                    return new OrderDto(ProductInfoStateEnum.INNER_ERROE);
                } else {
                    return new OrderDto(ProductInfoStateEnum.SUCCESS, orderMaster.getOrderId());
                }
            } catch (Exception e) {
                throw new OrderException("创建订单错误 error:" + e.getMessage());
            }
        } else {
            log.error("createOrder error:{}", ProductInfoStateEnum.EMPTY.getStateInfo());
            return new OrderDto(ProductInfoStateEnum.EMPTY);
        }
    }


    /**
     * 通过openid查找订单
     *
     * @param buyerOpenId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public OrderDto findListByOpenId(String buyerOpenId, Integer pageIndex, Integer pageSize) {
        //分页数
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        OrderDto orderDto = new OrderDto();
        try {
            List<OrderMaster> orderMasterList = orderMasterDao.selectOrderMasterByOpenid(buyerOpenId, rowIndex, pageSize);
            //返回数据
            orderDto.setOrderMasterList(orderMasterList);
        } catch (Exception e) {
            throw new OrderException("运单查找失败 error:" + e.getMessage());
        }
        return orderDto;
    }

    /**
     * 通过orderid查询
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public OrderDetailDto findByOrderId(String orderId,String userId) {
        try {
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            OrderMaster orderMaster = orderMasterDao.selectByOrderId(orderId,userId);
            List<OrderDetail> orderDetailList = orderDetailDao.selectOrderDetailList(orderId);

            orderDetailDto.setOrderMaster(orderMaster);
            orderDetailDto.setDetailList(orderDetailList);

            return orderDetailDto;
        } catch (Exception e) {
            throw new OrderException("通过orderid查询 error:" + e.getMessage());
        }

    }


//    /**已废弃的方法
//     * 通过id查询订单中商品详情
//     * @param buyerOpenId
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     */
//    @Override
//    @Transactional
//    public OrderDto findDetailsByOpenId(String buyerOpenId, Integer pageIndex, Integer pageSize){
//        //分页数
//        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
//        OrderDto orderDto = new OrderDto();
//        try {
//            List<OrderMaster> orderMasterList = orderMasterDao.selectOrderDetailByOpenid(buyerOpenId, rowIndex, pageSize);
//            //返回数据
//            orderDto.setOrderMasterList(orderMasterList);
//        } catch (Exception e) {
//            throw new OrderException("运单查找失败 error:" + e.getMessage());
//        }
//        return orderDto;
//    }

    /**
     * 取消订单
     *
     * @param openId
     * @param orderId
     * @return
     * @throws OrderException
     */
    @Override
    @Transactional
    public OrderDto cancelOrder(String openId, String orderId) throws OrderException {
        //调用witherOrderIdEqualsOpenid 方法,判断openid是否属于orderid
        OrderDto orderDto = witherOrderIdEqualsOpenid(openId, orderId);
        if (OrderStatusEnum.CANCEL.getState().equals(orderDto.getState())) {
            try {
                //更新订单状态
                Integer effectNum = orderMasterDao.updateOrderById(openId, orderId);
                if (effectNum <= 0) {
                    return new OrderDto(ResultEnum.ORDER_UPDATE_FAIL);
                } else {
                    //取消订单成功，库存回滚
                    for (OrderDetail orderDetail : orderDetailDao.selectOrderDetailList(orderId)){
                        //获取id 和数量,利用Math.abs()绝对值转换
                        String productId = orderDetail.getProductId();
                        Integer productQuantity = Math.abs(orderDetail.getProductQuantity());
                        //回滚库存
                        productInfoDao.backProductQuantity(productId,productQuantity);
                    }
                    //通过id 获取订单详情
                    OrderMaster orderMaster = findByOrderId(orderId,openId).getOrderMaster();
                    //将更新后的orderMaster 赋值orderDto
                    BeanUtils.copyProperties(orderMaster, orderDto);
                }
            } catch (Exception e) {
                throw new OrderException("取消订单失败 error: " + e.getMessage());
            }
        }
        return orderDto;
    }

    /**
     * 判断openid是否属于orderid
     *
     * @param openId
     * @param orderId
     * @return
     * @throws OrderException
     */
    @Override
    @Transactional
    public OrderDto witherOrderIdEqualsOpenid(String openId, String orderId) throws OrderException {
        OrderDto orderDto = new OrderDto();
        try {
            //判断openid与orderid
            Integer effectNum = orderMasterDao.selectOpenIdandOrderId(openId, orderId);
            if (effectNum <= 0) {
                //订单不属于这个openid
                return new OrderDto(ResultEnum.ORDER_OWNER_ERROR);
            } else {
                //订单取消成功
                orderDto.setState(OrderStatusEnum.CANCEL.getState());
                orderDto.setStateInfo(OrderStatusEnum.CANCEL.getStateInfo());
            }
        } catch (Exception e) {
            log.error("判断openid与orderid错误 error:{}", e.getMessage());
            throw new OrderException("判断openid与orderid错误 error: " + e.getMessage());
        }
        return orderDto;
    }

    /**
     * 通过id查询订单中商品详情
     * @param buyerOpenId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<OrderDetailDto> findDetailById(String buyerOpenId, Integer pageIndex, Integer pageSize) {
        //分页数
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        try {
            List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
            //关联查询ordermaster和orderdetail
            List<OrderMaster> orderMasterList = orderMasterDao.selectOrderDetailByOpenid(buyerOpenId, rowIndex, pageSize);
            //获取orderMaster中需要返回的值 赋值给新定义的pojo类orderDetailDto
            for(OrderMaster orderMaster :orderMasterList){
                OrderDetailDto orderDetailDto = new OrderDetailDto();

                String orderId = orderMaster.getOrderId();
                List<OrderDetail> orderDetailList = orderDetailDao.selectOrderDetailList(orderId);
                //添加订单详情的值
                orderDetailDto.setOrderId(orderId);
                orderDetailDto.setPayStatus(orderMaster.getPayStatus());
                orderDetailDto.setOrderStatus(orderMaster.getOrderStatus());
                orderDetailDto.setOrderAmount(orderMaster.getOrderAmount());
                orderDetailDto.setCreateTime(orderMaster.getCreateTime());
                orderDetailDto.setUpdateTime(orderMaster.getUpdateTime());
                orderDetailDto.setDetailList(orderDetailList);

                orderDetailDtoList.add(orderDetailDto);
            }

            return orderDetailDtoList;
        } catch (Exception e) {
            throw new OrderException("运单查找失败findDetailById error:" + e.getMessage());
        }
    }

}
