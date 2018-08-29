package com.zl.sneakerweb.controller;

import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerserver.authorization.annotatiaon.Autorization;
import com.zl.sneakerserver.authorization.annotatiaon.CurrentUser;
import com.zl.sneakerserver.server.CartServer;
import com.zl.sneakerweb.utils.RequestUtil;
import com.zl.sneakerweb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: le
 * @Date: 2018/8/28 17:43
 * @Description:
 */
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {
    @Autowired
    private CartServer cartServer;


    @PostMapping(value = "/add")
    @Autorization
    public Object addCartController(@RequestBody Map<String,String> reqMap,
                                    @CurrentUser User user){
        String userId = RequestUtil.getMapString(user.getId());
        try {
            String productId = RequestUtil.getMapString(reqMap.get("productId"));
            String numString = RequestUtil.getMapString(reqMap.get("product_num"));
            int num = Integer.parseInt(numString);
            if (productId==null || num<=0){
                return ResultUtil.fail(ResultEnum.PARAM_ERROR);
            }
            //存入缓存
            cartServer.addCart(userId,productId,num);
            return ResultUtil.ok();
        }catch (Exception e){
            log.error("添加购物车失败：{}",e.getMessage());
            return ResultUtil.fail(ResultEnum.ADD_CART_ERROR.getState(),e.getMessage());
        }

    }


}
