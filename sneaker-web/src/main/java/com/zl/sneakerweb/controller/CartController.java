package com.zl.sneakerweb.controller;

import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerserver.authorization.annotatiaon.Autorization;
import com.zl.sneakerserver.authorization.annotatiaon.CurrentUser;
import com.zl.sneakerserver.server.CartService;
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
 * @Date: 2018/9/5 19:22
 * @Description:
 */
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {
    @Autowired
    CartService cartService;

    /**
     * 加入购物车
     * @param reqMap
     * @param user
     * @return
     */
    @PostMapping(value = "/add")
    @Autorization
    public Object addCart(@RequestBody Map<String, Object> reqMap, @CurrentUser User user){
        //获取登陆用户的userId
        String userId = RequestUtil.getMapString(user.getId());
        String productId = RequestUtil.getMapString(reqMap.get("product_id").toString());
        String numString = RequestUtil.getMapString(reqMap.get("product_num").toString());
        Integer num = Integer.parseInt(numString);

        int effectNum = cartService.addCart(userId,productId,num);
        if (effectNum<=0){
            return ResultUtil.fail(ResultEnum.ADD_CART_ERROR);
        }
        return ResultUtil.ok(ResultEnum.ADD_CART_SUCCESS.getMessage());
    }
}
