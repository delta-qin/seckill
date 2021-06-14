package com.deltaqin.seckill.service.impl;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.dao.SeckillGoodMapper;
import com.deltaqin.seckill.dataobject.SeckillGood;
import com.deltaqin.seckill.model.GoodsModel;
import com.deltaqin.seckill.model.SeckillModel;
import com.deltaqin.seckill.model.UserModel;
import com.deltaqin.seckill.redisutils.jedis.RedisService;
import com.deltaqin.seckill.redisutils.keyprefix.GoodsKeyPrefix;
import com.deltaqin.seckill.redisutils.keyprefix.SeckillKeyPrefix;
import com.deltaqin.seckill.service.GoodsService;
import com.deltaqin.seckill.service.SeckillService;
import com.deltaqin.seckill.service.UserService;
import org.apache.catalina.User;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:24
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillGoodMapper seckillGoodMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;


    // 缓存没有的时候才会调用这里面，所以直接就是数据库操作
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

    //  发布秒杀预热
    @Override
    public void publishSeckill(Integer seckillId) throws CommonExceptionImpl {
        SeckillGood seckillGood = seckillGoodMapper.selectByPrimaryKey(seckillId);
        if (seckillGood == null || seckillGood.getItemId() == null || seckillGood.getItemId().intValue() == 0 ) {
            return;
        }

        // 获取到商品
        GoodsModel goodsModel = goodsService.getById(seckillGood.getItemId());

        // 商品信息 和 秒杀活动的信息 同步到Redis内部
        redisService.set(SeckillKeyPrefix.getSeckillItem, String.valueOf(seckillId), seckillGood);
        redisService.set(GoodsKeyPrefix.goodsDetail, String.valueOf(seckillGood.getItemId()), goodsModel);

        // 设置缓存的大闸，请求只能超过5倍
        redisService.set(SeckillKeyPrefix.getSeckillStockDoor, String.valueOf(seckillId),
                goodsModel.getStockCount().intValue() * 5);
    }

    // 生成令牌
    @Override
    public String generateSecKillToken(Integer id, Integer goodsId, Integer userId) throws CommonExceptionImpl {
        // 没有库存直接返回售罄
        if (redisService.get(SeckillKeyPrefix.getSeckillStock, String.valueOf(goodsId), Integer.class) == 0) {
            return null;
        }

        // 获取秒杀活动信息
        SeckillModel seckillModel = redisService.get(SeckillKeyPrefix.getSeckillItem, String.valueOf(id), SeckillModel.class);
        if (seckillModel == null) return null;

        // 判断活动是否正在进行
        if (seckillModel.getStartTime().isAfterNow()) {
            seckillModel.setItemStatus((short)1);
        } else if (seckillModel.getEndTime().isBeforeNow()) {
            seckillModel.setItemStatus((short)3);
        } else {
            seckillModel.setItemStatus((short)2);
        }
        if (seckillModel.getItemStatus() != 2) {
            return null;
        }

        // 判断商品信息是否存在,预热的时候会放在缓存里，没有的话回去数据库，还是没有的话就是商品不存在
        GoodsModel goodsModel = goodsService.getByIdInCache(goodsId);
        if (goodsModel == null) {
            return  null;
        }

        // 判断用户是否在缓存里面，没有的话是和token一起失效的，就要重新登录；
        UserModel userModel =  userService.getUserByIdInCache(userId);
        if (userModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.USER_NOT_LOGIN);
        }

        // 秒杀大闸
        Long decr = redisService.decr(SeckillKeyPrefix.getSeckillStockDoor, String.valueOf(id));
        if (decr < 0) {
            return null;
        }

        // token 每个用户都是自己的，内部已经设置 了过期时间
        String token = UUID.randomUUID().toString().replace("-", "");
        redisService.set(SeckillKeyPrefix.getSeckillToken, String.valueOf(userId), token);

        return token;
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
