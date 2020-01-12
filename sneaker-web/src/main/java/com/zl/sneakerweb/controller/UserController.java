package com.zl.sneakerweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.User;
import com.zl.common.redis.RedisService;
import com.zl.sneakerweb.authorization.annotatiaon.AdminUser;
import com.zl.sneakerweb.authorization.annotatiaon.Autorization;
import com.zl.sneakerweb.config.Constants;
import com.zl.sneakerserver.dto.TokenModel;
import com.zl.sneakerserver.dto.UserDto;
import com.zl.sneakerserver.server.TokenManager;
import com.zl.sneakerserver.server.UserServer;
import com.zl.common.utils.MD5Util;
import com.zl.sneakerweb.utils.RequestUtil;
import com.zl.sneakerweb.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/login")
    public Object Login(@RequestBody Map<String, Object> reqMap) {
        String userName = RequestUtil.getMapString(reqMap.get("user_name").toString());
        String passWord = RequestUtil.getMapString(reqMap.get("pass_word").toString());
        //判断用户名是否存在
        User user = userServer.getUser(userName);
        if (user == null) {
            return ResultUtil.fail(ResultEnum.LOGIN_FAIL);
        }
        //获取数据库中的密码，与输入的密码加密后比对
        String dbPassword = user.getPassWord();
        String equalPassword = MD5Util.inputPassToDbPass(passWord);
        if (!equalPassword.equals(dbPassword)) {
            return ResultUtil.fail(ResultEnum.LOGIN_FAIL_PASS);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(user.getId());
        return ResultUtil.ok(model);
    }

    /**
     * 通过wx登陆
     *
     * @param reqMap
     * @return
     */
    @PostMapping("/wx/login")
    public Object LoginWX(@RequestBody Map<String, Object> reqMap) {
        String code = RequestUtil.getMapString(reqMap.get("wx_code").toString());
        //微信接口
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constants.APPID +
                "&secret=" + Constants.SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        //restTemplate请求微信的接口，获取微信的sessionId
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                null, String.class);
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String response = responseEntity.getBody();
            /*
            //string转jsonObject,
            正常返回的JSON数据包{"openid": "OPENID","session_key": "SESSIONKEY"}
             */
            JSONObject responseObject = JSONObject.parseObject(response);
            String wxOpenId = responseObject.get("openid").toString();
//            String sessionKey = responseObject.get("session_key").toString();
            User user = userServer.findUserByWxId(wxOpenId);
            //wxOpenId与id都不存在则创建一个新用户
            if (user == null) {
                UserDto userDto = userServer.autoRegisterUser(wxOpenId);
                //生成一个token，保存用户登录状态
                TokenModel model = tokenManager.createToken(userDto.getUser().getId());
                return ResultUtil.ok(model);
            }else {
                //生成一个token，保存用户登录状态
                TokenModel model = tokenManager.createToken(user.getId());
                return ResultUtil.ok(model);
            }
        }
        return ResultUtil.fail();
    }

    /**
     * 用户信息
     * @param user
     * @return
     */
    @GetMapping("/info")
    @Autorization
    public Object userListController(@AdminUser User user) {
        //校验token
        tokenManager.getToken(user.getId());
        return ResultUtil.ok(user);
    }


}
