package com.deltaqin.seckill.service.impl;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.common.validator.ValidationResult;
import com.deltaqin.seckill.common.validator.ValidatorUtil;
import com.deltaqin.seckill.dao.GoodMapper;
import com.deltaqin.seckill.dao.ItemStockMapper;
import com.deltaqin.seckill.dao.StockLogMapper;
import com.deltaqin.seckill.dataobject.*;
import com.deltaqin.seckill.localcache.GuavaLocalCacheServiceImpl;
import com.deltaqin.seckill.localcache.keyprefix.LocalGoodsKeyPrefix;
import com.deltaqin.seckill.model.GoodsModel;
import com.deltaqin.seckill.model.SeckillModel;
import com.deltaqin.seckill.mqutils.MqProducer;
import com.deltaqin.seckill.redisutils.jedis.RedisService;
import com.deltaqin.seckill.redisutils.keyprefix.GoodsKeyPrefix;
import com.deltaqin.seckill.service.GoodsService;
import com.deltaqin.seckill.service.SeckillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:20
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private ItemStockMapper itemStockMapper;

    @Autowired
    private StockLogMapper stockLogMapper;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GuavaLocalCacheServiceImpl guavaLocalCacheService;

    @Autowired
    private MqProducer mqProducer;



    @Override
    //@Transactional
    public GoodsModel createGoods(GoodsModel goodsModel) throws CommonExceptionImpl {
        // 参数校验
        ValidationResult validate = validatorUtil.validate(goodsModel);
        if (validate.isHasError()) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, validate.getErrMsgString());
        }

        Good good = convertGoodDoFromModel(goodsModel);

        goodMapper.insertSelective(good);
        goodsModel.setGoodId(good.getGoodId());

        ItemStock itemStock = convertStockDoFromModel(goodsModel);
        itemStockMapper.insertSelective(itemStock);

        // 需要去数据库查询，是为了获取到秒杀活动的信息
        return getById(goodsModel.getGoodId());
    }

    private ItemStock convertStockDoFromModel(GoodsModel goodsModel) {
        if (goodsModel == null) return  null;

        ItemStock itemStock = new ItemStock();
        itemStock.setItemId(goodsModel.getGoodId());
        itemStock.setStockCount(goodsModel.getStockCount());
        return itemStock;
    }

    private Good convertGoodDoFromModel(GoodsModel goodsModel) {
        if (goodsModel == null) return null;

        Good good = new Good();
        BeanUtils.copyProperties(goodsModel, good);
        return good;
    }

    @Override
    public List<GoodsModel> getGoodsList() {
        GoodExample goodExample = new GoodExample();
        List<Good> goods = goodMapper.selectByExample(goodExample);

        List<GoodsModel> collect = goods.stream().map(item -> {
            GoodsModel goodsModel = convertGoodModelFromDO(item);
            return goodsModel;
        }).collect(Collectors.toList());

        // 注意这里在商品的列表没有显示秒杀，只有加入到详情页之后才会有秒杀显示
        return collect;
    }

    private GoodsModel convertGoodModelFromDO(Good item) {
        if (item == null) return null;
        GoodsModel goodsModel = new GoodsModel();
        BeanUtils.copyProperties(item, goodsModel);
        return goodsModel;
    }

    // 重载设置了库存的方法
    private GoodsModel convertGoodModelFromDO(Good item, ItemStock itemStock) {
        if (item == null) return null;
        GoodsModel goodsModel = new GoodsModel();
        BeanUtils.copyProperties(item, goodsModel);

        goodsModel.setStockCount(itemStock.getStockCount());
        return goodsModel;
    }

    // 纯粹的数据库操作
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GoodsModel getById(Integer id) throws CommonExceptionImpl {
        Good good = goodMapper.selectByPrimaryKey(id);
        if (good == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "商品不存在");
        }

        // 获取的商品的库存
        //ItemStock itemStock = itemStockMapper.selectByGoodsId(id);
        //ItemStock itemStock = itemStockMapper.selectByGoodsId(good.getGoodId());
        ItemStockExample itemStockExample = new ItemStockExample();
        itemStockExample.createCriteria().andItemIdEqualTo(good.getGoodId());
        List<ItemStock> itemStocks = itemStockMapper.selectByExample(itemStockExample);

        GoodsModel goodsModel = convertGoodModelFromDO(good, itemStocks.get(0));
        // 获取商品的秒杀信息
        SeckillModel seckillModel = seckillService.getSeckillByGoodId(id);
        if (seckillModel != null) {
            goodsModel.setSeckillModel(seckillModel);
        }

        return goodsModel;
    }
    // 使用三级缓存查询
    @Override
    public GoodsModel getByIdInCache(Integer id) throws CommonExceptionImpl {
        // 缓存没有的话更新一下到缓存里面（同时更新缓存？？？？）
        GoodsModel goodsModel = null;
        // 先从本地缓存里面读取
        goodsModel = (GoodsModel)guavaLocalCacheService.getFromLocalCache(
                LocalGoodsKeyPrefix.getGoodDetail.getPrefix() + id);
        if (goodsModel == null) {
            // 再去Redis里面读取
            goodsModel = redisService.get(GoodsKeyPrefix.goodsDetail, String.valueOf(id), GoodsModel.class);
            if (goodsModel == null) {
                // 再去数据库里面读取
                goodsModel = getById(id);
                if (goodsModel == null) {
                    throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "商品不存在");
                }
                // 再放到缓存里
                redisService.set(GoodsKeyPrefix.goodsDetail, String.valueOf(id), goodsModel);
            }
            // 再放到本地缓存里面
            guavaLocalCacheService.setLocalCache(LocalGoodsKeyPrefix.getGoodDetail.getPrefix() + String.valueOf(id),
                    goodsModel);
        }
        return goodsModel;
    }

    @Override
    public void increaseSales(Integer goodsId, Integer count) {
        goodMapper.increaseSales(goodsId, count);
    }


    // v2.0 减缓存的库存
    // 主要是为了下单的时候响应快速
    @Override
    public boolean decreaseStock(Integer id, Integer count) {
        // 在这里比较重要，减库存直接使用SQL语句里面避免减的时候出现负数
        //int affectRow  = itemStockMapper.decreaseStock(id, count);

        // 替换为直接减少缓存。并且使用消息队列实现缓库存的异步更新
        // 这里不使用本地缓存是因为太多的话并发有问题，尽量减少缓存的问题
        Long decr = redisService.decr(GoodsKeyPrefix.goodsStock, String.valueOf(id), count);
        if (decr > 0) {
            return true;
        } else if (decr == 0){
            // 更新库存失败回滚
            redisService.set(GoodsKeyPrefix.goodsInvalid, String.valueOf(id), "true");
            return true;
        } else {
            // 更新缓存失败
            increase(id, count);
            return false;
        }
    }

    // mq 异步减少数据库的库存，这个v2.0是在
    @Override
    public boolean asyncDecreaseStock(Integer id, Integer count) {
        return mqProducer.asyncDecreaseStock(id, count);
    }


    @Override
    public boolean increase(Integer id, Integer count) {
        //  实现缓存的回滚
        redisService.incr(GoodsKeyPrefix.goodsStock, String.valueOf(id), count);
        return true;
    }

    // 初始化对应的库存流水
    @Override
    public String initStockLog(Integer id, Integer count) {
        StockLog stockLog = new StockLog();
        stockLog.setItemId(id);
        stockLog.setItemStockCount(count);
        stockLog.setStockLogId(UUID.randomUUID().toString().replace("-", ""));
        stockLogMapper.insertSelective(stockLog);
        return stockLog.getStockLogId();
    }



    //public static void main(String[] args) {
    //    System.out.println(UUID.randomUUID().toString().replace("-", ""));;
    //}
}
