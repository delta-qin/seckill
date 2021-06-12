package com.deltaqin.seckill.service;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.model.OrderModel;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:24
 */
public interface OrderService {

    OrderModel createOrder(Integer userId, Integer goodsId, Integer SeckillId, Integer count, String stockLogId) throws CommonExceptionImpl;

}
