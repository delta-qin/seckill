package com.deltaqin.seckill.service.impl;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.dao.SeckillGoodMapper;
import com.deltaqin.seckill.dataobject.SeckillGood;
import com.deltaqin.seckill.model.SeckillModel;
import com.deltaqin.seckill.service.SeckillService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:24
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillGoodMapper seckillGoodMapper;


    @Override
    public SeckillModel getSeckillByGoodId(Integer goodsId) throws CommonExceptionImpl {
        SeckillGood seckillGood = seckillGoodMapper.selectByGoodsId(goodsId);
        if (seckillGood == null) {
            //throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "不是秒杀商品");
            return null;
        }
        SeckillModel seckillModel = convertSeckillModelFromDo(seckillGood);

        // 设置秒杀商品的状态，这个是不会在数据库维护的
        if (seckillModel.getStartTime().isAfterNow()) {
            seckillModel.setItemStatus((short) 1);
        } else if (seckillModel.getEndTime().isBeforeNow()) {
            seckillModel.setItemStatus((short) 3);
        } else {
            seckillModel.setItemStatus((short) 2);
        }
        return seckillModel;
    }

    private SeckillModel convertSeckillModelFromDo(SeckillGood seckillGood) {
        if (seckillGood == null) return null;

        SeckillModel seckillModel = SeckillModel.builder().build();
        BeanUtils.copyProperties(seckillGood, seckillModel);
        // 拷贝之后需要将对应的类型转换
        seckillModel.setStartTime(new DateTime(seckillGood.getStartTime()));
        seckillModel.setEndTime(new DateTime(seckillGood.getEndTime()));
        return seckillModel;
    }

    // TODO 发布秒杀预热
    @Override
    public void publishSeckill(Integer id) {

    }

    // TODO 生成令牌
    @Override
    public String generateSecKillToken(Integer id, Integer goodsId, Integer userId) {
        return null;
    }

    @Override
    public void createSecKill(SeckillModel seckillModel) throws CommonExceptionImpl {
        SeckillGood seckillGood = convertSeckillFromModel(seckillModel);
        try {
            seckillGoodMapper.insertSelective(seckillGood);
        } catch (DuplicateKeyException exception) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.UNKNOW_ERROR, "重复的商品不可以添加秒杀活动");
        }
    }

    private SeckillGood convertSeckillFromModel(SeckillModel seckillModel) {
        if (seckillModel == null) return null;
        SeckillGood seckillGood = new SeckillGood();
        BeanUtils.copyProperties(seckillModel, seckillGood);
        seckillGood.setStartTime(seckillModel.getStartTime().toDate());
        seckillGood.setEndTime(seckillModel.getEndTime().toDate());
        return seckillGood;
    }
}
