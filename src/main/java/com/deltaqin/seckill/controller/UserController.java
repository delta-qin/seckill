package com.deltaqin.seckill.controller;

import com.deltaqin.seckill.common.constant.GlobalConstant;
import com.deltaqin.seckill.common.entities.ResultType;
import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.common.utils.Encode;
import com.deltaqin.seckill.model.UserModel;
import com.deltaqin.seckill.service.UserService;
import com.deltaqin.seckill.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import static com.deltaqin.seckill.common.constant.GlobalConstant.CONTENT_TYPE_FORMED;

/**
 * @author deltaqin
 * @date 2021/6/10 下午9:11
 */
@RestController("user")
//解决跨域
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Api(tags = "用户接口")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 登录之后返回 token
     * @param telPhone
     * @param password
     * @return
     * @throws CommonExceptionImpl
     */
    @ApiOperation(value = "用户登录测试接口", notes = "登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST,consumes={CONTENT_TYPE_FORMED})
    public ResultType login(@RequestParam(name = "telphone") String telPhone,
                            @RequestParam(name = "password") String password) throws CommonExceptionImpl, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(password)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.login(telPhone, Encode.encodeByMd5(password));

        // 能走到这里说明通过校验，开始session+token
        String token = UUID.randomUUID().toString();
        token = token.replace("-", "");

        //将登陆凭证加入到用户登陆成功的session内
        httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        return ResultType.create(token);
    }

    @ApiOperation(value = "用户注册测试接口", notes = "注册接口")
    @RequestMapping(path = "/register",method = RequestMethod.POST, consumes = CONTENT_TYPE_FORMED)
    public ResultType register(@RequestParam(name = "telphone") String telphone,
                               @RequestParam(name = "otpCode") String otpCode,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "gender") Integer gender,
                               @RequestParam(name = "age") Integer age,
                               @RequestParam(name = "password") String password
                               ) throws CommonExceptionImpl, UnsupportedEncodingException, NoSuchAlgorithmException {

        String OtpCodeInSession = (String)httpServletRequest.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode, OtpCodeInSession)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "验证码错误");
        }

        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setPassword(Encode.encodeByMd5(password));
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byPhone");
        userService.register(userModel);

        log.info("用户登录: {}" , userModel);
        return ResultType.create(null);
    }

    @ApiOperation(value = "获取验证码", notes = "验证码在控制台")
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    public ResultType getOtpCode(@RequestParam(name = "telphone") String telphone) {
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 100000;
        String otpCode = String.valueOf(randomInt);

        httpServletRequest.getSession().setAttribute(telphone, otpCode);

        System.out.println("telphone:" + telphone + ", otpCode:" + otpCode);

        return ResultType.create(null);
    }

    @ApiOperation(value = "使用ID获取用户", notes = "获取用户")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultType getUserById(@RequestParam(name = "id") Integer id) throws CommonExceptionImpl {
        if (id == null)
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "需要用户id");

        UserModel userModel = userService.getUserById(id);

        if (userModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_EXIST);
        }

        UserVo userVo = convertFromModel(userModel);

        return ResultType.create(userVo);

    }

    @ApiOperation(value = "使用电话获取用户", notes = "获取用户")
    @RequestMapping(value = "/getbyphone", method = RequestMethod.GET)
    public ResultType getUserByPhone(@RequestParam(name = "phone") String phone) throws CommonExceptionImpl {
        if (phone == null)
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "需要用户手机号");

        UserModel userModel = userService.getUserByPhone(phone);

        if (userModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_EXIST);
        }

        UserVo userVo = convertFromModel(userModel);

        return ResultType.create(userVo);

    }

    private UserVo convertFromModel(UserModel userModel) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel, userVo);
        return userVo;
    }
}
