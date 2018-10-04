package com.zl.sneakerserver.authorization.annotatiaon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: le
 * @Date: 2018/9/25 14:31
 * @Description:
 */

/* 管理员注解*/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminUser {
}
