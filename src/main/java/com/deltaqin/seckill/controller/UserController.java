package com.deltaqin.seckill.controller;

import com.deltaqin.seckill.common.entities.ResultType;
import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.common.utils.CodeUtil;
import com.deltaqin.seckill.common.utils.Encode;
import com.deltaqin.seckill.model.UserModel;
import com.deltaqin.seckill.redisutils.jedis.RedisService;
import com.deltaqin.seckill.redisutils.keyprefix.UserKeyPrefix;
import com.deltaqin.seckill.service.UserService;
import com.deltaqin.seckill.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
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

    @Autowired
    private RedisService redisService;

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
                            @RequestParam(name = "password") String password,
                            @RequestParam(name = "code")String code ) throws CommonExceptionImpl, UnsupportedEncodingException, NoSuchAlgorithmException {

        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(password)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_LOGIN_FAIL);
        }

        //String codeInSession = (String)httpServletRequest.getSession().getAttribute(telPhone);
        //if (StringUtils.isEmpty(codeInSession)) {
        //}

        String codeInRedis = redisService.get(UserKeyPrefix.getVerificationCode, telPhone, String.class);
        if (StringUtils.isEmpty(codeInRedis)) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "验证码错误");
        }

        UserModel userModel = userService.login(telPhone, Encode.encodeByMd5(password));

        // 能走到这里说明通过校验，开始session或者token


        //将登陆凭证加入到用户登陆成功的session内（v1.0）
        //httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        //httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        // 使用token+redis实现(v2.0)
        String token = UUID.randomUUID().toString();
        token = token.replace("-", "");
        // 内部设置了过期时间
        // 前缀  key  value
        redisService.set(UserKeyPrefix.getToken, token, userModel);


        log.info("用户登录: {}" , userModel);

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

        log.info("用户注册: {}" , userModel);
        return ResultType.create(null);
    }

    @ApiOperation(value = "登录之前先获取验证码", notes = "使用返回的验证码登录。图片显示在用户登录页面")
    // produces = "image/jpeg" 不设置就是乱码
    @RequestMapping(value = "/getotp",method = {RequestMethod.GET},consumes={CONTENT_TYPE_FORMED}, produces = "image/jpeg")
    public void getOtpCode(@RequestParam(name = "telphone") String telphone, HttpServletResponse httpServletResponse) throws CommonExceptionImpl {
        //Random random = new Random();
        //int randomInt = random.nextInt(99999);
        //randomInt += 100000;
        //String otpCode = String.valueOf(randomInt);

        // 返回验证码的图片
        Map<String,Object> map = CodeUtil.generateCodeAndPic();

        log.info("telphone:" + telphone + ", otpCode:" + map.get("code"));
        // 使用session
        //httpServletRequest.getSession().setAttribute(telphone, map.get("code"));
        // 使用自带的redisTemplate
        //redisTemplate.opsForValue().set(telphone, map.get("code"));
        //redisTemplate.expire(telphone, 5, TimeUnit.MINUTES);
        // 使用自己封装的redisService
        redisService.set(UserKeyPrefix.getVerificationCode, telphone, map.get("code"));

        try {
            // 不设置这knife4j就是下载文件
            httpServletResponse.setContentType("image/jpeg");
            ImageIO.write((RenderedImage) map.get("pic"), "jpeg", httpServletResponse.getOutputStream());
        } catch (IOException ioException) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "验证码图片生成错误");
        } finally {

        }
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
