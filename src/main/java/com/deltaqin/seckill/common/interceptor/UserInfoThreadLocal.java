package com.deltaqin.seckill.common.interceptor;

import com.deltaqin.seckill.model.UserModel;

/**
 * 每一个线程独有的， 处理的时候使用，处理完之后在拦截器里面的后置处理里面手动调用删除，否则会造成内存泄漏
 *
 * @author deltaqin
 * @date 2021/6/13 下午11:05
 */
public class UserInfoThreadLocal {

    public static ThreadLocal<UserModel> userModelThreadLocal = new ThreadLocal<>();

    public static void setUser(UserModel user) {
        userModelThreadLocal.set(user);
    }

    public static UserModel getUser() {
        return userModelThreadLocal.get();
    }

    // 手动调用删除防止内存泄漏
    public static void removeUser() {
        userModelThreadLocal.remove();
    }

}
