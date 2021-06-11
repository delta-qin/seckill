package com.deltaqin.seckill.common.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author deltaqin
 * @date 2021/6/10 下午5:21
 */
public class ValidationResult {

    private boolean hasError = false;

    private Map<String, String> errMsg = new HashMap<>();

    public ValidationResult() {
    }

    public ValidationResult(boolean hasError, Map<String, String> errMsg) {
        this.hasError = hasError;
        this.errMsg = errMsg;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrMsg() {
        return errMsg;
    }

    // 格式化错误信息
    public String getErrMsgString() {
        //return errMsg;
        return StringUtils.join(errMsg.values().toArray(), ",");
    }

    public void setErrMsg(Map<String, String> errMsg) {
        this.errMsg = errMsg;
    }
}
