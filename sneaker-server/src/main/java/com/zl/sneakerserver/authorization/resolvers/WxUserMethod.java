package com.zl.sneakerserver.authorization.resolvers;

import com.zl.sneakerserver.authorization.annotatiaon.WxUser;
import com.zl.sneakerserver.config.Constants;
import com.zl.sneakerserver.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Auther: le
 * @Date: 2018/8/24 14:01
 * @Description:
 */
public class WxUserMethod implements HandlerMethodArgumentResolver {
    @Autowired
    private UserServer userServer;



    @Override
    public boolean supportsParameter(MethodParameter parameter){
        if (parameter.getParameterType().isAssignableFrom(Integer.class)
                &&parameter.hasParameterAnnotation(WxUser.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        String token = request.getHeader(Constants.LOGIN_TOKEN_WX);
        if (token==null||token.isEmpty()){
            return null;
        }
        return null;
    }
}
