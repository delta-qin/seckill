package com.deltaqin.seckill.common.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法注解：
 *      请求权限限制
 *      请求的个数限制
 *
 * @author deltaqin
 * @date 2021/6/13 下午11:02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    // 用户登录校验，加上注解默认就是需要登录的
    boolean needLogin() default true;
    // 秒杀间隔
    int seconds() default 0;
    // 秒杀限流：限流的时候可以可以对某一个用户限流，不是特定于每一个用户的限流那么使用的key就是一样的
    int maxCount() default 0;
}
