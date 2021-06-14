package com.deltaqin.seckill.redisutils.keyprefix;

/**
 * @author deltaqin
 * @date 2021/6/14 下午2:27
 */
public class AccessKeyPrefix extends BaseKeyPrefix {
    public AccessKeyPrefix(String prefix) {
        super(prefix);
    }

    public AccessKeyPrefix(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    public static AccessKeyPrefix getWithExpire(int expire) {
        return new AccessKeyPrefix("access", expire);
    }
}
