package com.deltaqin.seckill.mqutils;

import com.alibaba.fastjson.JSON;
import com.deltaqin.seckill.common.constant.GlobalConstant;
import com.deltaqin.seckill.dao.StockLogMapper;
import com.deltaqin.seckill.service.OrderService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
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

        // TODO 事务消息 v3.0
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


}
