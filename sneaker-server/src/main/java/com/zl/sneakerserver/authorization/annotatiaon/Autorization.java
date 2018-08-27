package com.zl.sneakerserver.authorization.annotatiaon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: le
 * @Date: 2018/8/17 17:48
 * @Description:
 */

/**
 * 检查用户是否登录
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autorization {
}
