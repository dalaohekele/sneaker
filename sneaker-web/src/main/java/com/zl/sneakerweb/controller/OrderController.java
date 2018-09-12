package com.zl.sneakerweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zl.sneakerentity.enums.OrderStatusEnum;
import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.OrderDetail;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerserver.authorization.annotatiaon.Autorization;
import com.zl.sneakerserver.authorization.annotatiaon.CurrentUser;
import com.zl.sneakerserver.dto.OrderDto;
import com.zl.sneakerserver.server.OrderServer;
import com.zl.sneakerweb.utils.RequestUtil;
import com.zl.sneakerweb.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: le
 * @Date: 2018/7/27 16:36
 * @Description:
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderServer orderServer;

    /**
     * 创建订单接口
     *
     * @param reqMap
     * @return
     */
    @ApiOperation(value = "创建订单")
    @PostMapping(value = "/create")
    @Autorization
    public Object create(@RequestBody Map<String, Object> reqMap,@CurrentUser User user) {
        //获取登陆用户的userId
        String buyerOpenId = RequestUtil.getMapString(user.getId());

        String buyerName = RequestUtil.getMapString(reqMap.get("buyer_name").toString());
        String buyerPhone = RequestUtil.getMapString(reqMap.get("buyer_phone").toString());
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
                //创建订单
                OrderDto createResult = orderServer.createOrder(orderDto);

                Map<String, Object> map = new HashMap<>();
                map.put("order_id", createResult.getOrderId());
                return ResultUtil.ok(map);
            } catch (Exception e) {
                log.error(OrderStatusEnum.FIALCREATE.getStateInfo());
                log.error(e.getMessage());
                return ResultUtil.fail(OrderStatusEnum.FIALCREATE.getState(),e.getMessage());
            }
        } catch (Exception e) {
            log.error("【格式转换错误】restl={}" + items);
            log.error(e.getMessage());
            return ResultUtil.badArgument();
        }

    }

    /**
     * 订单列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    @Autorization
    public Object list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                       @CurrentUser User user) {

        try {
            String openid = user.getId();
            if (StringUtils.isEmpty(openid)) {
                log.error("【查询订单列表】openid为空");
                return ResultUtil.badArgumentValue();
            }
            //获取数据
            OrderDto orderDto = orderServer.findListByOpenId(openid, page, size);
            //只需要返回订单详情即可
            return ResultUtil.ok(orderDto.getOrderMasterList());
        } catch (Exception e) {
            log.error("订单列表查询失败:{}", e.getMessage());
            return ResultUtil.fail(OrderStatusEnum.FAIL.getState(),e.getMessage());
        }
    }

    @GetMapping("/list/detail")
    @Autorization
    public Object listDetail(@RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                       @CurrentUser User user) {

        try {
            String openid = user.getId();
            if (StringUtils.isEmpty(openid)) {
                log.error("【查询订单列表】openid为空");
                return ResultUtil.badArgumentValue();
            }
            //获取数据
            OrderDto orderDto = orderServer.findDetailsByOpenId(openid, page, size);
            //只需要返回订单详情即可
            return ResultUtil.ok(orderDto.getOrderMasterList());
        } catch (Exception e) {
            log.error("订单列表查询失败:{}", e.getMessage());
            return ResultUtil.fail(OrderStatusEnum.FAIL.getState(),e.getMessage());
        }
    }
    /**
     * 取消订单
     *
     * @param reqMap
     * @return
     */
    @PostMapping("/cancel")
    @Autorization
    public Object cancel(@CurrentUser User user,
                         @RequestBody Map<String, Object> reqMap) {
        try {
            String openId = user.getId();
            String orderId = RequestUtil.getMapString(reqMap.get("order_id").toString());
            if (openId == null || orderId == null) {
                return ResultUtil.badArgument();
            }
            //判断订单是否属于当前客户
            OrderDto orderDto = orderServer.witherOrderIdEqualsOpenid(openId, orderId);
            if (OrderStatusEnum.CANCEL.getState().equals(orderDto.getState())) {
                try {
                    //将订单状态进行变更
                    orderDto = orderServer.cancelOrder(openId, orderId);
                    return ResultUtil.ok(orderDto);
                } catch (Exception e) {
                    log.error("订单取消失败：{}", e.getMessage());
                    return ResultUtil.fail(ResultEnum.ORDER_UPDATE_FAIL);
                }
            } else {
                return ResultUtil.fail(ResultEnum.ORDER_OWNER_ERROR);
            }
        } catch (Exception e) {
            return ResultUtil.fail(ResultEnum.ORDER_CANCEL_FAIL.getState(), e.getMessage());
        }
    }

    /**
     * 通过订单Id 查询订单详情
     *
     * @param reqMap
     * @return
     */
    @PostMapping("/detail")
    @Autorization
    public Object detail(@CurrentUser User user,
                         @RequestBody Map<String, Object> reqMap) {
        String userId = user.getId();
        String orderId = RequestUtil.getMapString(reqMap.get("order_id").toString());
        if (orderId == null) {
            return ResultUtil.badArgument();
        }
        try {
            //判断订单与用户Id是否一致
            OrderDto orderDto = orderServer.findByOrderId(orderId,userId);
            if (orderDto.getOrderMaster()==null){
                return ResultUtil.fail(ResultEnum.ORDER_OWNER_ERROR);
            }
            return ResultUtil.ok(orderDto.getOrderMaster());
        } catch (Exception e) {
            log.error("订单查询错误：{}", e.getMessage());
            return ResultUtil.fail();
        }
    }



}
