package com.deltaqin.seckill.common.exception;

/**
 * @author deltaqin
 * @date 2021/6/9 下午11:57
 */
public class CommonExceptionImpl extends Exception implements CommonException {

    private CommonException commonException;

    public CommonExceptionImpl(CommonException commonException) {
        super();
        this.commonException = commonException;
    }

    public CommonExceptionImpl(CommonException commonException, String errMsg) {
        super();
        this.commonException = commonException;
        this.commonException.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonException.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commonException.getErrMsg();
    }

    @Override
    public CommonException setErrMsg(String errMsg) {
        CommonException commonException = this.commonException.setErrMsg(errMsg);
        return commonException;
    }

}
