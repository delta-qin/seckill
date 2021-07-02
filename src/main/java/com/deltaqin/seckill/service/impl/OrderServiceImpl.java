package com.deltaqin.seckill.service.impl;

import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.common.exception.ExceptionTypeEnum;
import com.deltaqin.seckill.dao.OrderInfoMapper;
import com.deltaqin.seckill.dao.SequenceInfoMapper;
import com.deltaqin.seckill.dao.StockLogMapper;
import com.deltaqin.seckill.dataobject.OrderInfo;
import com.deltaqin.seckill.dataobject.SequenceInfo;
import com.deltaqin.seckill.dataobject.SequenceInfoExample;
import com.deltaqin.seckill.dataobject.StockLog;
import com.deltaqin.seckill.model.GoodsModel;
import com.deltaqin.seckill.model.OrderModel;
import com.deltaqin.seckill.model.UserModel;
import com.deltaqin.seckill.service.GoodsService;
import com.deltaqin.seckill.service.OrderService;
import com.deltaqin.seckill.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author deltaqin
 * @date 2021/6/12 上午9:24
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private StockLogMapper stockLogMapper;

    @Autowired
    private SequenceInfoMapper sequenceInfoMapper;

    // 自己不可以使用别人的mapper，只可以使用别人的service
    @Autowired
    //private GoodMapper goodMapper;
    private GoodsService goodsService;

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer goodsId, Integer seckillId, Integer count, String stockLogId) throws CommonExceptionImpl {

        // 验证商品信息
        GoodsModel goodsModel = goodsService.getByIdInCache(goodsId);
        if (goodsModel == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "商品不存在");
        }

        // 验证用户信息(controller校验了）
        //UserModel userModel = userService.getUserById(userId);
        //if (userModel == null) {
        //    throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        //}

        // 这里校验使用了商品的秒杀信息，所以必须在service里面处理
        // 不会在Controller里面获取实际的商品信息。
        // 这个不需要了。校验会放在用户获取秒杀令牌的时候。获取到令牌就说明此时秒杀开始了。获取令牌里面有校验逻辑
        //if (seckillId != null) {
        //    if (seckillId.intValue() != goodsModel.getSeckillModel().getSecId().intValue() ) {
        //        throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR,"秒杀信息不正确");
        //    } else if (goodsModel.getSeckillModel().getItemStatus().intValue() == 1) {
        //        throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "秒杀还没开始");
        //    } else if (goodsModel.getSeckillModel().getItemStatus().intValue() == 3) {
        //        throw new CommonExceptionImpl(ExceptionTypeEnum.PARAMETER_VALIDATION_ERROR, "秒杀已经结束");
        //    }
        //}

        // 落单减库存（不会超卖）/支付减库存（但是无法保证支付之后还有库存，恶意下单，即使超卖）
        // 这里就是预减库存，也就是减去的是缓存的，数据库的会在最后调用
        boolean b = goodsService.decreaseStock(goodsId, count);
        if (!b) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.STOCK_NOT_ENOUGH);
        }

        // 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(goodsId);
        orderModel.setItemCount(count);
        // 价格是创建订单时候应该有的价格
        if (seckillId != null) {
            // 获取秒杀活动里面的秒杀价格
            orderModel.setItemPrice(goodsModel.getSeckillModel().getSeckillPrice());
        } else {
            orderModel.setItemPrice(goodsModel.getGoodPrice());
        }
        orderModel.setSeckillId(seckillId);
        orderModel.setTotalPrice(orderModel.getItemPrice().multiply(new BigDecimal(count)));
        // 订单号
        orderModel.setOrderId(generateOrderId());
        // 转换为DO
        OrderInfo orderInfo = convertOrderInfoFromModel(orderModel);
        orderInfoMapper.insertSelective(orderInfo);

        // 商品的销量
        goodsService.increaseSales(goodsId, count);

        // 设置流水状态为成功
        // value = "1表示初始状态，2表示下单扣减库存成功，3表示下单回滚"
        StockLog stockLog = stockLogMapper.selectByPrimaryKey(stockLogId);
        if (stockLog == null) {
            throw new CommonExceptionImpl(ExceptionTypeEnum.UNKNOW_ERROR);
        }

        stockLog.setItemStatus((short) 2);
        stockLogMapper.updateByPrimaryKey(stockLog);

        // 发送异步消息扣减数据库里面的库存消息
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @SneakyThrows
            @Override
            public void afterCommit() {
                boolean mqRes = goodsService.asyncDecreaseStock(goodsId, count);
                if (!mqRes) {
                    // 修复库存
                    goodsService.increase(goodsId, count);
                    throw new CommonExceptionImpl(ExceptionTypeEnum.MQ_SEND_FAIL);
                }
            }
        });

        return orderModel;
    }

    private OrderInfo convertOrderInfoFromModel(OrderModel orderModel) {
        if (orderModel == null) return null;

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderModel, orderInfo);

        return orderInfo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderId() {
        StringBuilder stringBuilder = new StringBuilder();

        // 8位日期
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);

        // 6位使用数据库的自增序列
        int sequence = 0;
        SequenceInfoExample sequenceInfoExample = new SequenceInfoExample();
        sequenceInfoExample.createCriteria().andSeqNameEqualTo("order_info");
        List<SequenceInfo> sequenceInfos = sequenceInfoMapper.selectByExample(sequenceInfoExample);
        SequenceInfo sequenceInfo = sequenceInfos.get(0);
        sequence = sequenceInfo.getCurrentValue();

        // 加上设置的步长
        sequenceInfo.setCurrentValue(sequence + sequenceInfo.getStep());
        sequenceInfoMapper.updateByPrimaryKeySelective(sequenceInfo);
        String s = String.valueOf(sequence);
        stringBuilder.append(s);

        // 最后两位是分库分表位
        stringBuilder.append("00");

        return  stringBuilder.toString();
    }
}
