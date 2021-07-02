package com.deltaqin.seckill.service;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.model.SeckillModel;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:23
 */
public interface SeckillService {

    SeckillModel getSeckillByGoodId(Integer goodsId) throws CommonExceptionImpl;

    void publishSeckill(Integer id) throws CommonExceptionImpl;

    String generateSecKillToken(Integer id, Integer goodsId, Integer userId) throws CommonExceptionImpl;

    void createSecKill(SeckillModel seckillModel) throws CommonExceptionImpl;
}
