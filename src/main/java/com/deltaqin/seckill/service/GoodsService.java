package com.deltaqin.seckill.service;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.model.GoodsModel;
import com.deltaqin.seckill.model.SeckillModel;

import java.util.List;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:12
 */
public interface GoodsService {
    // 创建商品
    GoodsModel createGoods(GoodsModel goodsModel) throws CommonExceptionImpl;

    List<GoodsModel> getGoodsList();

    GoodsModel getById(Integer id) throws CommonExceptionImpl;

    // 扣减库存
    boolean decreaseStock(Integer id, Integer count);

    // 增加库存
    boolean increase(Integer id , Integer count);


    // 初始化库存流水
    String initStockLog(Integer id, Integer count);

}
