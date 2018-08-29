package com.zl.sneakerweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zl.sneakerentity.enums.OrderStatusEnum;
import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.OrderDetail;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerserver.dto.OrderDto;
import com.zl.sneakerserver.rabbitmq.MQSender;
import com.zl.sneakerserver.rabbitmq.OrderMQMessage;
import com.zl.sneakerserver.server.OrderServer;
import com.zl.sneakerserver.server.ProductInfoServer;
import com.zl.sneakerweb.utils.RequestUtil;
import com.zl.sneakerweb.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther: le
 * @Date: 2018/8/13 18:25
 * @Description:
 */

@RestController
@RequestMapping("/order")
@Slf4j
@Api("订单相关api")
public class OrderMQController {
    @Autowired
    private OrderServer orderServer;

    @Autowired
    MQSender sender;

    @Autowired
    ProductInfoServer productInfoServer;

    @ApiOperation(value = "创建订单")
    @PostMapping(value = "/create/mq")
    public Object createMQ(@RequestBody Map<String, Object> reqMap){
        String buyerName = RequestUtil.getMapString(reqMap.get("buyer_name").toString());
        String buyerPhone = RequestUtil.getMapString(reqMap.get("buyer_phone").toString());
        String buyerOpenId = RequestUtil.getMapString(reqMap.get("open_id").toString());
        String buyerAddress = RequestUtil.getMapString(reqMap.get("buyer_address").toString());
        //订单详情item里面包含orderdetail
        Object items = reqMap.get("item");

        if (buyerName == null || buyerPhone == null || buyerAddress == null) {
            return ResultUtil.badArgumentValue();
        }
        try {
            //对象转string
            String itemsString = JSON.toJSONString(items);
            //string再相关对象到Java实体
            List<OrderDetail> orderDetailList = JSONObject.parseArray(itemsString, OrderDetail.class);
            try {
                OrderDto orderDto = new OrderDto();
                orderDto.setBuyerName(buyerName);
                orderDto.setBuyerPhone(buyerPhone);
                orderDto.setBuyerAddress(buyerAddress);
                orderDto.setBuyerOpenid(buyerOpenId);
                orderDto.setOrderDetailList(orderDetailList);


                for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
                    //通过订单id查询商品
                    ProductInfo productInfo = productInfoServer.findById(orderDetail.getProductId());
                    //判断商品库存
                    if (productInfo.getProductStock() - orderDetail.getProductQuantity() <= -1) {
                        return ResultUtil.fail(ResultEnum.PRODUC_STOCK_ERROR.getState(),ResultEnum.PRODUC_STOCK_ERROR.getMessage());
                    }
                }
                //创建订单，异步的mq
                OrderMQMessage orderMQMessage = new OrderMQMessage();
                orderMQMessage.setOrderDto(orderDto);

                //用MQ消费订单创建
                sender.sendOrderMessage(orderMQMessage);

                return ResultUtil.ok();
            } catch (Exception e) {
                log.error(OrderStatusEnum.FIALCREATE.getStateInfo());
                log.error(e.getMessage());
                return ResultUtil.fail();
            }
        } catch (Exception e) {
            log.error("【格式转换错误】restl={}" + items);
            log.error(e.getMessage());
            return ResultUtil.badArgument();
        }
    }



}
