package com.deltaqin.seckill.redisutils.keyprefix;

/**
 * @author deltaqin
 * @date 2021/6/14 上午8:31
 */
public interface KeyPrefix {
    public String getPrefix();
    public int expireSeconds();
}
