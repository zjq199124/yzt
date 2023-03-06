package com.maizhiyu.yzt.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
/**
 * 消息消费方
 * 1.如果两个消费者group和topic都一样，则二者轮循接收消息
 * 2.如果两个消费者topic一样，而group不一样，则消息变成广播机制
 * RocketMQListener<>泛型必须和接收的消息类型相同
 */
@RocketMQMessageListener(
        // 1.topic：消息的发送者使用同一个topic
        topic = "sendMessage",
        // 2.group：不用和生产者group相同 ( 在RocketMQ中消费者和发送者组没有关系 )
        consumerGroup = "consumer-group",
        // 3.tag：设置为 * 时，表示全部。
        selectorExpression = "*",
        // 4.消费模式：默认 CLUSTERING （ CLUSTERING：负载均衡 ）（ BROADCASTING：广播机制 ）
        messageModel = MessageModel.CLUSTERING
)
public class RocketMQGetService implements RocketMQListener<Object> {
    @Override
    public void onMessage(Object object) {
        // TODO 业务处理
        log.info("监听到主题为'sendMsg-msg'的消息：" + object);
    }
}
