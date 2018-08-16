package com.zl.sneakerweb.controller;

import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.User;
import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerserver.server.UserServer;
import com.zl.sneakerserver.utils.MD5Util;
import com.zl.sneakerweb.core.shiro.CustomerAuthenticationToken;
import com.zl.sneakerweb.utils.RequestUtil;
import com.zl.sneakerweb.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: le
 * @Date: 2018/7/26 14:42
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServer userServer;

    @Autowired
    RedisService redisService;

    @PostMapping("/login")
    public Object Login(HttpServletResponse response, @RequestBody Map<String, Object> reqMap){
        String userName = RequestUtil.getMapString(reqMap.get("user_name").toString());
        String passWord = RequestUtil.getMapString(reqMap.get("pass_word").toString());
        //判断用户名是否存在
        User user = userServer.getUser(userName).getUser();
        if(user==null){
            return ResultUtil.fail(ResultEnum.LOGIN_FAIL);
        }
        //获取数据库中的密码，与输入的密码加密后比对
        String dbPassword = user.getPassWord();
        String equalPassword = MD5Util.inputPassToDbPass(passWord);
        if (!equalPassword.equals(dbPassword)){
            return ResultUtil.fail(ResultEnum.LOGIN_FAIL_PASS);
        }
        CustomerAuthenticationToken token =new CustomerAuthenticationToken(userName,passWord,false);



        return ResultUtil.ok();
    }





    @GetMapping("/list")
    public String userListController(){
        return "hello springboot";
    }


}
