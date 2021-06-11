package com.deltaqin.seckill.service;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.model.UserModel;

/**
 * @author deltaqin
 * @date 2021/6/10 下午8:38
 */
public interface UserService {

    // 校验用户的信息
    UserModel login(String telPhone, String passwaord) throws CommonExceptionImpl;


    void register(UserModel userModel) throws CommonExceptionImpl;

    UserModel getUserById(Integer id);
}
