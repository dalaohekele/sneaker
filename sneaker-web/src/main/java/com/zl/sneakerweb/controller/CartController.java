package com.zl.sneakerweb.controller;

import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerserver.authorization.annotatiaon.Autorization;
import com.zl.sneakerserver.authorization.annotatiaon.CurrentUser;
import com.zl.sneakerserver.dto.CartDto;
import com.zl.sneakerserver.server.CartService;
import com.zl.sneakerweb.utils.RequestUtil;
import com.zl.sneakerweb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        //加入购物车
        int effectNum = cartService.addCart(userId,productId,num);
        if (effectNum<=0){
            return ResultUtil.fail(ResultEnum.ADD_CART_ERROR);
        }
        return ResultUtil.ok(ResultEnum.ADD_CART_SUCCESS.getMessage());
    }

    @GetMapping(value = "/getCartList")
    @Autorization
    public Object getCartList(@CurrentUser User user){
        String userId = RequestUtil.getMapString(user.getId());
        List<CartDto> cartDtoList = cartService.getCartList(userId);
        return ResultUtil.ok(cartDtoList);
    }

    @PostMapping(value = "/updateCartNum")
    @Autorization
    public Object updateCartNum(@RequestBody Map<String,Object> reqMap,@CurrentUser User user){
        String userId = RequestUtil.getMapString(user.getId());
        String productId = RequestUtil.getMapString(reqMap.get("product_id").toString());
        String numString = RequestUtil.getMapString(reqMap.get("product_num").toString());
        Integer num = Integer.parseInt(numString);

        int effectNum = cartService.updateCartNum(userId,productId,num);
        if (effectNum <=0){
            return ResultUtil.fail();
        }
        return ResultUtil.ok();
    }

    @PostMapping(value = "/checkAll")
    @Autorization
    public Object checkAll(@RequestBody Map<String,Object> reqMap,@CurrentUser User user){
        String userId = RequestUtil.getMapString(user.getId());
        String check = RequestUtil.getMapString(reqMap.get("check").toString());

        int effectNum = cartService.checkAll(userId,check);
        if (effectNum <=0){
            return ResultUtil.fail();
        }
        return ResultUtil.ok();
    }

    @PostMapping("/delCartProduct")
    @Autorization
    public Object delCartProduct(@RequestBody Map<String,Object> reqMap,@CurrentUser User user){
        String userId = RequestUtil.getMapString(user.getId());
        String productId = RequestUtil.getMapString(reqMap.get("product_id").toString());

        int effectNum = cartService.delCartProduct(userId,productId);
        if (effectNum <=0){
            return ResultUtil.fail();
        }
        return ResultUtil.ok();
    }

    @PostMapping("/delCart")
    @Autorization
    public Object delCart(@CurrentUser User user){
        String userId = RequestUtil.getMapString(user.getId());

        int effectNum = cartService.delCart(userId);
        if (effectNum <=0){
            return ResultUtil.fail();
        }
        return ResultUtil.ok();
    }

}
