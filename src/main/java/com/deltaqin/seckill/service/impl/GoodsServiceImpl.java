package com.deltaqin.seckill.service.impl;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.common.validator.ValidationResult;
import com.deltaqin.seckill.common.validator.ValidatorUtil;
import com.deltaqin.seckill.dao.GoodMapper;
import com.deltaqin.seckill.dao.ItemStockMapper;
import com.deltaqin.seckill.dao.StockLogMapper;
import com.deltaqin.seckill.dataobject.*;
import com.deltaqin.seckill.model.GoodsModel;
import com.deltaqin.seckill.model.SeckillModel;
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

    @Override
    //@Transactional(propagation = Propagation.REQUIRED)
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

    @Override
    public boolean decreaseStock(Integer id, Integer count) {
        // 在这里比较重要，减库存直接使用SQL语句里面避免减的时候出现负数
        int affectRow  = itemStockMapper.decreaseStock(id, count);
        if (affectRow > 0) {
            return true;
        } else {
            // 更新库存失败回滚
        }
        return false;
    }

    @Override
    public boolean increase(Integer id, Integer count) {
        // TODO 实现缓存的回滚
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
