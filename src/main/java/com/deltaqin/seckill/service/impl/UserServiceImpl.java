package com.deltaqin.seckill.service.impl;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.common.validator.ValidationResult;
import com.deltaqin.seckill.common.validator.ValidatorUtil;
import com.deltaqin.seckill.dao.UserInfoMapper;
import com.deltaqin.seckill.dao.UserPasswordMapper;
import com.deltaqin.seckill.dataobject.UserInfo;
import com.deltaqin.seckill.dataobject.UserPassword;
import com.deltaqin.seckill.model.UserModel;
import com.deltaqin.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author deltaqin
 * @date 2021/6/10 下午8:44
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 校验用户的信息
     * @param telPhone
     * @param passwaord
     * @return
     * @throws CommonExceptionImpl
     */
    @Override
    public UserModel login(String telPhone, String passwaord) throws CommonExceptionImpl {
        // 获取用户的DO
        UserInfo userInfo = userInfoMapper.selectByTelPhone(telPhone);
        if (userInfo == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_LOGIN_FAIL);
        }

        // 获取密码的DO
        UserPassword userPassword = userPasswordMapper.selectByUserId(userInfo.getId());

        // 验证密码是否准确
        if (!StringUtils.equals(userPassword.getPassword(), passwaord)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_LOGIN_FAIL);
        }

        // 两个DO转换为Model领域模型
        UserModel userModel = converFromDataObject(userInfo, userPassword);
        return userModel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void register(UserModel userModel) throws CommonExceptionImpl {
        if (userModel == null){
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR);
        }

        ValidationResult validate = validatorUtil.validate(userModel);

        if (validate.isHasError()) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, validate.getErrMsgString());
        }
        UserInfo userInfo = convertUserFromModel(userModel);

        try {
            userInfoMapper.insert(userInfo);
        } catch (DuplicateKeyException exception) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "手机号已经注册");
        }

        userModel.setId(userInfo.getId());
        UserPassword userPassword = convertPasswordFromModel(userModel);

        userPasswordMapper.insertSelective(userPassword);
    }

    @Override
    public UserModel getUserById(Integer id) throws CommonExceptionImpl {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        if (userInfo == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_EXIST);
        }

        UserPassword userPassword = userPasswordMapper.selectByUserId(id);
        UserModel userModel = convertModelFromDo(userInfo, userPassword);
        return userModel;
    }

    // 使用缓存来获用户信息
    //public UserModel getUserByIdInCache(Integer id) {
    //    redisTemplate.opsForValue().get()
    //}

    @Override
    public UserModel getUserByPhone(String phone) throws CommonExceptionImpl {
        //userInfoMapper.selectByPrimaryKey(new UserInfoKey());
        UserInfo userInfo = userInfoMapper.selectByTelPhone(phone);
        if (userInfo == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_EXIST);
        }

        UserModel userModel = convertModelFromDo(userInfo, null);
        return userModel;
    }

    private UserModel convertModelFromDo(UserInfo userInfo, UserPassword userPassword) {
        if (userInfo == null) return null;

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfo, userModel);

        if (userPassword != null)
            userModel.setPassword(userPassword.getPassword());
        return userModel;
    }

    private UserPassword convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) return null;

        UserPassword userPassword = new UserPassword();
        userPassword.setPassword(userModel.getPassword());
        userPassword.setUserId(userModel.getId());
        return userPassword;
    }

    /**
     * 两个DO转换为Model领域模型
     * @param userInfo
     * @param userPassword
     * @return
     */
    private UserModel converFromDataObject(UserInfo userInfo, UserPassword userPassword) {
        if (userInfo == null) return null;

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfo, userModel);

        if (userPassword != null) {
            BeanUtils.copyProperties(userPassword, userModel);
        }
        return userModel;
    }

    private UserInfo convertUserFromModel(UserModel userModel) {
        if (userModel == null) {
            return  null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userModel, userInfo);
        return userInfo;
    }


}
