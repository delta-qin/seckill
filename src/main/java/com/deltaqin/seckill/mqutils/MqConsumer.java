package com.deltaqin.seckill.mqutils;

import com.alibaba.fastjson.JSON;
import com.deltaqin.seckill.common.constant.GlobalConstant;
import com.deltaqin.seckill.dao.ItemStockMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;
import java.util.Map;

import static com.deltaqin.seckill.common.constant.GlobalConstant.CONSUMER_GROUP;

/**
 * @author deltaqin
 * @date 2021/6/14 下午9:54
 */
@Component
public class MqConsumer {
    private DefaultMQPushConsumer consumer;

    @Value("${mq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${mq.topicname}")
    private String topicName;

    @Autowired
    private ItemStockMapper itemStockMapper;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(topicName, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            // 注册监听器实现减库存逻辑
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                Message message = list.get(0);
                String jsonString = new String(message.getBody());
                Map map = JSON.parseObject(jsonString, Map.class);
                Integer goodsId = (Integer) map.get("goodsId");
                Integer count = (Integer) map.get("count");
                itemStockMapper.decreaseStock(goodsId, count);

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
    }
}
