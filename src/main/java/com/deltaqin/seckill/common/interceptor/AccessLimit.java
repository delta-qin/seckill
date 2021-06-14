package com.deltaqin.seckill.common.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author deltaqin
 * @date 2021/6/13 下午11:02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    // 用户登录校验
    boolean needLogin() default true;
    // 秒杀间隔
    int seconds();
    // 秒杀限流
    int maxCount();
}
