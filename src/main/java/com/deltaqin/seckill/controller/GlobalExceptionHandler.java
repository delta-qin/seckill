package com.deltaqin.seckill.controller;

import com.deltaqin.seckill.common.entities.ResultType;
import com.deltaqin.seckill.common.exception.CommonException;
import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author deltaqin
 * @date 2021/6/10 下午10:05
 */

/**
 * 处理用户自定义异常
 * servlet路由异常
 * 路径404错误
 * 其余所有的异常捕获
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String CODE = "errCode";
    private static final String MSG = "errMsg";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultType handleError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                  Exception exception) {
        exception.printStackTrace();
        Map<String, Object> response = new HashMap<>();

        if (exception instanceof CommonExceptionImpl) {
            CommonExceptionImpl commonException = (CommonExceptionImpl) exception;
            response.put(CODE, commonException.getErrCode());
            response.put(MSG, commonException.getErrMsg());
        } else if (exception instanceof ServletRequestBindingException) {
            response.put(CODE, ExceptionTypeEnum.UNKNOW_ERROR.getErrCode());
            response.put(MSG, "URL绑定路由问题， ServletRequestBindingException");
        } else if (exception instanceof NoHandlerFoundException) {
            // 这个异常没有进入Controller，注意需要设置一下在配置文件里面
            response.put(CODE, ExceptionTypeEnum.UNKNOW_ERROR.getErrCode());
            response.put(MSG, "没有对应的访问路径，NoHandlerFoundException");
        } else {
            response.put(CODE, ExceptionTypeEnum.UNKNOW_ERROR.getErrCode());
            response.put(MSG, ExceptionTypeEnum.UNKNOW_ERROR.getErrMsg());
        }

        // 在返回类型里设置了重载，只有在错误的时候才需要设置
        return ResultType.create(response, "fail");
    }

}
