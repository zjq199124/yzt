package com.maizhiyu.yzt.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjq
 */
@Slf4j
@RestController
@RequestMapping("/rocketMQSend")
public class RocketMqSendController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通字符串消息
     */
    @GetMapping("sendMessage")
    public void sendMessage() {
        String json = "普通消息";
        rocketMQTemplate.convertAndSend("sendMessage", json);
    }

    /**
     * 同步消息
     * 同步消息有返回值SendResult，等到消息发送成功后才算结束。
     */
    @GetMapping("syncSend")
    public void syncSend() {
        String json = "同步消息";
        SendResult sendMessage = rocketMQTemplate.syncSend("sendMessage-", json);
        System.out.println(sendMessage);
    }

    /**
     * 异步消息
     * 异步消息无返回值，需要传入回调类。无需等待消息是否发送成功。
     */
    @GetMapping("asyncSend")
    public void asyncSend() {
        String json = "异步消息";
        SendCallback callback = new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("123");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("456");
            }
        };
        rocketMQTemplate.asyncSend("sendMessage_", json, callback);
    }

    /**延时30分钟消费*/
    public final static Integer THIRTY_MINUTES_DELAY_LEVEL=16;
    /**
     * 异步发送延时消息
     * @param topic
     *                          delayLevel=1  2  3   4   5  6  7  8  9  10 11 12 13  14 15  16  17 18
     * @param delayLevel messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    @GetMapping("asyncDelaySend")
    public void asyncDelaySend(String topic , Integer delayLevel){
        if (delayLevel==null){
            delayLevel = THIRTY_MINUTES_DELAY_LEVEL;
        }
        Message message= MessageBuilder.withPayload("我是消息").build();
        log.info("发送的Topic={},tag={},内容={}", topic );
        rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送延时消息成功的Topic={},tag={},内容={}", topic);
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("发送延时消息失败的Topic={},tag={},内容={}", topic);
            }
        },3000L,delayLevel);
    }


    /**
     * 单向消息
     */
    @GetMapping("oneWaySend")
    public void oneWaySend() {
        String json = "单向消息";
        rocketMQTemplate.sendOneWay("sendMessage", json);
    }

}