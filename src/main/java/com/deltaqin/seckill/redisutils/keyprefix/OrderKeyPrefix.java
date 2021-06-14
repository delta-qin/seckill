package com.deltaqin.seckill.redisutils.keyprefix;

/**
 * @author deltaqin
 * @date 2021/6/14 下午1:48
 */
public class OrderKeyPrefix extends BaseKeyPrefix {
    public OrderKeyPrefix(String prefix) {
        super(prefix);
    }

    public static OrderKeyPrefix getOrderByUidGid = new OrderKeyPrefix("order");
}
