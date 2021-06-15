package com.deltaqin.seckill.mqutils;

import com.alibaba.fastjson.JSON;
import com.deltaqin.seckill.common.constant.GlobalConstant;
import com.deltaqin.seckill.common.exception.CommonExceptionImpl;
import com.deltaqin.seckill.dao.StockLogMapper;
import com.deltaqin.seckill.dataobject.StockLog;
import com.deltaqin.seckill.service.OrderService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.tomcat.websocket.TransformationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author deltaqin
 * @date 2021/6/14 下午6:41
 */
@Component
public class MqProducer {
    private DefaultMQProducer producer;

    // v3.0
    private TransactionMQProducer transactionMQProducer;



    @Value("${mq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${mq.topicname}")
    private String topicName;

    // 异步减库存,直接减就好
    @Autowired
    private StockLogMapper stockLogMapper;

    // 发送半消息之后本地创建订单
    @Autowired
    private OrderService orderService;

    // 初始化方法
    @PostConstruct
    public void init() throws MQClientException {
        producer = new DefaultMQProducer(GlobalConstant.PRODUCER_GROUP);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();

        // 事务消息 v3.0
        transactionMQProducer = new TransactionMQProducer(GlobalConstant.TRANSACTION_PRODUCER_GROUP);
        transactionMQProducer.setNamesrvAddr(nameServerAddr);
        transactionMQProducer.start();

        // 设置事务消息的相关回调（半消息设置）
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
                // 本地创建订单
                Integer goodsId = (Integer) ((Map)arg).get("goodsId");
                Integer secKillId = (Integer) ((Map)arg).get("secKillId");
                Integer userId = (Integer) ((Map)arg).get("userId");
                Integer count = (Integer) ((Map)arg).get("count");
                String stockLogId = (String) ((Map)arg).get("stockLogId");

                try {
                    orderService.createOrder(userId, goodsId, secKillId, count, stockLogId);
                } catch (CommonExceptionImpl e) {
                    e.printStackTrace();
                    StockLog stockLog = stockLogMapper.selectByPrimaryKey(stockLogId);
                    // 设置商品的订单的流水状态是回滚
                    stockLog.setItemStatus((short)3);
                    stockLogMapper.updateByPrimaryKeySelective(stockLog);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                String string = new String(messageExt.getBody());

                Map<String, Object> map = JSON.parseObject(string, Map.class);

                //Integer goodsId = (Integer) map.get("goodsId");
                //Integer count = (Integer) map.get("count");
                String stockLogId = (String) map.get("stockLogId");

                StockLog stockLog = stockLogMapper.selectByPrimaryKey(stockLogId);
                if (stockLog == null) {
                    return LocalTransactionState.UNKNOW;
                }
                if (stockLog.getItemStatus().intValue() == 2) {
                    //
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (stockLog.getItemStatus().intValue() == 1) {
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });
    }

    // 非事务性 异步扣减库存
    public boolean asyncDecreaseStock(Integer goodsId, Integer count) {
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("goodsId", goodsId);
        messageMap.put("count", count);

        Message message = new Message(topicName, "reduce",
                JSON.toJSON(messageMap).toString().getBytes(Charset.forName("UTF-8")));

        try {
            producer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        } catch (RemotingException e) {
            e.printStackTrace();
            return false;
        } catch (MQBrokerException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 事务型 同步创建订单和异步扣减库存
    // 和上面的参数不一样，因为这个是在订单创建之前调用的，上面的是在订单创建之后调用的，上面只需简单发一个减数据库库存的消息即可
    public boolean transactionAsyncDecreaseStock(Integer userId, Integer goodsId, Integer secKillId,Integer count, String stockLogId) {
        Map<String,Object> bodyMap = new HashMap<>();
        bodyMap.put("goodsId",goodsId);
        bodyMap.put("count",count);
        bodyMap.put("stockLogId",stockLogId);

        Map<String,Object> argsMap = new HashMap<>();
        argsMap.put("goodsId",goodsId);
        argsMap.put("count",count);
        argsMap.put("userId",userId);
        argsMap.put("secKillId",secKillId);
        argsMap.put("stockLogId",stockLogId);

        Message message = new Message(topicName, "decrease",
                JSON.toJSON(bodyMap).toString().getBytes(Charset.forName("UTF-8")));
        TransactionSendResult result = null;
        try {
            result = transactionMQProducer.sendMessageInTransaction(message, argsMap);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        if (result.getLocalTransactionState() == LocalTransactionState.ROLLBACK_MESSAGE) {
            return false;
        } else if (result.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE) {
            return true;
        } else {
            return false;
        }

    }


}
