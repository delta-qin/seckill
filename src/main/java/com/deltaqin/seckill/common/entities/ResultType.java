package com.deltaqin.seckill.common.entities;

/**
 * @author deltaqin
 * @date 2021/6/9 下午11:38
 */
public class ResultType {
    // success or fail
    private String status;
    private Object data;

    // 成功了就直接使用这个
    public static ResultType create(Object result) {
        return ResultType.create(result, "success");
    }

    // 失败了就直接调用这个，返回对应的错误信息
    public static ResultType create(Object result, String status) {
        ResultType type = new ResultType();
        type.setData(result);
        type.setStatus(status);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
