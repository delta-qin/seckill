package com.deltaqin.seckill.common.exception;

/**
 * @author deltaqin
 * @date 2021/6/9 下午11:47
 */
public enum ExceptionTypeEnum implements CommonException {
    PARAMETER_VALIDATION_ERROR(40001, "参数不合法"),
    UNKNOW_ERROR(40000, "未知错误"),

    // 5 开头用户信息相关的错误
    USER_NOT_EXIST(50001, "用户不存在"),
    USER_LOGIN_FAIL(50002, "用户手机号或者密码不正确"),
    USER_NOT_LOGIN(50003, "用户还未登录"),

    // 6 开头是交易的信息错误
    STOCK_NOT_ENOUGH(60001,"库存不足"),
    MQ_SEND_FAIL(60002,"库存异步消息失败"),
    RATE_LIMIT(60003,"防刷限制，请稍后再试"),
    ;

    private int errCode;
    private String errMsg;

    ExceptionTypeEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonException setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }


}
