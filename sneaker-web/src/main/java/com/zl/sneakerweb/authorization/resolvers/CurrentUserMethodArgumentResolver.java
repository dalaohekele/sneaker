package com.zl.sneakerweb.authorization.resolvers;

import com.zl.sneakerentity.model.User;
import com.zl.sneakerweb.authorization.annotatiaon.CurrentUser;
import com.zl.common.Constants;
import com.zl.sneakerserver.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Auther: le
 * @Date: 2018/8/18 10:03
 * @Description:
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserServer userServer;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        if (parameter.getParameterType().isAssignableFrom(User.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        String currentUserId = (String) webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        User user = userServer.getUserById(currentUserId);
        if (user.getRole() == 1 || user.getRole() == 2) {
            //从数据库中查询并返回
            return user;
        }
        throw new Exception("该账户已失效");
    }
}
