package com.zl.sneakerweb.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.SeckillOrder;
import com.zl.sneakerentity.model.SeckillProduct;
import com.zl.sneakerentity.model.User;
import com.zl.common.redis.GoodsKey;
import com.zl.common.redis.RedisService;
import com.zl.sneakerweb.authorization.annotatiaon.Autorization;
import com.zl.sneakerweb.authorization.annotatiaon.CurrentUser;
import com.zl.sneakerserver.dto.SeckillDto;
import com.zl.sneakerserver.rabbitmq.MQSender;
import com.zl.sneakerserver.rabbitmq.SeckillMessage;
import com.zl.sneakerserver.server.OrderServer;
import com.zl.sneakerserver.server.SeckillService;
import com.zl.sneakerweb.utils.ResultUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: le
 * @Date: 2018/10/7 11:08
 * @Description:
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    OrderServer orderServer;

    @Autowired
    SeckillService seckillService;

    @Autowired
    RedisService redisService;


    @Autowired
    MQSender sender;

    //基于令牌桶算法的限流实现类
    RateLimiter rateLimiter = RateLimiter.create(10);

    //做标记，判断该商品是否被处理过了
    private HashMap<String, Boolean> localOverMap = new HashMap<String, Boolean>();

    /**
     * 系统初始化,将商品信息加载到redis和本地内存
     */
    @Override
    public void afterPropertiesSet() {
        SeckillDto seckillDto = seckillService.getAllSeckill();
        if (seckillDto.getSeckillProducts() == null){
            return;
        }
        for (SeckillProduct seckillProduct:seckillDto.getSeckillProducts()){
            redisService.set(GoodsKey.getGoodsStock,""+seckillProduct.getProductId(),seckillProduct.getProductStock());
            //初始化商品都是没有处理过的
//            localOverMap.put(seckillProduct.getProductId(), false);
        }
    }


    /**
     * 上架秒杀商品
     *
     * @param productId
     * @return
     */
    @PostMapping(value = "/add_product")
    @Autorization
    @ResponseBody
    public Object addSeckillProduct(@RequestParam("productId") String productId) {
        SeckillDto seckillDto = seckillService.getSeckillByProdcutID(productId);
        SeckillProduct seckillProduct = seckillDto.getSeckillProduct();

        redisService.set(GoodsKey.getGoodsStock, "" + seckillProduct.getProductId(), seckillProduct.getProductStock());
//        //初始化商品都是没有处理过的
//        localOverMap.put(seckillProduct.getProductId(), false);
        return ResultUtil.ok(seckillProduct);
    }

    /**
     * 秒杀资格
     *
     * @param productId
     * @param user
     * @return
     */
    @PostMapping(value = "/do_seckill")
    @ResponseBody
    @Transactional
    public Object doSeckill(@RequestParam("productId") String productId, @CurrentUser User user) {
        //获取当前商品信息
        SeckillDto seckillDto = seckillService.getSeckillByProdcutID(productId);
        SeckillProduct seckillProduct = seckillDto.getSeckillProduct();

        long startAt = seckillProduct.getStartDate().getTime();
        long endAt = seckillProduct.getEndDate().getTime();
        long now = System.currentTimeMillis();
        //判断秒杀是否开始
        if (now < startAt) {//秒杀还没开始，倒计时
            return ResultUtil.fail(ResultEnum.SECKILL_NOT_START);
        } else if (now > endAt) {//秒杀已经结束
            return ResultUtil.fail(ResultEnum.SECKILL_FINISHED);

        } else {//秒杀进行中
            //限流
            if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
                return ResultUtil.fail(ResultEnum.ACCESS_LIMIT_REACHED);
            }
//        //内存标记，减少redis访问
//        boolean over = localOverMap.get(productId);
//        if (over)
//            return ResultUtil.fail(ResultEnum.SECKILL_OVER);
//        }
            afterPropertiesSet();
            //预减库存
            long stock = redisService.decr(GoodsKey.getGoodsStock, "" + productId);
            if (stock < 0) {
                return ResultUtil.fail(ResultEnum.SECKILL_OVER);
            }
            //判断重复秒杀
            SeckillOrder order = orderServer.getOrderByUserIdGoodsId(user.getId(), productId);
            if (order != null) {
                return ResultUtil.fail(ResultEnum.REPEATE_SECKILL);
            }
            //入队
            SeckillMessage message = new SeckillMessage();
            message.setUser(user);
            message.setSeckillProduct(seckillProduct);

            sender.sendSeckillMessage(message);
            return ResultUtil.ok(0);//排队中
        }


    }


    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @GetMapping(value = "/result")
    @ResponseBody
    public Object seckillResult(@RequestParam("goodsId") String goodsId,
                                @CurrentUser User user) {
        Object orderId = seckillService.getSeckillResult(user.getId(), goodsId);
        return ResultUtil.ok(orderId);
    }


    @GetMapping("/list_all")
    @ResponseBody
    public Object getAllSeckill() {
        SeckillDto seckillDto = seckillService.getAllSeckill();
        return ResultUtil.ok(seckillDto);
    }

    @GetMapping("/product")
    @ResponseBody
    public Object getSeckillProduct(@RequestParam("product_id") String productId) {
        SeckillDto seckillDto = seckillService.getSeckillByProdcutID(productId);
        return ResultUtil.ok(seckillDto);
    }

}
