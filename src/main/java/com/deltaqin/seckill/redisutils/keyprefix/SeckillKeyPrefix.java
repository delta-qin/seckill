package com.deltaqin.seckill.redisutils.keyprefix;

/**
 * @author deltaqin
 * @date 2021/6/14 下午2:05
 */
public class SeckillKeyPrefix extends BaseKeyPrefix {

    public SeckillKeyPrefix(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    //public static SeckillKeyPrefix isSeckillEnd = new SeckillKeyPrefix("se", 0);
    public static SeckillKeyPrefix getSeckillPath = new SeckillKeyPrefix("sp", 60);
    public static SeckillKeyPrefix getSeckillCode = new SeckillKeyPrefix("sc", 300);
    public static SeckillKeyPrefix getSeckillToken = new SeckillKeyPrefix("st", 300);
    public static SeckillKeyPrefix getSeckillItem = new SeckillKeyPrefix("si", 0);
    public static SeckillKeyPrefix getSeckillStockDoor = new SeckillKeyPrefix("ssd", 0);
    // 当前还有的库存
    public static SeckillKeyPrefix getSeckillStock = new SeckillKeyPrefix("ss", 0);

}
