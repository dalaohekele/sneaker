package com.zl.sneakerweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: le
 * @Date: 2018/7/26 14:42
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @GetMapping("/list")
    public String userListController(){
        return "hello springboot";
    }
}
