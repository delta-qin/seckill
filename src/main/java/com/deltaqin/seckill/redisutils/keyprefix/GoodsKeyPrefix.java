package com.deltaqin.seckill.redisutils.keyprefix;

/**
 * @author deltaqin
 * @date 2021/6/14 上午8:39
 */

// 商品键 前缀
public class GoodsKeyPrefix extends BaseKeyPrefix {

    // 没有默认构造器要自己设置
    private GoodsKeyPrefix(int expire, String prefix) {
        super(prefix, expire);
    }

    public static GoodsKeyPrefix goodsList = new GoodsKeyPrefix(60, "gl");
    public static GoodsKeyPrefix goodsDetail = new GoodsKeyPrefix(60, "gd");

    // 库存一直保存，修改的时候才会刷新
    // 0 就是用不过期
    public static GoodsKeyPrefix goodsStock = new GoodsKeyPrefix(0, "gs");
    public static GoodsKeyPrefix goodsInvalid = new GoodsKeyPrefix(0, "gi");

}
