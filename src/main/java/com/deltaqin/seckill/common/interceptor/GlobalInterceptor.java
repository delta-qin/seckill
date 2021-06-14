package com.deltaqin.seckill.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.deltaqin.seckill.common.constant.GlobalConstant;
import com.deltaqin.seckill.common.entities.ResultType;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.model.UserModel;
import com.deltaqin.seckill.redisutils.jedis.RedisService;
import com.deltaqin.seckill.redisutils.keyprefix.AccessKeyPrefix;
import com.deltaqin.seckill.redisutils.keyprefix.UserKeyPrefix;
import com.deltaqin.seckill.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author deltaqin
 * @date 2021/6/13 下午11:00
 */
@Service
@Slf4j
public class GlobalInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    /**
     * 处理有注解修饰的方法
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            log.info("拦截请求方法的Handler: {}", handler);
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit methodAnnotation = handlerMethod.getMethodAnnotation(AccessLimit.class);
            // 没有注解修饰的话就放行
            if (methodAnnotation == null) {
                log.info("拦截器放行，没有注解的不做处理");
            }

            int seconds = methodAnnotation.seconds();
            int maxCount = methodAnnotation.maxCount();
            boolean needLogin = methodAnnotation.needLogin();
            UserModel user = getUser(request, response);

            // 限流的时候使用当前的请求路径作为限流的key，如果是要求登录的话还要加上用户唯一特征
            String key = request.getRequestURI();

            if (needLogin) {
                // 对需要登录的方法处理
                if (user == null) {
                    // 直接返回用户还未登录
                    render(response, ExceptionTypeEnum.USER_NOT_LOGIN);
                    // 注意拦截器返回false就不会往下执行了
                    return false;
                }
                // 否则设置一下当前 key 是用户专属的 key，而不是浏览商品页那种统一的key
                key += "_" + user.getName();
            }

            // 大于0 的时候才需要限流
            if (maxCount > 0 ){
                AccessKeyPrefix accessKeyPrefix = AccessKeyPrefix.getWithExpire(seconds);
                Integer count = redisService.get(accessKeyPrefix, key, Integer.class);
                if (count == null) {
                    redisService.set(accessKeyPrefix, key, 1);
                } else if (count < maxCount) {
                    redisService.incr(accessKeyPrefix, key);
                } else {
                    render(response, ExceptionTypeEnum.RATE_LIMIT);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 从线程的ThreadLocal里面删除
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        UserInfoThreadLocal.removeUser();
    }

    /**
     * 错误 请求返回给前端
     * @param httpServletResponse
     * @param typeEnum  错误信息枚举类型
     * @throws IOException
     */
    private void render(HttpServletResponse httpServletResponse, ExceptionTypeEnum typeEnum) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String s = JSON.toJSONString(ResultType.create(typeEnum));
        outputStream.write(s.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 通过请求的信息从Redis 获取当前的用户
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    private UserModel getUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String token = getToken(httpServletRequest, GlobalConstant.TOKEN_KEY_NAME);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return redisService.get(UserKeyPrefix.getToken, token, UserModel.class);
    }

    // 通过cookie或者param或者header获取token
    private String getToken(HttpServletRequest httpServletRequest, String tokenKeyName) {

        // 检测cookie
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length == 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenKeyName)) {
                    return cookie.getValue();
                }
            }
        }

        // 检测param
        String parameter = httpServletRequest.getParameter(tokenKeyName);
        if (!StringUtils.isEmpty(parameter)) {
            return parameter;
        }

        // 检测header
        String header = httpServletRequest.getHeader(tokenKeyName);
        if (!StringUtils.isEmpty(header)) {
            return header;
        }

        return null;
    }


}
