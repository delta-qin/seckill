package com.deltaqin.seckill.redisutils.keyprefix;

/**
 * @author deltaqin
 * @date 2021/6/14 下午2:05
 */
public class SeckillKeyPrefix extends BaseKeyPrefix {

    public SeckillKeyPrefix(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    public static SeckillKeyPrefix isSeckillEnd = new SeckillKeyPrefix("se", 0);
    public static SeckillKeyPrefix getSeckillPath = new SeckillKeyPrefix("sp", 60);
    public static SeckillKeyPrefix getSeckillCode = new SeckillKeyPrefix("sc", 300);

}
