package com.deltaqin.seckill.localcache.keyprefix;

import com.deltaqin.seckill.redisutils.keyprefix.BaseKeyPrefix;

/**
 * @author deltaqin
 * @date 2021/6/14 下午5:55
 */
public class LocalGoodsKeyPrefix extends BaseKeyPrefix {

    public LocalGoodsKeyPrefix(String prefix) {
        super(prefix);
    }

    public static LocalGoodsKeyPrefix getGoodDetail = new LocalGoodsKeyPrefix("gd");
}
