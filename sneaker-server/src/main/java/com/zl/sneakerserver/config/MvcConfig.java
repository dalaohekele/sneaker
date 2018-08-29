package com.zl.sneakerserver.config;

import com.zl.sneakerserver.authorization.interceptor.AuthorizationInterceptor;
import com.zl.sneakerserver.authorization.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/8/18 09:54
 * @Description:
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(authorizationInterceptor);
        //配置不拦截的路径
        ir.excludePathPatterns("/picture/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }


}
