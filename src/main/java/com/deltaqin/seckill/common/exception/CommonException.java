package com.deltaqin.seckill.common.exception;

/**
 * @author deltaqin
 * @date 2021/6/9 下午11:45
 */
public interface CommonException {
    public int getErrCode();
    public String getErrMsg();
    public CommonException setErrMsg(String errMsg);
}
